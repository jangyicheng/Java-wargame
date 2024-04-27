package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.CircularStrategy;
import edu.hitsz.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractEnemy {


    private int speed = 8 + speedY;

    //public Strategy strategy=new CircularStrategy();
    private static BloodpropFactory bloodfactory = new BloodpropFactory();
    private static BombpropFactory bombfactory = new BombpropFactory();
    private static BulletpropFactory bulletfactory = new BulletpropFactory();
    private static BulletpluspropFactory bulletplusfactory = new BulletpluspropFactory();
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum=20;
        this.power=5;
        this.score=500;
        this.direction=1;
        strategy=new CircularStrategy();
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return this.strategy.shoot(this);
    }

    public int getScore() {
        return score;
    }

    public void adjustspeed() {
        this.speedX += (int) (0.02 * (-locationX + 0.5 * (Main.WINDOW_WIDTH)));
    }

    public void createprop(List<Baseprop> props) {
        for (int i = 0; i < 3; i++) {
            Random rand = new Random();
            double randouble = rand.nextDouble();

            if (randouble < 0.2) {
                bloodfactory.init(this);
                props.add(bloodfactory.createprop());
            } else if (randouble < 0.4) {
                bombfactory.init(this);
                props.add(bombfactory.createprop());
            } else if (randouble < 0.6) {
                bulletfactory.init(this);
                props.add(bulletfactory.createprop());
            }
            else if (randouble < 0.8) {
                bulletplusfactory.init(this);
                props.add(bulletplusfactory.createprop());
            }
        }
    }
}
