package edu.hitsz.application;


import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.aircraft.EliteplusEnemy;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.bullet.ChaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE1;
    public static BufferedImage BACKGROUND_IMAGE2;
    public static BufferedImage BACKGROUND_IMAGE3;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_INVISIBLE_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage CHASE_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITE_PLUS_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage BULLET_PROP_IMAGE;
    public static BufferedImage BOMB_PROP_IMAGE;
    public static BufferedImage BLOOD_PROP_IMAGE;
    public static BufferedImage BULLET_PLUS_PROP_IMAGE;
    public static BufferedImage CHASE_PROP_IMAGE;
    public static BufferedImage INVISIBLE_PROP_IMAGE;
    static {
        try {

            BACKGROUND_IMAGE1 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE2 = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE3 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            HERO_INVISIBLE_IMAGE = ImageIO.read(new FileInputStream("src/images/hero_invisible.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITE_PLUS_IMAGE= ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
            BULLET_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            BULLET_PLUS_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletplus.png"));
            CHASE_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_chase.png"));
            BOMB_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            BLOOD_PROP_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            INVISIBLE_PROP_IMAGE= ImageIO.read(new FileInputStream("src/images/prop_invisible.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            CHASE_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_chase.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));


            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ChaseBullet.class.getName(), CHASE_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteplusEnemy.class.getName(), ELITE_PLUS_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Bloodprop.class.getName(),  BLOOD_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Bombprop.class.getName(), BOMB_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Bulletprop.class.getName(), BULLET_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Bulletplusprop.class.getName(), BULLET_PLUS_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Chaseprop.class.getName(), CHASE_PROP_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Invisibleprop.class.getName(),  INVISIBLE_PROP_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
