package edu.hitsz.application;

import edu.hitsz.DAO.RankListGUI;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EasyGame extends Game{
    public EasyGame(Boolean sound)
    {
        super(sound);
        Mode=1;
        background=ImageManager.BACKGROUND_IMAGE1;
        mobfactory.setMode(1);
        elitefactory.setMode(1);
        eliteplusfactory.setMode(1);
        bossfactory.setMode(1);
        //可以调整难度的方式：
        //血量、敌机攻速、速度：传入工厂进行修改
        //敌机最大数量，精英敌机产生概率,boss机得分阈值，直接修改
        eliteprob=0.5;
        bosscore=500;
    }

    protected  void createEnemy(){
        Random random = new Random();
        double rand = random.nextDouble();
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if(rand>eliteprob){
                enemyAircrafts.add(mobfactory.createEnemy());
            }
            else if(rand<0.3){
                enemyAircrafts.add(elitefactory.createEnemy());}
            else
            {enemyAircrafts.add(eliteplusfactory.createEnemy());}

        }

    }
    protected void enforcEnemy()
    {

    }
    protected void  adjustParam()
    {

    }

}
