package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

import java.util.Random;

public class Factoryprop {
    private AbstractAircraft enemyAircraft;
    public Factoryprop(AbstractAircraft craft)
    {
        enemyAircraft=craft;
    }

    public Baseprop Produceprop(){

        Random rand=new Random();
        double randouble=rand.nextDouble();
        int x=enemyAircraft.getLocationX();
        int y=enemyAircraft.getLocationY();
        int sx=enemyAircraft.getSpeedX();
        int sy=enemyAircraft.getSpeedY();

        if(randouble>0.7)
        {
            return new Bloodprop(x,y,sx,sy);
        } else if (randouble>0.4) {
            return new Bombprop(x,y,sx,sy);
        } else  {
            return new Bulletprop(x,y,sx,sy);
        }

    }


}
