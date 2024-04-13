package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemy;

public class BulletpropFactory extends PropFactory{

    public Bulletprop createprop(){return new
            Bulletprop(locationX, locationY, speedX, speedY);}
}
