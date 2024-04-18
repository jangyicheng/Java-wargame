package edu.hitsz.aircraft;


import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory extends EnemyFactory {
        @Override
        public EliteEnemy createEnemy() {
            return new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    5,
                    100
            );
        }
    }
