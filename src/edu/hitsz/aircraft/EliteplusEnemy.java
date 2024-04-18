package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteplusEnemy extends AbstractEnemy{

    private int shootNum= 3;
    private int direction=1;
    private int score=20;
    private int hp = 5;
    private int speed =5+speedY;

    private double startx;
    public EliteplusEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        startx=locationX+40*(locationX< Main.WINDOW_HEIGHT*0.5?1:-1);

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
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
//        double[] angle = new double[shootNum];
//
//        for (int i = 0; i < shootNum; i++) {
//            angle[i] = i / (double) (shootNum)*Math.PI/3;
//        }
        double[] angle = {Math.PI/2,3*Math.PI/7,4*Math.PI/7};

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
    public void adjustspeed(){
        this.speedX+=(int)(0.03*(-locationX+startx));
    }

}
