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
    private DefaultTableModel tableModel;
    private RankList rankList;
    private String mode;
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
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());

        tableModel = new DefaultTableModel(new Object[]{"Rank", "ID", "Score", "Time"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(300, 700, 100, 60);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });
        add(deleteButton);
        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);
    }
    private void loadRankData() {
        clearTable();
        List<Record> records = rankList.getRankList();

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
            RankListGUI rankListGUI = new RankListGUI(0);
            rankListGUI.setVisible(true);
        });
    }
}