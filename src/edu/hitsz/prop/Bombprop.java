package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Bombprop extends Baseprop{
    public Bombprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void apply()
    {
        System.out.println("BombSupply active!");
    }
}
