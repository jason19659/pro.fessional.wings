/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.example.database.autogen.tables.records;


import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;

import pro.fessional.wings.example.database.autogen.tables.WinAuthRoleTable;
import pro.fessional.wings.example.database.autogen.tables.interfaces.IWinAuthRole;


/**
 * The table <code>wings_example.win_auth_role</code>.
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
@Entity
@Table(name = "win_auth_role", uniqueConstraints = {
    @UniqueConstraint(name = "KEY_win_auth_role_PRIMARY", columnNames = {"id"})
})
public class WinAuthRoleRecord extends UpdatableRecordImpl<WinAuthRoleRecord> implements Record9<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Integer, String, String, String>, IWinAuthRole {

    private static final long serialVersionUID = -938583775;

    /**
     * Setter for <code>win_auth_role.id</code>.
     */
    @Override
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>win_auth_role.id</code>.
     */
    @Id
    @Column(name = "id", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>win_auth_role.create_dt</code>.
     */
    @Override
    public void setCreateDt(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>win_auth_role.create_dt</code>.
     */
    @Column(name = "create_dt", nullable = false)
    @Override
    public LocalDateTime getCreateDt() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>win_auth_role.modify_dt</code>.
     */
    @Override
    public void setModifyDt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>win_auth_role.modify_dt</code>.
     */
    @Column(name = "modify_dt", nullable = false)
    @Override
    public LocalDateTime getModifyDt() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>win_auth_role.delete_dt</code>.
     */
    @Override
    public void setDeleteDt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>win_auth_role.delete_dt</code>.
     */
    @Column(name = "delete_dt", nullable = false)
    @Override
    public LocalDateTime getDeleteDt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>win_auth_role.commit_id</code>.
     */
    @Override
    public void setCommitId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>win_auth_role.commit_id</code>.
     */
    @Column(name = "commit_id", nullable = false, precision = 19)
    @NotNull
    @Override
    public Long getCommitId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>win_auth_role.role_type</code>.
     */
    @Override
    public void setRoleType(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>win_auth_role.role_type</code>.
     */
    @Column(name = "role_type", nullable = false, precision = 10)
    @NotNull
    @Override
    public Integer getRoleType() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>win_auth_role.role_name</code>.
     */
    @Override
    public void setRoleName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>win_auth_role.role_name</code>.
     */
    @Column(name = "role_name", nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    @Override
    public String getRoleName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>win_auth_role.desc</code>.
     */
    @Override
    public void setDesc(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>win_auth_role.desc</code>.
     */
    @Column(name = "desc", nullable = false, length = 200)
    @Size(max = 200)
    @Override
    public String getDesc() {
        return (String) get(7);
    }

    /**
     * Setter for <code>win_auth_role.auth_set</code>.
     */
    @Override
    public void setAuthSet(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>win_auth_role.auth_set</code>.
     */
    @Column(name = "auth_set", nullable = false, length = 3000)
    @NotNull
    @Size(max = 3000)
    @Override
    public String getAuthSet() {
        return (String) get(8);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Integer, String, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    @Override
    public Row9<Long, LocalDateTime, LocalDateTime, LocalDateTime, Long, Integer, String, String, String> valuesRow() {
        return (Row9) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return WinAuthRoleTable.WinAuthRole.Id;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return WinAuthRoleTable.WinAuthRole.CreateDt;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return WinAuthRoleTable.WinAuthRole.ModifyDt;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return WinAuthRoleTable.WinAuthRole.DeleteDt;
    }

    @Override
    public Field<Long> field5() {
        return WinAuthRoleTable.WinAuthRole.CommitId;
    }

    @Override
    public Field<Integer> field6() {
        return WinAuthRoleTable.WinAuthRole.RoleType;
    }

    @Override
    public Field<String> field7() {
        return WinAuthRoleTable.WinAuthRole.RoleName;
    }

    @Override
    public Field<String> field8() {
        return WinAuthRoleTable.WinAuthRole.Desc;
    }

    @Override
    public Field<String> field9() {
        return WinAuthRoleTable.WinAuthRole.AuthSet;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public LocalDateTime component2() {
        return getCreateDt();
    }

    @Override
    public LocalDateTime component3() {
        return getModifyDt();
    }

    @Override
    public LocalDateTime component4() {
        return getDeleteDt();
    }

    @Override
    public Long component5() {
        return getCommitId();
    }

    @Override
    public Integer component6() {
        return getRoleType();
    }

    @Override
    public String component7() {
        return getRoleName();
    }

    @Override
    public String component8() {
        return getDesc();
    }

    @Override
    public String component9() {
        return getAuthSet();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public LocalDateTime value2() {
        return getCreateDt();
    }

    @Override
    public LocalDateTime value3() {
        return getModifyDt();
    }

    @Override
    public LocalDateTime value4() {
        return getDeleteDt();
    }

    @Override
    public Long value5() {
        return getCommitId();
    }

    @Override
    public Integer value6() {
        return getRoleType();
    }

    @Override
    public String value7() {
        return getRoleName();
    }

    @Override
    public String value8() {
        return getDesc();
    }

    @Override
    public String value9() {
        return getAuthSet();
    }

    @Override
    public WinAuthRoleRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value2(LocalDateTime value) {
        setCreateDt(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value3(LocalDateTime value) {
        setModifyDt(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value4(LocalDateTime value) {
        setDeleteDt(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value5(Long value) {
        setCommitId(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value6(Integer value) {
        setRoleType(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value7(String value) {
        setRoleName(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value8(String value) {
        setDesc(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord value9(String value) {
        setAuthSet(value);
        return this;
    }

    @Override
    public WinAuthRoleRecord values(Long value1, LocalDateTime value2, LocalDateTime value3, LocalDateTime value4, Long value5, Integer value6, String value7, String value8, String value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IWinAuthRole from) {
        setId(from.getId());
        setCreateDt(from.getCreateDt());
        setModifyDt(from.getModifyDt());
        setDeleteDt(from.getDeleteDt());
        setCommitId(from.getCommitId());
        setRoleType(from.getRoleType());
        setRoleName(from.getRoleName());
        setDesc(from.getDesc());
        setAuthSet(from.getAuthSet());
    }

    @Override
    public <E extends IWinAuthRole> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WinAuthRoleRecord
     */
    public WinAuthRoleRecord() {
        super(WinAuthRoleTable.WinAuthRole);
    }

    /**
     * Create a detached, initialised WinAuthRoleRecord
     */
    public WinAuthRoleRecord(Long id, LocalDateTime createDt, LocalDateTime modifyDt, LocalDateTime deleteDt, Long commitId, Integer roleType, String roleName, String desc, String authSet) {
        super(WinAuthRoleTable.WinAuthRole);

        set(0, id);
        set(1, createDt);
        set(2, modifyDt);
        set(3, deleteDt);
        set(4, commitId);
        set(5, roleType);
        set(6, roleName);
        set(7, desc);
        set(8, authSet);
    }
}
