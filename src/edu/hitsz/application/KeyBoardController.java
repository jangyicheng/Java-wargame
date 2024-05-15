package edu.hitsz.application;

import edu.hitsz.aircraft.HeroAircraft;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 英雄机控制类
 * 监听键盘，控制英雄机的移动
 *
 * @author hitsz
 */
public class KeyBoardController {
    private Game game;
    private HeroAircraft heroAircraft;
    private KeyAdapter keyAdapter;

    public KeyBoardController (Game game, HeroAircraft heroAircraft){
        this.game = game;
        this.heroAircraft = heroAircraft;

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                int x = heroAircraft.getLocationX();
                int y = heroAircraft.getLocationY();
                int moveSpeed = 20; // 调整移动速度

                if (keyCode == KeyEvent.VK_LEFT) {
                    x -= moveSpeed;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    x += moveSpeed;
                } else if (keyCode == KeyEvent.VK_UP) {
                    y -= moveSpeed;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    y += moveSpeed;
                }

                // 防止超出边界
                if (x < 0) {
                    x = 0;
                } else if (x > Main.WINDOW_WIDTH) {
                    x = Main.WINDOW_WIDTH;
                }
                if (y < 0) {
                    y = 0;
                } else if (y > Main.WINDOW_HEIGHT) {
                    y = Main.WINDOW_HEIGHT;
                }

                heroAircraft.setLocation(x, y);
            }
        };

        game.addKeyListener(keyAdapter);
        game.setFocusable(true); // 确保游戏窗口可以获取焦点
    }
}