package edu.hitsz.aircraft;

public interface EnemyFactory {

   AbstractEnemy createEnemy();
    void adjust(int time);
    void setMode(int mode);

}




