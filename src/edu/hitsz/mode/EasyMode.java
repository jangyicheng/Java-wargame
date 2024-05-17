package edu.hitsz.mode;

import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.EliteplusEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

public class EasyMode extends Mode{
    public EasyMode(int mode, MobEnemyFactory mobfactory, EliteEnemyFactory elitefactory, EliteplusEnemyFactory eliteplusfactory, BossEnemyFactory bossfactory,Integer enemyMaxNumber,Double eliteprob,Integer cycleDuration)
    {
        super(mode,mobfactory,elitefactory,eliteplusfactory,bossfactory,enemyMaxNumber,eliteprob,cycleDuration);
    }
    public void update(int time,int bosscount,Integer cycleDuration,Integer enemyMaxNumber,Double eliteprob)
    {

    }
}
