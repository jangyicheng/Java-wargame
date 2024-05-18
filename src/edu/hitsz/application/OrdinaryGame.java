package edu.hitsz.application;

import edu.hitsz.DAO.RankListGUI;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrdinaryGame extends Game{
    public OrdinaryGame(Boolean sound)
    {
        super(sound);
        Mode=2;
        background=ImageManager.BACKGROUND_IMAGE2;
        mobfactory.setMode(2);
        elitefactory.setMode(2);
        eliteplusfactory.setMode(2);
        bossfactory.setMode(2);
        //可以调整难度的方式：
        //血量、敌机攻速、速度：传入工厂进行修改
        //敌机最大数量，精英敌机产生概率,boss机得分阈值，直接修改
        eliteprob=0.5;
        bosscore=1500;
    }


    protected void createEnemy(){
        Random random = new Random();
        double rand = random.nextDouble();
        if(tempscore>bosscore & bosswar==false & rand<0.2 )
        {   bosswar=true;
            tempscore=0;//清空临时分数
            music.pauseThread();
            bgm_boss.resumeThread();
            enemyAircrafts.add(bossfactory.createEnemy());}
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

        if(time%1000==0)
        {mobfactory.adjust(time);
            elitefactory.adjust(time);
            eliteplusfactory.adjust(time);
            bossfactory.adjust(bosscount);

        }
    }
    protected void  adjustParam()
    {
        double times;
        enemyMaxNumber=Math.min(enemyMaxNumber+time/8000,7);
        eliteprob=Math.min(eliteprob+(double)time/200000.0,0.7);
        cycleDuration=Math.max(cycleDuration-time/100,400);
        times=Math.min(5+(double)time/4000.0,8.0)/5.0;
        System.out.println("提高难度！最大敌机数量："+enemyMaxNumber+",精英敌机概率:"+eliteprob+",敌机属性提升："+times);
    }



}
