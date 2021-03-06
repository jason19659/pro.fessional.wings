/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.example.database.autogen.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row14;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.TableImpl;

import pro.fessional.wings.example.database.autogen.DefaultSchema;
import pro.fessional.wings.example.database.autogen.tables.records.WinUserLoginRecord;
import pro.fessional.wings.faceless.convention.EmptyValue;
import pro.fessional.wings.faceless.database.jooq.WingsAliasTable;
import pro.fessional.wings.faceless.service.lightid.LightIdAware;


/**
 * The table <code>wings_example.win_user_login</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4",
        "schema version:2019070403"
    },
    date = "2020-08-13T07:33:30.191Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WinUserLoginTable extends TableImpl<WinUserLoginRecord> implements WingsAliasTable<WinUserLoginTable>, LightIdAware {

    private static final long serialVersionUID = -2059579822;

    /**
     * The reference instance of <code>win_user_login</code>
     */
    public static final WinUserLoginTable WinUserLogin = new WinUserLoginTable();
    public static final WinUserLoginTable asA2 = WinUserLogin.as("a2");

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WinUserLoginRecord> getRecordType() {
        return WinUserLoginRecord.class;
    }

    /**
     * The column <code>win_user_login.id</code>.
     */
    public final TableField<WinUserLoginRecord, Long> Id = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "主键");

    /**
     * The column <code>win_user_login.create_dt</code>.
     */
    public final TableField<WinUserLoginRecord, LocalDateTime> CreateDt = createField(DSL.name("create_dt"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP(3)", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建日时(系统)");

    /**
     * The column <code>win_user_login.modify_dt</code>.
     */
    public final TableField<WinUserLoginRecord, LocalDateTime> ModifyDt = createField(DSL.name("modify_dt"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1000-01-01 00:00:00.000", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "修改日时(系统)");

    /**
     * The column <code>win_user_login.delete_dt</code>.
     */
    public final TableField<WinUserLoginRecord, LocalDateTime> DeleteDt = createField(DSL.name("delete_dt"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1000-01-01 00:00:00.000", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "标记删除");

    /**
     * The column <code>win_user_login.commit_id</code>.
     */
    public final TableField<WinUserLoginRecord, Long> CommitId = createField(DSL.name("commit_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "提交id");

    /**
     * The column <code>win_user_login.user_id</code>.
     */
    public final TableField<WinUserLoginRecord, Long> UserId = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "客户id:win_user.id");

    /**
     * The column <code>win_user_login.login_type</code>.
     */
    public final TableField<WinUserLoginRecord, Integer> LoginType = createField(DSL.name("login_type"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "登录类别/41201##:邮件|手机|微信|facebook");

    /**
     * The column <code>win_user_login.login_name</code>.
     */
    public final TableField<WinUserLoginRecord, String> LoginName = createField(DSL.name("login_name"), org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "登录名称");

    /**
     * The column <code>win_user_login.login_pass</code>.
     */
    public final TableField<WinUserLoginRecord, String> LoginPass = createField(DSL.name("login_pass"), org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "登录密码及算法，参考SpringSecurity格式");

    /**
     * The column <code>win_user_login.login_salt</code>.
     */
    public final TableField<WinUserLoginRecord, String> LoginSalt = createField(DSL.name("login_salt"), org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "密码加盐");

    /**
     * The column <code>win_user_login.login_para</code>.
     */
    public final TableField<WinUserLoginRecord, String> LoginPara = createField(DSL.name("login_para"), org.jooq.impl.SQLDataType.VARCHAR(2000).nullable(false), this, "登录参数:json格式的第三方参数");

    /**
     * The column <code>win_user_login.auth_code</code>.
     */
    public final TableField<WinUserLoginRecord, String> AuthCode = createField(DSL.name("auth_code"), org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "长期的识别码");

    /**
     * The column <code>win_user_login.bad_count</code>.
     */
    public final TableField<WinUserLoginRecord, Integer> BadCount = createField(DSL.name("bad_count"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "错误次数");

    /**
     * The column <code>win_user_login.status</code>.
     */
    public final TableField<WinUserLoginRecord, Integer> Status = createField(DSL.name("status"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "鉴权状态/41101##:同用户状态");

    /**
     * Create a <code>win_user_login</code> table reference
     */
    public WinUserLoginTable() {
        this(DSL.name("win_user_login"), null);
    }

    /**
     * Create an aliased <code>win_user_login</code> table reference
     */
    public WinUserLoginTable(String alias) {
        this(DSL.name(alias), WinUserLogin);
    }

    /**
     * Create an aliased <code>win_user_login</code> table reference
     */
    public WinUserLoginTable(Name alias) {
        this(alias, WinUserLogin);
    }

    private WinUserLoginTable(Name alias, Table<WinUserLoginRecord> aliased) {
        this(alias, aliased, null);
    }

    private WinUserLoginTable(Name alias, Table<WinUserLoginRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("412/用户登录"));
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<WinUserLoginRecord> getPrimaryKey() {
        return Internal.createUniqueKey(pro.fessional.wings.example.database.autogen.tables.WinUserLoginTable.WinUserLogin, "KEY_win_user_login_PRIMARY", pro.fessional.wings.example.database.autogen.tables.WinUserLoginTable.WinUserLogin.Id);
    }

    @Override
    public List<UniqueKey<WinUserLoginRecord>> getKeys() {
        return Arrays.<UniqueKey<WinUserLoginRecord>>asList(
              Internal.createUniqueKey(pro.fessional.wings.example.database.autogen.tables.WinUserLoginTable.WinUserLogin, "KEY_win_user_login_PRIMARY", pro.fessional.wings.example.database.autogen.tables.WinUserLoginTable.WinUserLogin.Id)
        );
    }

    @Override
    public WinUserLoginTable as(String alias) {
        return new WinUserLoginTable(DSL.name(alias), this);
    }

    @Override
    public WinUserLoginTable as(Name alias) {
        return new WinUserLoginTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserLoginTable rename(String name) {
        return new WinUserLoginTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public WinUserLoginTable rename(Name name) {
        return new WinUserLoginTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Long, Integer, String, String, String, String, String, Integer, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * alias A2
     */
    @Override
    public WinUserLoginTable getAliasTable() {
        return asA2;
    }

    /**
     * The column <code>delete_dt</code> condition
     */
    public final Condition onlyDiedData = DeleteDt.gt(EmptyValue.DATE_TIME);
    public final Condition onlyLiveData = DeleteDt.eq(EmptyValue.DATE_TIME);
    @Override
    public Condition getOnlyDied() {
        return onlyDiedData;
    }
    @Override
    public Condition getOnlyLive() {
        return onlyLiveData;
    }
}
