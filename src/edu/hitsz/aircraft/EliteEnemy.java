package edu.hitsz.aircraft;
import java.util.Random;
import java.util.stream.DoubleStream;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.CircularStrategy;
import edu.hitsz.strategy.StraightStrategy;
import edu.hitsz.strategy.Strategy;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractEnemy {

    private static BloodpropFactory bloodfactory = new BloodpropFactory();
    private static BombpropFactory bombfactory = new BombpropFactory();
    private static BulletpropFactory bulletfactory = new BulletpropFactory();
    private static BulletpluspropFactory bulletplusfactory = new BulletpluspropFactory();
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int mode,int time) {
        super(locationX, locationY, speedX, speedY, hp,mode,time);
        shootNum= 1;
        direction=1;
         score=20;
         power=10;
        strategy=new StraightStrategy();
        setMode(mode);
        enforce(time);
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
        return this.strategy.shoot(this);
    }

    public void adjustspeed(){;}
    public void createprop(List<Baseprop> props) {
        Random rand = new Random();
        double randouble = rand.nextDouble();

        if (randouble < 0.1) {
            bloodfactory.init(this);
            props.add(bloodfactory.createprop());
        } else if (randouble < 0.2) {
            bombfactory.init(this);
            props.add(bombfactory.createprop());
        } else if (randouble < 0.3) {
            bulletfactory.init(this);
            props.add(bulletfactory.createprop());
        }
        else if (randouble < 0.4) {
            bulletplusfactory.init(this);
            props.add(bulletplusfactory.createprop());
        }
    }
    public void update()
    {
        this.vanish();
    }
    private void enforce(int time)
    {
        //生命值提升
        this.hp=Math.min(time/4000+this.hp,300);
        setMaxHp(this.hp);
        //速度提升
        this.speedY=Math.min(time/4000+this.speedY,8);

    }

    public void setMode(int mode)
    {
        if(mode==1)
        {

        }
        else if(mode==2)
        {
            this.hp+=50;
        }
        else if(mode==3)
        {
            this.hp+=100;
        }
        setMaxHp(this.hp);
    }
}
