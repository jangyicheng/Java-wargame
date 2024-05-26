package edu.hitsz.bullet;

import edu.hitsz.aircraft.AbstractEnemy;
import java.util.LinkedList;
import java.util.List;

public class ChaseBullet extends HeroBullet{
    private static final double ACCELERATION_MAGNITUDE = 2; // 恒定的加速度大小
    private static final double SPEED_MAGNITUDE = 10; // 恒定的速度大小
    private double accelerationX;
    private double accelerationY;

    public ChaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
        this.accelerationX = 0;
        this.accelerationY = 0;
    }

    private List<AbstractEnemy> objects = new LinkedList<>();

    public void registerObjects(AbstractEnemy abstractEnemy) {
        this.objects.add(abstractEnemy);
    }

    public void removeObjects(AbstractEnemy abstractEnemy) {
        this.objects.remove(abstractEnemy);
    }

    public void chaseObjects() {
        AbstractEnemy closestEnemy = null;
        double minDistance = Double.MAX_VALUE;

        for (AbstractEnemy enemy : objects) {
            if (!enemy.notValid()) {
                double distance = calculateDistance(enemy);

                if (distance < minDistance) {
                    closestEnemy = enemy;
                    minDistance = distance;
                }
            }
        }

        if (closestEnemy != null) {
            int targetX = closestEnemy.getLocationX();
            int targetY = closestEnemy.getLocationY();

            // 计算追踪方向
            int dx = targetX - locationX;
            int dy = targetY - locationY;

            double distance = minDistance;

            // 计算新的加速度方向
            accelerationX = dx / distance * ACCELERATION_MAGNITUDE;
            accelerationY = dy / distance * ACCELERATION_MAGNITUDE;

            // 更新速度
            speedX += accelerationX;
            speedY += accelerationY;

            // 保持速度恒定
            double currentSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
            if (currentSpeed != SPEED_MAGNITUDE) {
                speedX = (int) (speedX / currentSpeed * SPEED_MAGNITUDE);
                speedY = (int) (speedY / currentSpeed * SPEED_MAGNITUDE);
            }
        }
    }

    private double calculateDistance(AbstractEnemy enemy) {
        int targetX = enemy.getLocationX();
        int targetY = enemy.getLocationY();

        int dx = targetX - locationX;
        int dy = targetY - locationY;

        return Math.sqrt(dx * dx + dy * dy);
    }
}