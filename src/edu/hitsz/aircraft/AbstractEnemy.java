package edu.hitsz.aircraft;

import edu.hitsz.prop.Baseprop;

public abstract class AbstractEnemy extends AbstractAircraft{

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
    }
    public int score;
    public int getScore() {
        return score;
    }
   // abstract public Baseprop createprop();
   public void adjustspeed(){;}

}