package pro.fessional.wings.faceless.sample;

import pro.fessional.wings.faceless.jooqgen.WingsCodeGenerator;

/**
 * @author trydofor
 * @since 2019-05-31
 */
public class JooqCodeAutoGenSample {

    // 需要 版本 20190601_01，手动执行亦可
    // WingsInitDatabaseSample
    // 注意在目标工程中，应该注释掉.springRepository(false)，使Dao自动加载
    public static void main(String[] args) {
        String database = "wings";
        WingsCodeGenerator.builder()
                          .jdbcDriver("com.mysql.cj.jdbc.Driver")
                          .jdbcUrl("jdbc:mysql://127.0.0.1/" + database)
                          .jdbcUser("trydofor")
                          .jdbcPassword("moilioncircle")
                          .databaseSchema(database)
                          // 支持 pattern的注释写法
                          .databaseIncludes("sys_constant_enum" +
                                  "|sys_standard_i18n" +
                                  "|tst_中文也分表")
                          .databaseVersionProvider("SELECT MAX(revision) FROM sys_schema_version WHERE apply_dt > '1000-01-01'")
                          .targetPackage("pro.fessional.wings.faceless.database.autogen")
                          .targetDirectory("wings-faceless-jooq/src/test/java/")
//                          .springRepository(false)
                          .buildAndGenerate();
    }
}
