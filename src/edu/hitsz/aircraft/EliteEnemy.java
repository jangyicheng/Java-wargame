package edu.hitsz.aircraft;
import java.util.Random;
import java.util.stream.DoubleStream;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.Baseprop;
import edu.hitsz.prop.BloodpropFactory;
import edu.hitsz.prop.BombpropFactory;
import edu.hitsz.prop.BulletpropFactory;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy {
    private int shootNum= 20;
    private int direction=1;
    private int score=20;
    private int hp = 5;
    private static BloodpropFactory bloodfactory = new BloodpropFactory();
    private static BombpropFactory bombfactory = new BombpropFactory();
    private static BulletpropFactory bulletfactory = new BulletpropFactory();
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
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
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;

        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散

            bullet = new EnemyBullet(x , y, speedX, speedY, hp);
            res.add(bullet);
        }
        return res;
    }

    public int getScore() {
        return score;
    }
    public void adjustspeed(){;}
    public void createprop(List<Baseprop> props, HeroAircraft heroAircraft) {
        Random rand = new Random();
        double randouble = rand.nextDouble();

        if (randouble < 0.1) {
            bloodfactory.init(this);
            props.add(bloodfactory.createprop().connect(heroAircraft));
        } else if (randouble < 0.2) {
            bombfactory.init(this);
            props.add(bombfactory.createprop());
        } else if (randouble < 0.3) {
            bulletfactory.init(this);
            props.add(bulletfactory.createprop());
        }
    }
}
