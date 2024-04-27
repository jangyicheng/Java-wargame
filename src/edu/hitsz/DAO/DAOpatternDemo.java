package edu.hitsz.DAO;

public class DAOpatternDemo {

    public static void main(String[] args) {
        int[] scores = {1000, 200, 300};
        RankList rankList = new RankList();
        for (int score : scores) {
            Record record = rankList.createRecord("username", score);
            rankList.updateRecord(record);
        }
        rankList.deleteRecord("username");
        rankList.printRecord();
    }
}