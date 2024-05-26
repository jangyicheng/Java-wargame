package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.*;
import edu.hitsz.strategy.ScatterStrategy;

import java.util.List;
import java.util.Random;

public class EliteplusEnemy extends AbstractEnemy{

    private double startx;
    private static BloodpropFactory bloodfactory = new BloodpropFactory();
    private static BombpropFactory bombfactory = new BombpropFactory();
    private static BulletpropFactory bulletfactory = new BulletpropFactory();
    private static BulletpluspropFactory bulletplusfactory = new BulletpluspropFactory();
    private static ChasepropFactory chasefactory = new ChasepropFactory();
    private static InvisiblepropFactory invisiblefactory = new InvisiblepropFactory();
    public EliteplusEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int mode,int time) {
        super(locationX, locationY, speedX, speedY, hp,mode,time);
        startx=locationX+40*(locationX< Main.WINDOW_HEIGHT*0.5?1:-1);
        shootNum= 3;
         direction=1;
         score=40;
         power=10;
        strategy=new ScatterStrategy();
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
        return this.strategy.shoot(this);
    }
    public int getScore() {
        return score;
    }
    public void adjustspeed(){
        this.speedX+=(int)(0.03*(-locationX+startx));
    }
    public void createprop(List<Baseprop> props) {
            Random rand = new Random();
            double randouble = rand.nextDouble();

            if (randouble < 0.2) {
                bloodfactory.init(this);
                props.add(bloodfactory.createprop());
            } else if (randouble < 0.4) {
                bombfactory.init(this);
                props.add(bombfactory.createprop());
            } else if (randouble < 0.6) {
                bulletfactory.init(this);
                props.add(bulletfactory.createprop());
            } else if (randouble < 0.8) {
                bulletplusfactory.init(this);
                props.add(bulletplusfactory.createprop());
            }else if(randouble<0.9)
            {   chasefactory.init(this);
                props.add(chasefactory .createprop());}
            else     {   invisiblefactory.init(this);
                props.add(invisiblefactory .createprop());}


    }
    public void update()
    {
        this.decreaseHp((int)(getMaxHp()*0.5));
    }
    private void enforce(int time)
    {
        //生命值提升
        this.hp=Math.min(time/4000+this.hp,400);
        setMaxHp(this.hp);
        //速度提升
        this.speedY=Math.min(time/4000+this.speedY,8);

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

}
