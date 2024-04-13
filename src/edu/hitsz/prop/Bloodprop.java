package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Bloodprop extends Baseprop{
    private int blood=50;
    private HeroAircraft heroAircraft;
    public Bloodprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void apply()
    {

        int fullhp=heroAircraft.getMaxHp();
        int temphp=heroAircraft.getHp();
        if(fullhp>temphp+blood)
        {
            heroAircraft.decreaseHp(-blood);
        }
        else{heroAircraft.decreaseHp(temphp-fullhp);}
    }

    public Bloodprop connect(HeroAircraft heroAircraft)
    {
        this.heroAircraft=heroAircraft;
        return this;
    }



}
