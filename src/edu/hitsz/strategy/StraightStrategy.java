package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.ChaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class StraightStrategy implements Strategy{

    public StraightStrategy(){}

    public List<BaseBullet> shoot(AbstractAircraft aircraft)
    {
        int locationX=aircraft.getLocationX();
        int locationY=aircraft.getLocationY();
        int power=aircraft.getPower();
        int speedX=aircraft.getSpeedX();
        int speedY=10;
        int direction=aircraft.getDirection();
        int shootNum=aircraft.getShootnum();
        List<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY+ direction*2;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(aircraft instanceof AbstractEnemy)
                bullet = new EnemyBullet(x , y, speedX, speedY, power);
            else if(((HeroAircraft)aircraft).Ischase==false)
            { bullet = new HeroBullet(x , y, speedX, -speedY, power);}
            else
                bullet= new ChaseBullet(x , y, speedX, -speedY, power);
            res.add(bullet);
        }
        return res;
    }

}
