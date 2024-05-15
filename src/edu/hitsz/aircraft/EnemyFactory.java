package edu.hitsz.aircraft;

public interface EnemyFactory {

    public  AbstractEnemy createEnemy();
    public  void adjust(int time);
    public  void setMode(int mode);

}




