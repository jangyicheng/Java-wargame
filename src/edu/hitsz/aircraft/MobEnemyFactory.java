package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory {

    private int mode;
    private int time;
        public MobEnemyFactory(){}
        @Override
        public MobEnemy createEnemy() {
            return new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    5,
                    50,mode,time
            );
        }
    public  void setMode(int mode){this.mode=mode;}
    public  void adjust(int time)
    {
        this.time=time;
    }

}


