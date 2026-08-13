package org.example.dao;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class RecordDao {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Record> findAllRecords () {
        return entityManager
                .createQuery("SELECT r FROM Record r ORDER BY r.id ASC ", Record.class)
                .getResultList();
    }

    public void saveRecord(Record record) {
        entityManager.persist(record);
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        entityManager
                .createQuery("UPDATE Record  SET status = :newStatus WHERE id = :id ")
                .setParameter("newStatus", newStatus)
                .setParameter("id", id)
                .executeUpdate();
    }

    public void deleteRecord(int id) {
        entityManager
                .createQuery("DELETE FROM Record WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<Record> findByStatus (RecordStatus status) {
        return entityManager
                .createQuery("FROM Record WHERE status = :status", Record.class)
                .setParameter("status", status)
                .getResultList();
    }
}
