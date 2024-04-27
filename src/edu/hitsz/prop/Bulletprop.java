package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Bulletprop extends Baseprop{

    private HeroAircraft heroAircraft=HeroAircraft.getInstance();
    public Bulletprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);


    }
//    public Bulletprop connect(HeroAircraft heroAircraft)
//    {
//        this.heroAircraft=heroAircraft;
//        return this;
//    }

    public void apply()
    {
        heroAircraft.setStrategy("Scatter");
        System.out.println("FireSupply" +
                "active!");
    }
}
