package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteplusEnemyFactory implements EnemyFactory{

    private int mode;
    private int time;
    public EliteplusEnemyFactory()
    {}
    @Override
    public EliteplusEnemy createEnemy() {
        EliteplusEnemy eliteplusEnemy= new EliteplusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                4,
                200,mode,time
        );
        return  eliteplusEnemy;
    }
    public  void setMode(int mode){this.mode=mode;}
    public  void adjust(int time)
    {
        this.time=time;
    }

}
