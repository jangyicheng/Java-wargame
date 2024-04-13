package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;

public class BloodpropFactory extends PropFactory{
        public BloodpropFactory(){}

        public Bloodprop createprop(){return new
        Bloodprop(locationX, locationY, speedX, speedY);}
}
