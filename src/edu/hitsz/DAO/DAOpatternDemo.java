package edu.hitsz.DAO;

import java.util.List;

public class DAOpatternDemo {

    public static void main(String[] args) {
        int[] scores = {1000, 200, 300};
        RankList rankList = new RankList();
        //演示更新
        for (int score : scores) {
            //演示新增记录
            Record record = rankList.createRecord("username", score);
            rankList.updateRecord(record);
        }
        System.out.println("添加名为username的记录后");
        rankList.printRecord();

        //演示查找
        List<Record> records=rankList.getRecordsById("username");
        System.out.println("所有名为username的记录：");
        for(Record record:records)
        {
            System.out.println(record);}

        //演示删除
        rankList.deleteRecord("username");
        System.out.println("删除所有名为username的记录后：");
        rankList.printRecord();
    }
}