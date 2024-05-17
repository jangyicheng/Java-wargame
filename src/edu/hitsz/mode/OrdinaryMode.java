package edu.hitsz.mode;

import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.EliteplusEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

public class OrdinaryMode extends Mode{

    public OrdinaryMode(int mode, MobEnemyFactory mobfactory, EliteEnemyFactory elitefactory, EliteplusEnemyFactory eliteplusfactory, BossEnemyFactory bossfactory,Integer enemyMaxNumber,Double eliteprob,Integer cycleDuration)
    {
        super(mode,mobfactory,elitefactory,eliteplusfactory,bossfactory,enemyMaxNumber,eliteprob,cycleDuration);
    }
    public void update(int time,int bosscount,Integer cycleDuration,Integer enemyMaxNumber,Double eliteprob)
    {
        if(time%1000==0)
        {   mobfactory.adjust(time);
            elitefactory.adjust(time);
            eliteplusfactory.adjust(time);
            enemyMaxNumber=Math.max(enemyMaxNumber+time/2000,7);
            eliteprob=Math.max(eliteprob+time/200000,0.15);
            cycleDuration=Math.min(cycleDuration-time/200,400);
        }
    }
}