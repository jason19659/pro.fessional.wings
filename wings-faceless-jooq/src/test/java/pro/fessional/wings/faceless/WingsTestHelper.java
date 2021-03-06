package pro.fessional.wings.faceless;

import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pro.fessional.mirana.data.Diff;
import pro.fessional.wings.faceless.database.FacelessDataSources;
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager;
import pro.fessional.wings.faceless.flywave.util.SimpleJdbcTemplate;
import pro.fessional.wings.faceless.util.FlywaveRevisionScanner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * @author trydofor
 * @since 2020-05-22
 */
@Component
public class WingsTestHelper {

    public static final long REVISION_TEST_V1 = 2019_0601_01L;
    public static final long REVISION_TEST_V2 = 2019_0601_02L;

    private static final Logger logger = LoggerFactory.getLogger(WingsTestHelper.class);

    @Setter(onMethod = @__({@Autowired}))
    private SchemaRevisionManager schemaRevisionManager;
    @Setter(onMethod = @__({@Autowired}))
    private FacelessDataSources facelessDataSources;

    private final HashMap<DataSource, Boolean> isH2Map = new HashMap<>();

    public boolean isH2() {
        for (DataSource ds : facelessDataSources.plains().values()) {
            Boolean h2 = isH2Map.computeIfAbsent(ds, dataSource -> {
                String s = new SimpleJdbcTemplate(ds, "").jdbcUrl();
                return s.contains(":h2:") || s.contains(":H2:");
            });
            if (h2) return true;
        }
        return false;
    }

    public void cleanAndInit() {
        cleanAndInit(0, FlywaveRevisionScanner.REVISION_PATH_MASTER);
    }

    public void cleanAndInit(long revi, String... branches) {
        facelessDataSources.plains().forEach((k, v) -> {
            testcaseNotice("clean database " + k);
            JdbcTemplate tmpl = new JdbcTemplate(v);
            tmpl.query("SHOW TABLES", rs -> {
                String tbl = rs.getString(1);
                testcaseNotice("drop table " + tbl);
                tmpl.execute("DROP TABLE " + tbl);
            });
        });

        SortedMap<Long, SchemaRevisionManager.RevisionSql> sqls = FlywaveRevisionScanner.scan(branches);
        schemaRevisionManager.checkAndInitSql(sqls, 0, true);
        if (revi > 0) {
            schemaRevisionManager.publishRevision(revi, 0);
        }
    }

    public enum Type {
        Table("SHOW TABLES"),
        Trigger("SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS WHERE EVENT_OBJECT_SCHEMA = database()"),
        ;
        private final String sql;

        Type(String sql) {
            this.sql = sql;
        }
    }

    public void assertSame(Type type, String... str) {
        List<String> bSet = lowerCase(str);
        AtomicBoolean good = new AtomicBoolean(true);
        fetchAllColumn1(type.sql).forEach((k, aSet) -> {
            Diff.S<String> diff = Diff.of(aSet, bSet);
            if (!diff.bNotA.isEmpty()) {
                testcaseNotice(k + " 数据库少：" + type + ":" + Strings.join(diff.bNotA, ','));
                good.set(false);
            }
            if (!diff.aNotB.isEmpty()) {
                testcaseNotice(k + " 数据库多：" + type + ":" + Strings.join(diff.aNotB, ','));
                good.set(false);
            }
        });

       assertTrue(type.name() + "不一致，查看日志，", good.get());
    }

    public void assertHas(Type type, String... str) {
        List<String> bSet = lowerCase(str);
        AtomicBoolean good = new AtomicBoolean(true);
        fetchAllColumn1(type.sql).forEach((k, aSet) -> {
            Diff.S<String> diff = Diff.of(aSet, bSet);
            if (!diff.bNotA.isEmpty()) {
                testcaseNotice(k + " 数据库少：" + type + ":" + Strings.join(diff.bNotA, ','));
                good.set(false);
            }
        });

       assertTrue(type.name() + "不一致，查看日志，", good.get());
    }

    public void assertNot(Type type, String... str) {
        List<String> bSet = lowerCase(str);
        AtomicBoolean good = new AtomicBoolean(true);
        fetchAllColumn1(type.sql).forEach((k, aSet) -> {
            Diff.S<String> diff = Diff.of(aSet, bSet);
            if (diff.bNotA.size() != bSet.size()) {
                testcaseNotice(k + " 数据库不能有：" + type + ": " + Strings.join(diff.bNotA, ','));
                good.set(false);
            }
        });

       assertTrue(type.name() + "不一致，查看日志，", good.get());
    }

    private List<String> lowerCase(String... str) {
        return Arrays.stream(str).map(String::toLowerCase).collect(Collectors.toList());
    }

    private Map<String, Set<String>> fetchAllColumn1(String sql) {
        return facelessDataSources
                .plains().entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> new HashSet<>(new JdbcTemplate(e.getValue())
                                        .query(sql, (rs, i) -> rs.getString(1).toLowerCase())
                                )
                        )
                );

    }

    public static void testcaseNotice(String... mes) {
        for (String s : mes) {
            logger.info(">>=>🦁🦁🦁 " + s + " 🦁🦁🦁<=<<");
        }
    }

    public static void breakpointDebug(String... mes) {
        Arrays.stream(mes).forEach(s -> logger.debug(">>=>🐶🐶🐶 " + s + " 🐶🐶🐶<=<<"));
    }
}
