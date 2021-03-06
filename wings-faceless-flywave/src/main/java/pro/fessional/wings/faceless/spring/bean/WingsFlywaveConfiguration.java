package pro.fessional.wings.faceless.spring.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.fessional.wings.faceless.database.FacelessDataSources;
import pro.fessional.wings.faceless.flywave.SchemaDefinitionLoader;
import pro.fessional.wings.faceless.flywave.SchemaFulldumpManager;
import pro.fessional.wings.faceless.flywave.SchemaJournalManager;
import pro.fessional.wings.faceless.flywave.SchemaRevisionManager.AskType;
import pro.fessional.wings.faceless.flywave.SchemaShardingManager;
import pro.fessional.wings.faceless.flywave.SqlSegmentProcessor;
import pro.fessional.wings.faceless.flywave.SqlStatementParser;
import pro.fessional.wings.faceless.flywave.impl.DefaultRevisionManager;
import pro.fessional.wings.faceless.flywave.impl.MySqlStatementParser;
import pro.fessional.wings.faceless.flywave.impl.MysqlDefinitionLoader;
import pro.fessional.wings.faceless.spring.conf.WingsFlywaveSqlProperties;
import pro.fessional.wings.faceless.spring.conf.WingsFlywaveVerProperties;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * @author trydofor
 * @since 2019-06-01
 */
@Configuration
@ConditionalOnProperty(name = "spring.wings.flywave.enabled", havingValue = "true")
public class WingsFlywaveConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(WingsFlywaveConfiguration.class);

    @Bean
    @ConditionalOnBean(DataSource.class)
    @ConditionalOnMissingBean
    public FacelessDataSources facelessDataSources(DataSource inuse) {
        logger.info("init bean FacelessDataSources");
        return new FacelessDataSources(Collections.singletonMap("default", inuse), inuse, null, false);
    }

    @Bean
    public SchemaJournalManager schemaJournalManager(
            FacelessDataSources sources,
            SqlStatementParser statementParser,
            SqlSegmentProcessor segmentProcessor,
            SchemaDefinitionLoader schemaDefinitionLoader,
            WingsFlywaveVerProperties properties) {

        SchemaJournalManager.JournalDdl ddl = new SchemaJournalManager.JournalDdl(
                properties.getJournalUpdate(),
                properties.getTriggerUpdate(),
                properties.getJournalDelete(),
                properties.getTriggerDelete()
        );
        return new SchemaJournalManager(sources, statementParser, segmentProcessor, schemaDefinitionLoader, ddl);
    }

    @Bean
    public DefaultRevisionManager schemaVersionManger(
            FacelessDataSources sources,
            SqlStatementParser statementParser,
            SqlSegmentProcessor segmentProcessor,
            SchemaDefinitionLoader schemaDefinitionLoader,
            WingsFlywaveVerProperties properties) {
        DefaultRevisionManager revisionManager = new DefaultRevisionManager(sources, statementParser, segmentProcessor, schemaDefinitionLoader);
        revisionManager.confirmAsk(AskType.Mark, properties.isAskMark());
        revisionManager.confirmAsk(AskType.Undo, properties.isAskUndo());
        revisionManager.confirmAsk(AskType.Drop, properties.isAskDrop());
        for (String s : properties.getDropReg()) {
            revisionManager.addDropRegexp(s);
        }
        return revisionManager;
    }

    @Bean
    public SchemaShardingManager schemaShardingManager(
            FacelessDataSources sources,
            SqlStatementParser statementParser,
            SqlSegmentProcessor segmentProcessor,
            SchemaDefinitionLoader schemaDefinitionLoader) {
        return new SchemaShardingManager(sources, statementParser, segmentProcessor, schemaDefinitionLoader);

    }

    @Bean
    public SchemaFulldumpManager schemaFulldumpManager(
            SqlStatementParser statementParser,
            SchemaDefinitionLoader schemaDefinitionLoader) {
        return new SchemaFulldumpManager(statementParser, schemaDefinitionLoader);
    }

    @Bean
    public SqlStatementParser sqlStatementParser(@Value("${spring.jooq.sql-dialect}") String dialect) {
        if ("mysql".equalsIgnoreCase(dialect)) {
            return new MySqlStatementParser();
        } else {
            throw new IllegalArgumentException("only support mysql");
        }
    }

    @Bean
    public SqlSegmentProcessor sqlSegmentParser(@Value("${spring.jooq.sql-dialect}") String dialect,
                                                WingsFlywaveSqlProperties properties) {
        if ("mysql".equalsIgnoreCase(dialect)) {
            return new SqlSegmentProcessor(properties.getCommentSingle(),
                    properties.getCommentMultiple(),
                    properties.getDelimiterDefault(),
                    properties.getDelimiterCommand());
        } else {
            throw new IllegalArgumentException("only support mysql");
        }
    }

    @Bean
    public SchemaDefinitionLoader schemaDefinitionLoader(@Value("${spring.jooq.sql-dialect}") String dialect) {
        if ("mysql".equalsIgnoreCase(dialect)) {
            return new MysqlDefinitionLoader();
        } else {
            throw new IllegalArgumentException("only support mysql");
        }
    }

    @Bean
    @ConfigurationProperties("wings.flywave.sql")
    public WingsFlywaveSqlProperties sqlProperties() {
        return new WingsFlywaveSqlProperties();
    }

    @Bean
    @ConfigurationProperties("wings.flywave.ver")
    public WingsFlywaveVerProperties verProperties() {
        return new WingsFlywaveVerProperties();
    }
}
