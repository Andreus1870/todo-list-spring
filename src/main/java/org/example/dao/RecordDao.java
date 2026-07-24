package org.example.dao;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.color.ICC_ColorSpace;
import java.sql.*;
import java.util.*;

@Repository
public class RecordDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecordDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Record> findAllRecords() {
        return jdbcTemplate.query("SELECT * FROM records",
                (rs, rowNum) -> new Record(
                        rs.getInt("id"),
                        rs.getString("title"),
                        RecordStatus.valueOf(rs.getString("status"))
                ));
    }

    public void saveRecord(Record record) {
        jdbcTemplate.update(
                "INSERT INTO records (title, status) VALUES (?, ?)",
                record.getTitle(),
                record.getStatus().name()
        );
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        jdbcTemplate.update(
                "UPDATE records SET status = ? WHERE id = ?",
                newStatus.name(),
                id
        );
    }

    public void deleteRecord(int id) {
        jdbcTemplate.update("DELETE FROM records WHERE id = ?", id);
    }

    public List<Record> findByStatus(RecordStatus status) {
        return jdbcTemplate.query(
                "SELECT * FROM records WHERE status = ?",
                (rs, rowNum) -> new Record(
                        rs.getInt("id"),
                        rs.getString("title"),
                        RecordStatus.valueOf(rs.getString("status"))
                ),
                status.name()
        );
    }
}
