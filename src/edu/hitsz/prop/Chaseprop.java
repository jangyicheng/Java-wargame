package edu.hitsz.prop;

import edu.hitsz.strategy.ScatterStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class Chaseprop extends Baseprop{

    public Chaseprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {
        heroAircraft.Ischase=true;
        System.out.println("Chasebullet active!");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                heroAircraft.Ischase=false;
            }
        }, 15000); // 20秒后执行
    }
}
