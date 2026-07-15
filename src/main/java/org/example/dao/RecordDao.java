package org.example.dao;

import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import java.awt.color.ICC_ColorSpace;
import java.util.*;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record("Take a shower", RecordStatus.ACTIVE),
                    new Record("Buy flowers", RecordStatus.ACTIVE),
                    new Record("Go to the gym", RecordStatus.DONE)
            )
    );

    public List<Record> findAllRecords() {
        return new ArrayList<>(records);
    }

    public void saveRecord(Record record) {
        records.add(record);
    }

    public void updateRecordStatus (int id, RecordStatus newStatus) {
        for (Record item : records) {
            if (item.getId() == id) {
                item.setStatus(newStatus);
                break;
            }
        }
    }

    public void deleteRecord(int id) {
        records.removeIf(item -> item.getId() == id);
    }

    public List<Record> findByStatus (RecordStatus status) {
        List<Record> filteredRecords = new ArrayList<>();
        for (Record item : records) {
            if (item.getStatus() == status) {
                filteredRecords.add(item);
            }
        }
        return filteredRecords;
    }
}
