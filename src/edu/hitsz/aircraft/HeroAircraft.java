package edu.hitsz.aircraft;


import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.*;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft{

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */



    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */

    private volatile static HeroAircraft instance;

    public  static synchronized HeroAircraft getInstance()
    {
        if(instance==null)
        {synchronized (HeroAircraft.class)
        {
            if(instance==null)
                instance=new HeroAircraft(Main.WINDOW_WIDTH / 2,
                        Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                        0, 0, 10000);
            instance.setStrategy("Straight");

        }
        }
        return instance;

    }
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootNum = 5;
        /**
         * 子弹伤害
         */
        power = 50;
        /**
         * 子弹射击方向 (向上发射：1，向下发射：-1)
         */
        direction = -2;
        strategy=new StraightStrategy();
    }


    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

        @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return this.strategy.shoot(this);
    }


}
