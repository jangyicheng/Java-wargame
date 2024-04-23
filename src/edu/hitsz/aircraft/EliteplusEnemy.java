package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.Baseprop;
import edu.hitsz.prop.BloodpropFactory;
import edu.hitsz.prop.BombpropFactory;
import edu.hitsz.prop.BulletpropFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EliteplusEnemy extends AbstractEnemy{

    private int shootNum= 3;
    private int direction=1;
    private int score=40;
    private int hp = 5;
    private int speed =5+speedY;

    private double startx;
    private static BloodpropFactory bloodfactory = new BloodpropFactory();
    private static BombpropFactory bombfactory = new BombpropFactory();
    private static BulletpropFactory bulletfactory = new BulletpropFactory();
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
    public void createprop(List<Baseprop> props, HeroAircraft heroAircraft) {
            Random rand = new Random();
            double randouble = rand.nextDouble();

            if (randouble < 0.2) {
                bloodfactory.init(this);
                props.add(bloodfactory.createprop().connect(heroAircraft));
            } else if (randouble < 0.4) {
                bombfactory.init(this);
                props.add(bombfactory.createprop());
            } else if (randouble < 0.6) {
                bulletfactory.init(this);
                props.add(bulletfactory.createprop());
            }

    }
}
