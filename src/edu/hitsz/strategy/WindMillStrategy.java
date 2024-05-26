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

public class WindMillStrategy implements Strategy {

    private double currentAngle; // 当前旋转角度
    private double rotationSpeed; // 每次发射时增加的角度

    public WindMillStrategy() {
        this.currentAngle = 0;
        this.rotationSpeed = Math.PI / 20; // 每次发射旋转18度
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        int locationX = aircraft.getLocationX();
        int locationY = aircraft.getLocationY();
        int power = aircraft.getPower();
        int speed = 10;
        int shootNum = 15; // 子弹数量
        List<BaseBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY;

        double[] angle = new double[shootNum];
        for (int i = 0; i < shootNum; i++) {
            angle[i] = (i / (double) shootNum) * 2 * Math.PI + currentAngle;
        }

        for (int i = 0; i < shootNum; i++) {
            int speedx = (int) (speed * Math.cos(angle[i]));
            int speedy = (int) (speed * Math.sin(angle[i]));

            BaseBullet bullet;
            if (aircraft instanceof AbstractEnemy) {
                bullet = new EnemyBullet(x, y, speedx, speedy, power);
            } else if (((HeroAircraft) aircraft).Ischase == false) {
                bullet = new HeroBullet(x, y, speedx, -speedy, power);
            } else {
                bullet = new ChaseBullet(x, y, speedx, -speedy, power);
            }
            res.add(bullet);
        }

        // 更新当前角度
        currentAngle += rotationSpeed;
        if (currentAngle >= 2 * Math.PI) {
            currentAngle -= 2 * Math.PI;
        }
        return res;
    }
}