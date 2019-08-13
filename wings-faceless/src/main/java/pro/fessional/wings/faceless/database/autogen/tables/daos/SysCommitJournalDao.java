/*
 * This file is generated by jOOQ.
 */
package pro.fessional.wings.faceless.database.autogen.tables.daos;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pro.fessional.wings.faceless.database.autogen.tables.SysCommitJournalTable;
import pro.fessional.wings.faceless.database.autogen.tables.pojos.SysCommitJournal;
import pro.fessional.wings.faceless.database.autogen.tables.records.SysCommitJournalRecord;


/**
 * The table <code>wings.SYS_COMMIT_JOURNAL</code>.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11",
        "schema version:2019052001"
    },
    date = "2019-08-13T12:54:19.706Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class SysCommitJournalDao extends DAOImpl<SysCommitJournalRecord, SysCommitJournal, Long> {

    /**
     * Create a new SysCommitJournalDao without any configuration
     */
    public SysCommitJournalDao() {
        super(SysCommitJournalTable.SYS_COMMIT_JOURNAL, SysCommitJournal.class);
    }

    /**
     * Create a new SysCommitJournalDao with an attached configuration
     */
    @Autowired
    public SysCommitJournalDao(Configuration configuration) {
        super(SysCommitJournalTable.SYS_COMMIT_JOURNAL, SysCommitJournal.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(SysCommitJournal object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<SysCommitJournal> fetchById(Long... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public SysCommitJournal fetchOneById(Long value) {
        return fetchOne(SysCommitJournalTable.SYS_COMMIT_JOURNAL.ID, value);
    }

    /**
     * Fetch records that have <code>CREATE_DT IN (values)</code>
     */
    public List<SysCommitJournal> fetchByCreateDt(LocalDateTime... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.CREATE_DT, values);
    }

    /**
     * Fetch records that have <code>EVENT_NAME IN (values)</code>
     */
    public List<SysCommitJournal> fetchByEventName(String... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.EVENT_NAME, values);
    }

    /**
     * Fetch records that have <code>TARGET_KEY IN (values)</code>
     */
    public List<SysCommitJournal> fetchByTargetKey(String... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.TARGET_KEY, values);
    }

    /**
     * Fetch records that have <code>LOGIN_INFO IN (values)</code>
     */
    public List<SysCommitJournal> fetchByLoginInfo(String... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.LOGIN_INFO, values);
    }

    /**
     * Fetch records that have <code>OTHER_INFO IN (values)</code>
     */
    public List<SysCommitJournal> fetchByOtherInfo(String... values) {
        return fetch(SysCommitJournalTable.SYS_COMMIT_JOURNAL.OTHER_INFO, values);
    }
}
