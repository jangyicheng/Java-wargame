package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.Timer;
import java.util.TimerTask;

public class Invisibleprop extends Baseprop{
    public Invisibleprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void apply() {
        HeroAircraft.visible=false;
        System.out.println("Invisible active!");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                HeroAircraft.visible=true;
            }
        }, 10000); // 5秒后执行
    }

}
