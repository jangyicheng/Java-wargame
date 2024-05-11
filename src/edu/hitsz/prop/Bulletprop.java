package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;

import java.util.Timer;
import java.util.TimerTask;

public class Bulletprop extends Baseprop {

    public Bulletprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {
        heroAircraft.setStrategy("Scatter");
        System.out.println("FireSupply active!");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                heroAircraft.setStrategy("Straight");
            }
        }, 5000); // 5秒后执行
    }


}
