package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements  EnemyFactory{
    @Override
    public BossEnemy createEnemy() {
        BossEnemy bossEnemy= new BossEnemy(
                (int) (0.6 * (Main.WINDOW_WIDTH)),
                (int) (  Main.WINDOW_HEIGHT * 0.2),
                0,
                0,
                2000
        );

        return bossEnemy;
    }
}
