package edu.hitsz.prop;

public class InvisiblepropFactory extends PropFactory{
    public Invisibleprop createprop(){return new
            Invisibleprop(locationX, locationY, speedX, speedY);}
}
