package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;

import java.util.Timer;
import java.util.TimerTask;

public class Bulletplusprop extends Baseprop {

    public Bulletplusprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {


        heroAircraft.setStrategy("Circular");
        System.out.println("FireplusSupply active!");


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                heroAircraft.setStrategy("Straight");
            }
        }, 5000); // 5秒后执行
    }


}
