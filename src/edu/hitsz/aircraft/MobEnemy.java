package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.Baseprop;
import edu.hitsz.strategy.NullStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy{

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int mode,int time) {
        super(locationX, locationY, speedX, speedY, hp,mode,time);
        this.score=10;
        strategy=new NullStrategy();
        setMode(mode);
        enforce(time);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }

    public int getScore() {
        return score;
    }
    public void adjustspeed(){;}
    public void createprop(List<Baseprop> props)
    {}
    public void update()
    {
        this.vanish();
    }
    public void setMode(int mode)
    {
        if(mode==1)
        {

        }
        else if(mode==2)
        {
            this.hp+=50;
        }
        else if(mode==3)
        {
            this.hp+=100;
        }
        setMaxHp(this.hp);
    }
    private void enforce(int time)
    {
        //生命值提升
        this.hp=Math.min(time/1000+this.hp,200);
        setMaxHp(this.hp);
        //速度提升
        this.speedY=Math.min(time/1000+this.speedY,8);

    }
}
