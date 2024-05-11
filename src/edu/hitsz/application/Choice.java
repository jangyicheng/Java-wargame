package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choice extends JFrame {
    private JComboBox<String> modeComboBox;
    public Integer Mode;
    public Boolean Sound;
    private ChoiceCallback callback;

    // 构造函数
    public Choice(Integer mode, Boolean sound, ChoiceCallback callback) {
        this.callback = callback;
        setTitle("Swing Window Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 创建三个按钮
        JButton button1 = new JButton("简单模式");
        JButton button2 = new JButton("普通模式");
        JButton button3 = new JButton("困难模式");
        button1.setBounds(125,20,150,80);
        button2.setBounds(125,160,150,80);
        button3.setBounds(125,300,150,80);
        // 创建下拉列表
        modeComboBox = new JComboBox<>(new String[]{"开", "关"});
        JLabel label = new JLabel("音效:");
        //label.setBounds(80,300,50,20);
        // 添加按钮和下拉列表到面板
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(modeComboBox);
        panel.add(label);


        // 添加面板到窗口
        getContentPane().add(panel);

        label.setBounds(60,400,25,20);
        modeComboBox.setBounds(125,400,150,30);
        // 添加按钮的点击事件监听器
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMode = (String) modeComboBox.getSelectedItem();
                System.out.println("Button 1 clicked. Selected mode: " + selectedMode);
                callback.onChoiceConfirmed(1,selectedMode.equals("开"));
                dispose();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMode = (String) modeComboBox.getSelectedItem();
                System.out.println("Button 2 clicked. Selected mode: " + selectedMode);
                callback.onChoiceConfirmed(2,selectedMode.equals("开"));
                dispose();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMode = (String) modeComboBox.getSelectedItem();
                System.out.println("Button 3 clicked. Selected mode: " + selectedMode);
                Mode=3;
                callback.onChoiceConfirmed(3,selectedMode.equals("开"));
                dispose();
            }
        });

    }

    public static void main(String[] args) {
        Integer mode = 1;
        Boolean sound = false;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Choice window = new Choice(mode, sound, new ChoiceCallback() {
                    @Override
                    public void onChoiceConfirmed(Integer mode, Boolean sound) {
                        // 执行下面的代码
                        // ...
                    }
                });

                window.setVisible(true);
            }
        });
    }
}