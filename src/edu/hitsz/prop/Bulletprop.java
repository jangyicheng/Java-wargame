package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Bulletprop extends Baseprop{
    public Bulletprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);


    }

    public void apply()
    {
        System.out.println("FireSupply" +
                "active!");
    }
}
