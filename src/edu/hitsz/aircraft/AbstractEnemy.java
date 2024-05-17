package edu.hitsz.aircraft;

import edu.hitsz.prop.Baseprop;

import java.util.List;

public abstract class AbstractEnemy extends AbstractAircraft  implements Observer{
    private int mode;
    private int time;

    protected int score;
    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int mode,int time){
        super(locationX, locationY, speedX, speedY, hp);
        this.mode=mode;
        this.time=time;
    }
    public int getScore() {
        return score;
    }

    public abstract void adjustspeed();
   public  abstract  void createprop(List<Baseprop> props);
   public abstract void setMode(int mode);
   public abstract void update();

}
