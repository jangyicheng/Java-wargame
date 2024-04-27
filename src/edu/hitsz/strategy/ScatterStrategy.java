package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterStrategy implements Strategy{
    private int locationX;
    private int locationY;
    private int speedX;
    private int speedY;
    private int speed=10;
    private int shootNum;
    private int power;

    public ScatterStrategy(){}

    public List<BaseBullet> shoot(AbstractAircraft aircraft)
    {
        locationX=aircraft.getLocationX();
        locationY=aircraft.getLocationY();
        power=aircraft.getPower();
        speedX=aircraft.getSpeedX();
        speedY=aircraft.getSpeedY();
        shootNum=3;

        List<BaseBullet> res = new LinkedList<>();
        int x = this.locationX;
        int y = this.locationY;//+ direction*20;
        BaseBullet bullet;
        double[] angle = {Math.PI/2,3*Math.PI/7,4*Math.PI/7};

        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            speedX =(int)(speed*Math.cos(angle[i]));
            speedY =(int)(speed*Math.sin(angle[i]));
            if(aircraft instanceof AbstractEnemy)
            bullet = new EnemyBullet(x , y, speedX, speedY, power);
            else
            { bullet = new HeroBullet(x , y, speedX, -speedY, power);}
            res.add(bullet);
        }
        return res;
    }

}
