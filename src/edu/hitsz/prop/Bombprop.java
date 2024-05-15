package edu.hitsz.prop;

import edu.hitsz.Music.MusicThread;
import edu.hitsz.aircraft.Observer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bombprop extends Baseprop{

    private List<Observer> observers=new LinkedList<>();
    public Bombprop(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    public void apply()
    {
        new MusicThread("src/videos/bomb_explosion.wav").start();
        this.notifyObserver();
        System.out.println("BombSupply active!");
    }
    public void registerObserver(Observer observer)
    {
        this.observers.add(observer);
    }
    public void removeObserver(Observer observer)
    {
        this.observers.add(observer);
    }
    public void notifyObserver()
    {
        for (Observer observer:observers)
        {observer.update();}
    }


}
