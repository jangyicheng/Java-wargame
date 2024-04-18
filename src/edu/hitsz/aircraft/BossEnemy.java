package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.Baseprop;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractEnemy{

    private int shootNum= 20;
    private int direction=1;
    private int score=500;
    private int hp = 5;
    private int speed =8+speedY;


    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX() ;
       int y = this.getLocationY() ;//+ direction*20;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
        double[] angle = new double[shootNum];

        for (int i = 0; i < shootNum; i++) {
            angle[i] = i / (double) (shootNum)*2*Math.PI;
        }
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            speedX =(int)(speed*Math.cos(angle[i]));
            speedY =(int)(speed*Math.sin(angle[i]));

            bullet = new EnemyBullet(x , y, speedX, speedY, hp);
            res.add(bullet);
        }
        return res;
    }

    public int getScore() {
        return score;
    }
    //public Baseprop createprop(){}
    public void adjustspeed()
    {
        this.speedX+=(int)(0.02*(-locationX+0.5 * (Main.WINDOW_WIDTH)));
    }
}
