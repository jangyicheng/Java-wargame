package edu.hitsz.application;

import edu.hitsz.DAO.RankListGUI;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HardGame extends Game{
    public HardGame(Boolean sound)
    {
        super(sound);
        Mode=3;
        background=ImageManager.BACKGROUND_IMAGE3;
        mobfactory.setMode(3);
        elitefactory.setMode(3);
        eliteplusfactory.setMode(3);
        bossfactory.setMode(3);
        //可以调整难度的方式：
        //血量、敌机攻速、速度：传入工厂进行修改
        //敌机最大数量，精英敌机产生概率,boss机得分阈值，直接修改
        enemyMaxNumber=5;
        mobprob=0.5;
        eliteprob=0.5;
        bosscore=2000;
    }
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                enforcenemy();
                enforceboss();
                System.out.println(time);
                // 新敌机产生
                createEnemy();
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();
            // 飞机移动
            aircraftsMoveAction();
            // 横向移动
            adjustspeed();
            //道具移动
            propsMoveAction();
            // 撞击检测
            crashCheckAction();
            // 后处理
            postProcessAction();
            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                music.pauseThread();
                bgm_boss.pauseThread();
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                SwingUtilities.invokeLater(() -> {
                    RankListGUI rankListGUI = new RankListGUI(3);
                    rankListGUI.setVisible(true);
                    rankListGUI.updateRecord(score);
                });
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        if(Sound) {
            executorService.execute(music);
            executorService.execute(bgm_boss);
            bgm_boss.pauseThread();
        }

    }
    protected  void createEnemy(){
        Random random = new Random();
        double rand = random.nextDouble();
        double times;
        if(tempscore>bosscore & bosswar==false & rand<0.2)
        {
            bosswar=true;
            bosscount++;
            times=Math.min(bosscount*500+2000,4000)/2000.0;
            System.out.println("Boss敌机增强！血量增长倍率："+times);
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
    protected void enforcenemy()
    {
        double times;
        if(time%1000==0)
        {mobfactory.adjust(time);
            elitefactory.adjust(time);
            eliteplusfactory.adjust(time);
            enemyMaxNumber=Math.min(enemyMaxNumber+time/8000,7);
            eliteprob=Math.min(eliteprob+(double)time/200000.0,0.9);
            times=Math.min(5+(double)time/4000.0,8.0)/5.0;
            System.out.println("提高难度！最大敌机数量："+enemyMaxNumber+",精英敌机概率:"+eliteprob+",敌机属性提升："+times);
        }
    }
    protected void enforceboss()
    {
        bossfactory.adjust(bosscount);
    }

}
