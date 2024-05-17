package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;
import edu.hitsz.strategy.CircularStrategy;
import edu.hitsz.strategy.ScatterStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class Bulletprop extends Baseprop {
    private static int control=0;
    public Bulletprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {
        heroAircraft.setStrategy("Scatter");
        System.out.println("FireSupply active!");
        control++;        //显示当前有几个正在控制的
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                control--;
                if(heroAircraft.strategy instanceof ScatterStrategy && control==0){
                    heroAircraft.setStrategy("Straight");
                   }
            }
        }, 5000); // 5秒后执行
    }


}
