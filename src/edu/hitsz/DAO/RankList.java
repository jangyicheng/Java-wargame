package edu.hitsz.DAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import edu.hitsz.DAO.Record.parseRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.time.temporal.ChronoUnit;


public class RankList implements DAO{
    private List<Record> ranklist;

    private String DataPath ;
    public RankList(int mode){
        ranklist = new ArrayList<>();
        if(mode==1) DataPath = "data1.csv";
        else if (mode==2) {
            DataPath = "data2.csv";
        }
        else if(mode==3)
        {    DataPath = "data3.csv";}
        else
        {System.out.println(mode);
            throw new RuntimeException("没有对应模式数据！");}

    }
    Comparator<Record> comparatorbyid = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };
    Comparator<Record> comparatorbyrank = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o2.getRank()-o1.getRank();
        }
    };
    Comparator<Record> comparatorbytime = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o1.getTime().compareTo(o2.getTime());
        }
    };
    Comparator<Record> comparatorbyscore = new Comparator<Record>() {
        @Override
        public int compare(Record o1, Record o2) {
            return o2.getScore()-o1.getScore();
        }
    };

    private void loadRecord()
    {
        Path filePath = Paths.get(DataPath);
        if (!Files.exists(filePath)) {
            try {

                Files.createFile(filePath);
                System.out.println("Created a new file: " + DataPath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(DataPath))) {
            String line;
            boolean headerSkipped = false;
            ranklist.clear();
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    // 跳过标题行
                    headerSkipped = true;
                    continue;
                }
                // 解析每一行数据为 Record 对象
                Record record = parseRecord(line);
                // 将解析后的 Record 对象添加到 ArrayList
                ranklist.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRecord()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DataPath))) {
            // 写入标题行
            writer.write("rank,id,score,time");
            writer.newLine();

            // 遍历 recordList，将每个 Record 对象的属性写入文件
            for (Record record: ranklist) {
                String line = record.getRank()  + "," + record.getId()+ "," + record.getScore() + "," + record.getTime();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Record createRecord(String userid,int score) {//创建一个记录
        return new Record(-1,userid, score,LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
    public List<Record> getRecordsById(String id) {
        loadRecord();
        List<Record> matchingRecords = new ArrayList<>();

        for (Record record : ranklist) {
            if (record.getId().equals(id)) {
                matchingRecords.add(record);
            }
        }
        return matchingRecords;
    }
    public Record getRecordByHashcode(int hashcode) {
        loadRecord();
        for (Record record : ranklist) {
            if (record.getHashcode()==(hashcode)) {
              return record;
            }
        }
        return  null;
    }
    public void deleteRecordbyHashcode(int hashcode) {
        loadRecord();
        Record record=getRecordByHashcode(hashcode);
        ranklist.remove(record);
        for(Record record_:ranklist)
            {if(record_.getScore()<record.getScore())
                record_.setRank(record_.getRank()-1);
            }
        writeRecord();
    }
    @Override
    public void updateRecord(Record record){//用当前排行榜更新文件
        int rank=1;
        loadRecord();//读取文件
        int score1;
        int score2=record.getScore();
        for(Record record_:ranklist)//修改排行
        {score1=record_.getScore();
            if(score1<score2)//当前分数值较小，则名次+1
        record_.setRank(record_.getRank()+1);
        else if(score1>score2)
        {rank++;}//否则新增记录的名次+1
        }
        record.setRank(rank);
        ranklist.add(record);
        sort("score");//重新排序
        writeRecord();//写入文件
    }

    public List<Record> getRankList()
    {
        loadRecord();
        return this.ranklist;
    }
    @Override
    public void deleteRecord(String id) {
        loadRecord();
        List<Record> records=getRecordsById(id);
        for(Record record:records)
        {   ranklist.remove(record);
            for(Record record_:ranklist)
            {if(record_.getScore()<record.getScore())
                record_.setRank(record_.getRank()-1);
            }}

        writeRecord();
    }

    public void printRecord() {
        loadRecord();
        System.out.println("得分排行榜:");
        System.out.println("Rank\tUsername\tScore\t\tTime");
        for (Record record : ranklist) {
            System.out.printf("%-5d\t%-15s\t%-10d\t%s%n", record.getRank(), record.getId(), record.getScore(), record.getTime());
        }
        }
    private Record parseRecord(String str){
        String[] fields = str.split(",");
        // 字段顺序为rank,id score, time
        String id = fields[1].trim();
        int rank = Integer.parseInt(fields[0].trim());
        int score = Integer.parseInt(fields[2].trim());
        LocalDateTime time = LocalDateTime.parse(fields[3], DateTimeFormatter.ISO_DATE_TIME);
        Record record = new Record(rank, id, score, time);
        return record;
    }

    public void sort(String by) {
        Comparator<Record> comparator;
        if(by.equals("id"))
            comparator=comparatorbyid;
        else if (by.equals("score")) {
            comparator=comparatorbyscore;
        } else if (by.equals("rank")) {
            comparator=comparatorbyrank;
        } else if (by.equals("time")) {
            comparator=comparatorbytime;
        }
        else {throw new RuntimeException("can't sort by "+by);}
        Collections.sort(ranklist, comparator);
    }


}
