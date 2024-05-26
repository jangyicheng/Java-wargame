package edu.hitsz.application;

import edu.hitsz.DAO.RankListGUI;
import edu.hitsz.Music.MusicThread;
import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.ChaseBullet;
import edu.hitsz.prop.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {
    protected int backGroundTop = 0;
    /**
     * Scheduled 线程池，用于任务调度
     */
    protected final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected final HeroAircraft heroAircraft;
    protected final List<AbstractEnemy> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<Baseprop> props;

    protected MobEnemyFactory mobfactory=new MobEnemyFactory();
    protected EliteEnemyFactory elitefactory=new EliteEnemyFactory();
    protected BossEnemyFactory bossfactory=new BossEnemyFactory();
    protected EliteplusEnemyFactory eliteplusfactory=new EliteplusEnemyFactory();

    /**
     * 屏幕中出现的敌机最大数量
     */
    //private int enemyMaxNumber = 5;
    protected Integer enemyMaxNumber = 5;
    /**
     * 当前得分
     */
    protected int score = 0;
    protected int tempscore =0;//阶段得分
    /**
     * 当前时刻
     */
    protected int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 800;
    protected int cycleTime = 0;
    protected boolean bosswar = false;
    protected boolean gameOverFlag=false;
    /**
     * 游戏结束标志
     */
    protected Integer Mode;
    protected Boolean Sound;
    protected BufferedImage background=ImageManager.BACKGROUND_IMAGE1;
    MusicThread music;//=new MusicThread("src/videos/bgm.wav",2);
    MusicThread bgm_boss;//=new MusicThread("src/videos/bgm_boss.wav",2);
    protected double eliteprob;
    protected double mobprob;
    protected int bosscore;//boss出现的分数
    protected int bosscount=0;

    public Game(Boolean sound) {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        music=new MusicThread("src/videos/bgm0.wav",2);
        bgm_boss=new MusicThread("src/videos/bgm_boss0.wav",2);
        Sound=sound;
        enemyMaxNumber=5;
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
       new HeroController(this, heroAircraft);
       new KeyBoardController(this, heroAircraft);
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
                enforcEnemy();
                adjustParam();
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
                    RankListGUI rankListGUI = new RankListGUI(Mode);
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
    //***********************
    //      Action 各部分
    //***********************

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    protected void shootAction() {
        // TODO 敌机射击
        for (AbstractEnemy enemy : enemyAircrafts) {
            enemyBullets.addAll(enemy.shoot());}
            // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
        for(BaseBullet bullet : heroBullets)
        {
            if(bullet instanceof ChaseBullet)
        {

            for (AbstractEnemy enemy : enemyAircrafts){
                ((ChaseBullet)bullet).removeObjects(enemy);
                ((ChaseBullet)bullet).registerObjects(enemy);}
        }
        }

    }
    protected void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            if(bullet instanceof ChaseBullet)
            {

                 ((ChaseBullet) bullet).chaseObjects();
            }//调整速度方向
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    protected void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    protected void propsMoveAction() {
    for(Baseprop prop:props){
        prop.forward();
    }
    }
    //我添加的
    protected abstract void enforcEnemy();
    protected abstract void adjustParam();
    protected  abstract void createEnemy();
    protected void adjustspeed(){
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
    protected void crashCheckAction(){
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
                            bgm_boss.pauseThread();
                            music.resumeThread();
                        }
                        // TODO 获得分数，产生道具补给
                        score += enemyAircraft.getScore();
                        if(bosswar==false)
                            tempscore += enemyAircraft.getScore();
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
            if(prop instanceof Bombprop)
            {
                for(BaseBullet bullet: enemyBullets)
                {
                    ((Bombprop) prop).registerObserver((Observer) bullet);
                }
                for(AbstractEnemy enemy:enemyAircrafts)
                {
                ((Bombprop) prop).registerObserver(enemy);
                }

            }
            if(prop.crash(heroAircraft)||heroAircraft.crash(prop))
            {
                if (Sound)
                    new MusicThread("src/videos/get_supply.wav").start();
                prop.vanish();
                prop.apply();
            }
            if(prop instanceof Bombprop)
            {
                for(BaseBullet bullet: enemyBullets)
                {
                    ((Bombprop) prop).removeObserver((Observer) bullet);
                }
                for(AbstractEnemy enemy:enemyAircrafts) {
                    ((Bombprop) prop).removeObserver(enemy);
                    if(enemy.notValid())
                    {score+=enemy.getScore();
                        if(bosswar==false)
                        tempscore+=enemy.getScore();
                    }
                }
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
    protected void postProcessAction() {
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
        if(HeroAircraft.visible==true)
        {g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);}
        else
        {g.drawImage(ImageManager.HERO_INVISIBLE_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);}
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
            Font font = new Font("Arial", Font.PLAIN, 12);
            g.setFont(font);
            String healthText = String.valueOf(enemy.getHp()) + "/" + String.valueOf(enemy.getMaxHp());
            int textX = enemy.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2;
            int textY = enemy.getLocationY() - image.getHeight() / 2 - barHeight - 5; // 血条上方一段距离
            g.drawString(healthText, textX, textY);
        }
        //绘制得分和生命值
        paintScoreAndLife(g);

    }



    protected void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            if(object instanceof BaseBullet)
            {int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            int objectX = object.getLocationX();
            int objectY = object.getLocationY();
            int speedX = object.getSpeedX();
            int speedY = object.getSpeedY();

            // 计算旋转角度
            double angle = Math.atan2(speedY, speedX);

            // 创建变换对象
            AffineTransform transform = new AffineTransform();
            // 移动到对象的中心位置
            transform.translate(objectX, objectY);
            // 旋转图像
            transform.rotate(angle);
            // 将图像移动到正确的位置
            transform.translate(-imageWidth / 2.0, -imageHeight / 2.0);
            ////////
            angle = Math.atan2(speedY, speedX) - Math.PI / 2;

            transform = new AffineTransform();
            transform.translate(objectX, objectY);
            transform.rotate(angle);
            // 绘制旋转后的图像
            g2d.drawImage(image, transform, null);}
            else
                g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    protected void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }




}
