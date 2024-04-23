package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemy;

public abstract class PropFactory {

    protected int locationX;
    protected int locationY;
    protected int speedX;
    protected int speedY;
    protected AbstractEnemy enemy;
    public PropFactory() {
    }

    public void init(AbstractEnemy enemy)
    {
        this.enemy=enemy;
        this.locationX = enemy.getLocationX();
        this.locationY = enemy.getLocationY();
        this.speedX = 0;
        this.speedY = Math.max(enemy.getSpeedY(),5);
    }
    public abstract Baseprop createprop();


}