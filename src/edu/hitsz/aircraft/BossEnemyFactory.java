package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements  EnemyFactory{
    private int mode;
    private int bosscount;
    public BossEnemyFactory()
    {}
    @Override
    public BossEnemy createEnemy() {
        BossEnemy bossEnemy= new BossEnemy(
                (int) (0.6 * (Main.WINDOW_WIDTH)),
                (int) (  Main.WINDOW_HEIGHT * 0.2),
                0,
                0,
                2000,
                mode,bosscount
        );

        return bossEnemy;
    }
    public  void setMode(int mode){this.mode=mode;}
    public  void adjust(int bosscount)
    {
        this.bosscount=bosscount;
    }
}
