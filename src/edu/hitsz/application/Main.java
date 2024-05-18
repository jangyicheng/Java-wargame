package edu.hitsz.application;

import edu.hitsz.DAO.RankListGUI;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static Integer Mode=1;
    public static Boolean Sound=true;
    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Game[] game = {new EasyGame(true)};
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Choice window = new Choice(Mode, Sound, new ChoiceCallback() {
                    @Override
                    public void onChoiceConfirmed(Integer mode, Boolean sound) {

                        if (mode == 1) {
                            game[0]=(new EasyGame(sound));
                        } else if (mode == 2) {
                            game[0] = new OrdinaryGame(sound);
                        } else if (mode == 3) {
                            game[0] = new HardGame(sound);
                        }
                        frame.add(game[0]);
                        frame.setVisible(true);
                        game[0].action();
                    }
                });
                window.setVisible(true);
            }
        });

            }

}
