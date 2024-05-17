package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;
import edu.hitsz.strategy.CircularStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class Bulletplusprop extends Baseprop {
    private  static int control=0;
    public Bulletplusprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {
        heroAircraft.setStrategy("Circular");
        control++;
        System.out.println("FireplusSupply active!");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                control--;
                if(heroAircraft.strategy instanceof CircularStrategy && control==0){
                    heroAircraft.setStrategy("Straight");
                }
            }
        }, 5000); // 5秒后执行
    }


}
