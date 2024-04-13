package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemy;

public class BombpropFactory extends PropFactory{

    public BombpropFactory(){}
    public Bombprop createprop(){return new
            Bombprop(locationX, locationY, speedX, speedY);}
}
