package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Bulletplusprop extends Baseprop{
    //private HeroAircraft heroAircraft=HeroAircraft.getInstance();
    public Bulletplusprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);


    }

    public void apply()
    {
        heroAircraft.setStrategy("Circular");
        System.out.println("FireplusSupply" +
                "active!");
    }
}
