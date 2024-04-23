package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.BulletpropFactory;
import java.util.HashSet;
import java.util.Set;;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
class HeroAircraftTest {

    private static HeroAircraft heroAircraft= null;
    private Set<HeroAircraft> heroSet = Collections.synchronizedSet(new HashSet<>());

    class TestThread extends Thread {
        @Override
        public void run() {
            synchronized (heroSet){
         heroSet.add( HeroAircraft.getInstance());}
        }

    }

// 创建多个线程测试

    @Test
    @BeforeEach
    void getInstance() throws InterruptedException {
        heroAircraft=HeroAircraft.getInstance();
        assertNotNull(heroAircraft);
       ExecutorService executor=Executors.newFixedThreadPool(10);
       TestThread[] thread = new TestThread[20];

        {
        for (int i = 0; i < 20; i++) {//同时创建20个线程，即调用20次getinstance
            thread[i] = new TestThread();
            executor.execute(thread[i]);
        }
        }
        assertEquals(1,heroSet.size());
        executor.shutdown();
        assertNotNull(heroAircraft);
    }

    private int BeforeBlood;
    private int AfterBlood;
    //@Test
    @ParameterizedTest
    @ValueSource(ints = {2,5,100,-40,-1000})//对掉血回血的情况均进行检验
    void decreaseHp(int blood) {
        BeforeBlood=heroAircraft.getHp();
        int maxblood=heroAircraft.maxHp;
        heroAircraft.decreaseHp(blood);
        AfterBlood=heroAircraft.getHp();
        assertEquals(Math.min(BeforeBlood-blood,maxblood),AfterBlood);
    }

    private static Stream <List<AbstractFlyingObject > >objectProvider() {
        EnemyFactory factory1=new MobEnemyFactory();
        AbstractFlyingObject flyingObject1=factory1.createEnemy();
        BulletpropFactory factory2=new BulletpropFactory();
        AbstractFlyingObject flyingObject2=factory2.createprop();
        List<AbstractFlyingObject > enemyList1 = new ArrayList<>();
        enemyList1.add(flyingObject1);
        enemyList1.add(flyingObject2);

        List<AbstractFlyingObject > enemyList2 = new ArrayList<>();
        enemyList2.add(flyingObject1);
        enemyList2.add(heroAircraft);

        List<AbstractFlyingObject > enemyList3 = new ArrayList<>();
        enemyList3.add(flyingObject1);
        enemyList3.add(heroAircraft);
        return Stream.of(
                enemyList1, enemyList2, enemyList3
        );
    }

    @ParameterizedTest
    @MethodSource("objectProvider")
    void crash(List<AbstractFlyingObject> abstractFlyingObjects ){
        AbstractFlyingObject flyingObject1=abstractFlyingObjects.get(0);
        AbstractFlyingObject flyingObject2=abstractFlyingObjects.get(1);
        // 创建被测飞行物对象
        //对方坐标、宽度、高度
        int factor =  2 ; //我方
        int fFactor = flyingObject2 instanceof AbstractAircraft ? 2 : 1;//对方

        int x = flyingObject2.getLocationX();
        int y = flyingObject2.getLocationY();
        int fWidth = flyingObject2.getWidth();
        int fHeight = flyingObject2.getHeight();
        double distancex=(double)(fWidth+flyingObject1.getWidth())/2.0;
        double distancey=(double)(fHeight/fFactor+flyingObject1.getHeight()/factor)/2.0;
        flyingObject1.setLocation(x+distancex-1,y+distancey-1);//测试重叠时是否撞击
        System.out.printf("%f, %f%n",distancex,distancey);
        System.out.printf("%d, %d%n",x,y);
        System.out.printf("%d, %d%n", flyingObject1.getLocationX(),flyingObject1.getLocationY());
        assertTrue(flyingObject1.crash(flyingObject2));
        assertTrue(flyingObject2.crash(flyingObject1));
        flyingObject1.setLocation(x+distancex-1,y+distancey);//测试擦边时是否撞击
        System.out.printf("%f, %f%n",distancex,distancey);
        System.out.printf("%d, %d%n",x,y);
        System.out.printf("%d, %d%n", flyingObject1.getLocationX(), flyingObject1.getLocationY());
        assertFalse(flyingObject1.crash(flyingObject2));
        assertFalse(flyingObject2.crash(flyingObject1));
    }
}