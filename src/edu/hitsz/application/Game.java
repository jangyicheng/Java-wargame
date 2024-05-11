package edu.hitsz.application;

import edu.hitsz.DAO.RankList;
import edu.hitsz.DAO.RankListGUI;
import edu.hitsz.DAO.Record;
import edu.hitsz.Music.MusicThread;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JOptionPane;
/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemy> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<Baseprop> props;

    private MobEnemyFactory mobfactory=new MobEnemyFactory();
    private EliteEnemyFactory elitefactory=new EliteEnemyFactory();
    private BossEnemyFactory bossfactory=new BossEnemyFactory();
    private EliteplusEnemyFactory eliteplusfactory=new EliteplusEnemyFactory();

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;
    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private boolean bosswar = false;
    private boolean gameOverFlag=false;
    /**
     * 游戏结束标志
     */
    private Integer Mode;
    private Boolean Sound;
    private BufferedImage background=ImageManager.BACKGROUND_IMAGE1;
    Thread music=new MusicThread("src/videos/bgm.wav",2);
    private Thread bgm_boss=new MusicThread("src/videos/bgm_boss.wav");

    public Game() {
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(8,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                    createEnemy();
//                }
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

                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");


                SwingUtilities.invokeLater(() -> {
                    RankListGUI rankListGUI = new RankListGUI((int)Mode);
                    rankListGUI.setVisible(true);
                    rankListGUI.updateRecord(score);
                });
                }



        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Choice window = new Choice(Mode, Sound, new ChoiceCallback() {
                    @Override
                    public void onChoiceConfirmed(Integer mode,Boolean sound) {
                        Mode=mode;
                        if(mode.equals(1))
                        background=ImageManager.BACKGROUND_IMAGE1;
                        else if (mode.equals(2)) {
                            background=ImageManager.BACKGROUND_IMAGE2;
                        }
                        else {background=ImageManager.BACKGROUND_IMAGE3;}
                        Sound=sound;
                        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
                        if(Sound) {
                            executorService.execute(music);
                        }

                    }
                });
                window.setVisible(true);
            }
        });

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft enemy : enemyAircrafts) {
            enemyBullets.addAll(enemy.shoot());}
            // 英雄射击
          heroBullets.addAll(heroAircraft.shoot());

    }
    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
    for(Baseprop prop:props){
        prop.forward();
    }
    }
    //我添加的
    private  void createEnemy()
    {
        Random random = new Random();
        double rand = random.nextDouble();

        if(score>200 & bosswar==false & rand<0.2)
        {   bosswar=true;
            //bgm_boss.start();
            enemyAircrafts.add(bossfactory.createEnemy());}
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if(rand>0.5){
                enemyAircrafts.add(mobfactory.createEnemy());

            }
            else if(rand>0.1){
                enemyAircrafts.add(elitefactory.createEnemy());}
            else
            {enemyAircrafts.add(eliteplusfactory.createEnemy());}

        }
    }
    private void adjustspeed(){
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            if(enemyAircraft instanceof EliteplusEnemy||enemyAircraft instanceof BossEnemy)
            {
                enemyAircraft.adjustspeed();
            }
        }
    }



    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                if (Sound)
                    new MusicThread("src/videos/bullet_hit.wav").start();
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                }
            }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    if (Sound)
                        new MusicThread("src/videos/bullet_hit.wav").start();
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if(enemyAircraft instanceof BossEnemy)
                        {
                            bosswar=false;
                        //bgm_boss.interrupt();
                             }
                        // TODO 获得分数，产生道具补给
                        score += enemyAircraft.getScore();
                        enemyAircraft.createprop(props);
                        }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    if (Sound)
                        new MusicThread("src/videos/game_over.wav").start();
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);

                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for(Baseprop prop: props)
        {
            if(prop.crash(heroAircraft)||heroAircraft.crash(prop))
            {
                if (Sound)
                    new MusicThread("src/videos/get_supply.wav").start();
                prop.vanish();
                prop.apply();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    //绘制所有物体
    public void paint(Graphics g) {
        super.paint(g);


        // 绘制背景,图片滚动
        g.drawImage(background, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(background, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);
        for (AbstractEnemy enemy:enemyAircrafts)
        {
            BufferedImage image = enemy.getImage();
            int barWidth = image.getWidth(); // 血条宽度与飞行物图片宽度相同
            int barHeight = 5; // 血条高度
            int barX = enemy.getLocationX() - barWidth / 2; // 血条横坐标与飞行物中心对齐
            int barY = enemy.getLocationY() - image.getHeight() / 2 - barHeight ; // 血条纵坐标在飞行物上方一段距离

            double healthRatio = (double) enemy.getHp() / enemy.getMaxHp(); // 血量比例
            int barFillWidth = (int) (barWidth * healthRatio); // 血条填充的宽度
            // 绘制血条背景
            g.setColor(Color.GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);
            // 绘制血条
            if(healthRatio<0.5){
            g.setColor(Color.RED);
           }
            else
            {
                g.setColor(Color.GREEN);
            }
            g.fillRect(barX, barY, barFillWidth, barHeight);
        }
        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    //绘制每一帧的飞行物体
    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);

        }

    }


    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


    public static String getUsername() {
            String username = JOptionPane.showInputDialog(null, "请输入您的姓名:", "输入姓名", JOptionPane.PLAIN_MESSAGE);
            return username;
        }


}
