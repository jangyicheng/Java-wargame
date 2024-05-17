package edu.hitsz.DAO;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public interface DAO {

    Record createRecord(String userid, int score);
    List<Record> getRecordsById(String userid);
    Record getRecordByHashcode(int Hashcode);
    void deleteRecordbyHashcode(int hashcode);
    void updateRecord(Record record);
    void deleteRecord(String userid);
    void sort(String by);



}
