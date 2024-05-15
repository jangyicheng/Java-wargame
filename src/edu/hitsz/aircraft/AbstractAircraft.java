package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.*;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected int power;
    protected int shootNum;
    protected int direction;
    public Strategy strategy;
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        hp=Math.min(hp,maxHp);
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }
    public int getMaxHp() {
        return maxHp;
    }
    public void setMaxHp(int maxhp){this.maxHp=maxhp;}
    public int getPower() {
        return power;
    }
    public int getShootnum(){return shootNum;}
    public int getDirection(){return direction;}
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    public void setStrategy(String str)
        {
            if(str.equals("Circular"))
            {this.strategy=new CircularStrategy();}
            else if (str.equals("Null")) {
                this.strategy=new NullStrategy();
            } else if (str.equals("Scatter")) {
                this.strategy=new ScatterStrategy();
            }
            else if(str.equals("Straight")){
                this.strategy=new StraightStrategy();
            }
            else
            {throw new RuntimeException("Wrong Strategy!");}
        }
}


