package edu.hitsz.DAO;
import java.time.LocalDateTime;
public class Record {
    private int rank;
    private String userid;
    private int score;
    private LocalDateTime time;
    public Record(int rank,String userid,int score,LocalDateTime time)
    {
    this.userid=userid;
    this.time=time;
    this.score=score;}

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getId() {
        return userid;
    }

    public void setName(String userid) {
        this.userid = userid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
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
}
