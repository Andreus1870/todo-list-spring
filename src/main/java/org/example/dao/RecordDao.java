package org.example.dao;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.awt.color.ICC_ColorSpace;
import java.sql.*;
import java.util.*;

@Repository
public class RecordDao {
    private final String url;
    private final String user;
    private final String password;
    public RecordDao(@Value("${db.url}") String url,
                     @Value("${db.user}") String user,
                     @Value("${db.password}")String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public List<Record> findAllRecords() {
        String sql = "SELECT * FROM records";
        List<Record> records = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                records.add(new Record(
                   resultSet.getInt("id"),
                   resultSet.getString("title"),
                   RecordStatus.valueOf(resultSet.getString("status"))
                ));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return records;
    }


    public void saveRecord(Record record) {
        String sql = "INSERT INTO records (title, status) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, record.getTitle());
            stmt.setString(2, record.getStatus().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        String sql = "UPDATE records SET status = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(int id) {
        String sql = "DELETE from records WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Record> findByStatus(RecordStatus status) {
        String sql = "SELECT * FROM records WHERE status = ?";
        List<Record> records = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                records.add(new Record(
                        rs.getInt("id"),
                        rs.getString("title"),
                        RecordStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
