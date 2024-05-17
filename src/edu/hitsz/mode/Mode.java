package edu.hitsz.mode;

import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.EliteplusEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

public class Mode {
    protected MobEnemyFactory mobfactory;
    protected EliteEnemyFactory elitefactory;
    protected EliteplusEnemyFactory eliteplusfactory;
    protected BossEnemyFactory bossfactory;

    protected int mode;
    protected Integer enemyMaxNumber;
    protected Double eliteprob;
    protected Integer cycleDuration;
    public Mode(int mode,MobEnemyFactory mobfactory,EliteEnemyFactory elitefactory,EliteplusEnemyFactory eliteplusfactory,BossEnemyFactory bossfactory,Integer enemyMaxNumber,Double eliteprob,Integer cycleDuration)
    {
        this.mode=mode;
        this.mobfactory=mobfactory;
        this.elitefactory=elitefactory;
        this.eliteplusfactory=eliteplusfactory;
        this.bossfactory=bossfactory;
        this.eliteprob=eliteprob;
        this.enemyMaxNumber=enemyMaxNumber;
        this.cycleDuration=cycleDuration;
        mobfactory.setMode(mode);
        elitefactory.setMode(mode);
        eliteplusfactory.setMode(mode);
        bossfactory.setMode(mode);
    }

    public void update(int time, int bosscount,Integer cycleDuration,Integer enemyMaxNumber,Double eliteprob) {

    }

}
