package edu.hitsz.DAO;
import edu.hitsz.DAO.RankList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class RankListGUI extends JFrame {
    private JTable table;
    JPanel root;
    private DefaultTableModel tableModel;
    private RankList rankList;
    private String mode;
    private JComboBox<String> modeComboBox;
    private  String by;
    public RankListGUI(int mode) {
        if(mode==1)
            this.mode="简单模式";
        else if(mode==2)
            this.mode="普通模式";
        else if(mode==3)
            this.mode="困难模式";
        initialize();
        rankList=new RankList(mode);
        loadRankData();
    }
    private void initialize() {
        setTitle("排行榜");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout());

        tableModel = new DefaultTableModel(new Object[]{"Rank", "ID", "Score", "Time"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100,50,600,500);
        panel1.add(scrollPane);

        JButton deleteButton = new JButton("删除");
        //deleteButton.setBounds(350,550,30,30);
        panel2.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });

        modeComboBox = new JComboBox<>(new String[]{"time", "id","score"});
        //modeComboBox.setBounds(400,550,30,30);
        panel2.add(modeComboBox);

        JButton sortButton = new JButton("排序");
        //sortButton.setBounds(450,550,30,30);
        panel2.add(sortButton);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              sortBy();
            }
        });

        JButton exitButton = new JButton("退出");
        //exitButton.setBounds(500,550,30,30);
        panel2.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JLabel label=new JLabel(this.mode);
       // label.setBounds(350,20,100,50);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20)); // 设置标签的字体大小为 20
        label.setHorizontalAlignment(SwingConstants.CENTER); // 设置标签内容居中对齐
        add(label, BorderLayout.NORTH);
        add(panel1, BorderLayout.CENTER); // 将 panel1 添加到中央区域
        add(panel2, BorderLayout.SOUTH); // 将 panel2 添加到南部区域
        setVisible(true);

    }
    private void loadRankData() {//从当前rankList直接读取数据
        clearTable();
        List<Record> records = rankList.getRankList();
        for (Record record : records) {
            Object[] rowData = new Object[]{record.getRank(), record.getId(), record.getScore(), record.getTime()};
            tableModel.addRow(rowData);
        }
    }
    private void sortBy()
    {   by=(String) modeComboBox.getSelectedItem();
        clearTable();
        List<Record> records = rankList.getRankList();
        rankList.sort(by);
        for (Record record : records) {
            Object[] rowData = new Object[]{record.getRank(), record.getId(), record.getScore(), record.getTime()};
            tableModel.addRow(rowData);
        }
    }
    private void clearTable() {
        tableModel.setRowCount(0);
    }
    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id=(String) table.getValueAt(selectedRow, 1);
            LocalDateTime time=(LocalDateTime) table.getValueAt(selectedRow, 3);
            int hashcode = Objects.hashCode(id)+Objects.hashCode(time);
            int choice = JOptionPane.showConfirmDialog(this, "确认删除该记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                rankList.deleteRecordbyHashcode(hashcode);
            }
        }
        loadRankData();
    }
    public static String getUsername() {
        String username = JOptionPane.showInputDialog(null, "请输入您的姓名:", "输入姓名", JOptionPane.PLAIN_MESSAGE);
        return username;
    }
    public void updateRecord(int score)
    {
        Record record=rankList.createRecord(getUsername(),score);
        rankList.updateRecord(record);
        loadRankData();
    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            RankListGUI rankListGUI = new RankListGUI(2);
            rankListGUI.setVisible(true);
        });
    }
}