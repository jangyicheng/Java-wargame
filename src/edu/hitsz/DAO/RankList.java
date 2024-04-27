package edu.hitsz.DAO;
import edu.hitsz.strategy.NullStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.time.ZoneId;

public class RankList implements DAO{
    List<Record> ranklist = new ArrayList<>();
    private String DataPath = "data.csv";
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

            System.out.println("CSV 文件写入成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Record parseRecord(String str){
        String[] fields = str.split(",");
        // 假设字段顺序为 id, rank, score, time
        String id = fields[1].trim();
        int rank = Integer.parseInt(fields[0].trim());
        int score = Integer.parseInt(fields[2].trim());
        LocalDateTime time = LocalDateTime.parse(fields[3], DateTimeFormatter.ISO_DATE_TIME);
        Record record = new Record(rank, id, score, time);
        return record;
    }
    @Override
    public Record createRecord(String userid,int score) {//创建一个记录
        return new Record(-1,userid, score,LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

//    @Override
//    public Record getRecordById(String id) {//查数据
//        loadRecord();
//        for(Record record:ranklist)
//        {if(record.getId().equals(id))
//        return record;
//        }
//        return null;
//    }
    public List<Record> getRecordsById(String id) {
        loadRecord();
        List<Record> matchingRecords = new ArrayList<>();

        for (Record record : ranklist) {
            if (record.getId().equals(id)) {
                matchingRecords.add(record);
            }
        }
        for (Record record:matchingRecords)
        {
            System.out.println(record);
        }
        return matchingRecords;
    }
    @Override
    public void updateRecord(Record record){//用当前排行榜更新文件
        int rank=1;
        loadRecord();//读取文件
        for(Record record_:ranklist)//修改排行
        {if(record_.getScore()<record.getScore())
        record_.setRank(record_.getRank()+1);
        else if(record_.getScore()>record.getScore())
        {rank++;}
        else {;}}
        record.setRank(rank);
        ranklist.add(record);
        sort("score");//重新排序
        writeRecord();//写入文件
    }

    @Override
    public void deleteRecord(String id) {
        loadRecord();
        // 实现删除记录的逻辑
        List<Record> records=getRecordsById(id);
        for(Record record:records)
        {   ranklist.remove(record);
            for(Record record_:ranklist)
            {if(record_.getScore()<record.getScore())
                record_.setRank(record_.getRank()+1);
            else {;}}}

        writeRecord();
    }

    @Override
    public void printRecord() {
        loadRecord();
        System.out.println("Rank\tUsername\tScore\t\tTime");
        for (Record record : ranklist) {
            System.out.printf("%-5d\t%-15s\t%-10d\t%s%n", record.getRank(), record.getId(), record.getScore(), record.getTime());
        }
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

    public static void main(String[] args) {
        int []scores={1,2,3,4,5,6,7,8,9};
        LocalDateTime time = LocalDateTime.now();
        RankList rankList=new RankList();
        for (int score:scores)
        { Record record=rankList.createRecord("username",score);
            rankList.updateRecord(record);
        }
        //rankList.deleteRecord("username");
        rankList.printRecord();

    }

}
