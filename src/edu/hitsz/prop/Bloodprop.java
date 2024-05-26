package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;

public class Bloodprop extends Baseprop{
    private int blood=500;


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



}
