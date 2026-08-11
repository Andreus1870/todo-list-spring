package org.example.dao;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.awt.color.ICC_ColorSpace;
import java.sql.*;
import java.util.*;

@Repository
public class RecordDao {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public RecordDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Record> findAllRecords () {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT r FROM Record r");
            List<Record> records = query.getResultList();

            entityManager.getTransaction().commit();
            return records;
        } catch (Exception exception) {
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }


    public void saveRecord(Record record) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(record);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("UPDATE Record SET status = :newStatus WHERE id = :id");
            query.setParameter("newStatus", newStatus);
            query.setParameter("id", id);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public void deleteRecord(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("DELETE FROM Record WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    public List<Record> findByStatus (RecordStatus status) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery( "FROM Record WHERE status = :status");
            query.setParameter("status", status);
            List<Record> records = query.getResultList();

            entityManager.getTransaction().commit();
            return records;
        } catch (Exception exception) {
            exception.printStackTrace();
            entityManager.getTransaction().rollback();
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }
}
