package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;

public class Bombprop extends Baseprop{


    public Bombprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void apply()
    {
        new MusicThread("src/videos/bomb_explosion.wav").start();
        System.out.println("BombSupply active!");
    }
}
