package edu.hitsz.DAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Record {
    private int rank;
    private String userid;
    private int score;
    private LocalDateTime time;
    private int hashcode;
    public Record(int rank,String userid,int score,LocalDateTime time)
    {this.rank=rank;
    this.userid=userid;
    this.time=time;
    this.score=score;
    this.hashcode= Objects.hashCode(time)+Objects.hashCode(userid);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId() {
        return userid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHashcode(){return hashcode;}
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Record{" +
                "rank=" + rank +
                ", userid='" + userid + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
    public Record parseRecord(String str){
        String[] fields = str.split(",");
        // 字段顺序为rank,id score, time
        String id = fields[1].trim();
        int rank = Integer.parseInt(fields[0].trim());
        int score = Integer.parseInt(fields[2].trim());
        LocalDateTime time = LocalDateTime.parse(fields[3], DateTimeFormatter.ISO_DATE_TIME);
        Record record = new Record(rank, id, score, time);
        return record;
    }
}
