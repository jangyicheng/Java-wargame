package edu.hitsz.aircraft;


import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyFactory implements EnemyFactory {
    private int time;
    private int mode;
    public EliteEnemyFactory()
    {}
        @Override
        public EliteEnemy createEnemy() {
            EliteEnemy eliteEnemy= new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                    0,
                    5,
                    100,mode,time
            );
            return eliteEnemy;
        }
    public  void setMode(int mode){this.mode=mode;}
    public  void adjust(int time)
    {
        this.time=time;
    }

    }

