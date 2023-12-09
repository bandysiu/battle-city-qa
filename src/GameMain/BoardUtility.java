/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2016
 *
 * Name: Tongyu Yang, Peter Unrein, Hung Giang, Adrian Berg
 * Date: Apr 29, 2016
 * Time: 7:23:00 PM
 *
 * Project: csci205FinalProject
 * Package: GameMain
 * File: BoardUtility
 * Description: A utility class for the board
 *
 * ****************************************
 */
package GameMain;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import SpriteClasses.Animation;
import SpriteClasses.Block;
import SpriteClasses.Bullet;
import SpriteClasses.ExplodingTank;
import SpriteClasses.PowerUps.BombPowerUp;
import SpriteClasses.PowerUps.ClockPowerUp;
import SpriteClasses.PowerUps.PowerUp;
import SpriteClasses.PowerUps.ShieldPowerUp;
import SpriteClasses.PowerUps.StarPowerUp;
import SpriteClasses.PowerUps.TankPowerUp;
import SpriteClasses.Tank;
import SpriteClasses.TankAI;
import SpriteClasses.TankShield;
import SpriteClasses.TankSpawn;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
/**
 * A utility class for the board
 *
 * @author Adrian Berg
 */
public class BoardUtility {

    private static ArrayList<SpriteClasses.TankAI> enemy = new ArrayList<>();
    private static ArrayList<Block> blocks = new ArrayList<>();
    private static ArrayList<Animation> animations = new ArrayList<>();
    private static ArrayList<PowerUp> powerUps = new ArrayList<>();
    private static Tank tank;

    /**
     * Constructor for the BoardUtility class
     *
     * @param enemy1 an array list that stores enemy tanks
     * @param blocks1 an array list that stores blocks on the board
     * @param animations1 an array list that stores different animations
     * @param powerUps1 an array list that stores different power-ups
     * @param tank1 the Tank class that represents the player
     */
    public static void loadBoardUtility(ArrayList<SpriteClasses.TankAI> enemy1,
                                        ArrayList<Block> blocks1,
                                        ArrayList<Animation> animations1,
                                        ArrayList<PowerUp> powerUps1, Tank tank1) {
        enemy = enemy1;
        blocks = blocks1;
        animations = animations1;
        powerUps = powerUps1;
        tank = tank1;
    }

    /**
     * Spawn random PowerUp when the given tank AI carries powerUp and is
     * destroyed.
     */
    public static void spawnPowerUp() {
        Random random = new Random();
        int randomPow = random.nextInt(5);
        if (CollisionUtility.powerUpX != 0 || CollisionUtility.powerUpY != 0) {
            switch (randomPow) {
                case 0:
                    powerUps.add(new BombPowerUp(CollisionUtility.powerUpX,
                                                 CollisionUtility.powerUpY));
                    break;
                case 1:
                    powerUps.add(new ClockPowerUp(CollisionUtility.powerUpX,
                                                  CollisionUtility.powerUpY));
                    break;
                case 2:
                    powerUps.add(new ShieldPowerUp(CollisionUtility.powerUpX,
                                                   CollisionUtility.powerUpY));
                    break;
                case 3:
                    powerUps.add(new StarPowerUp(CollisionUtility.powerUpX,
                                                 CollisionUtility.powerUpY));
                    break;
                case 4:
                    powerUps.add(new TankPowerUp(CollisionUtility.powerUpX,
                                                 CollisionUtility.powerUpY));
                    break;
                default:
                    break;
            }
            CollisionUtility.powerUpX = 0;
            CollisionUtility.powerUpY = 0;
        }
    }

    /**
     * Update bullets and tank AI on the board.
     */
    public static void updateBulletsTankAI() {
        for (SpriteClasses.TankAI tankAI : enemy) {
            ArrayList<Bullet> bullets = tankAI.getBullets();
            for (int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                if (b.isVisible()) {
                    b.move();
                } else if (b.isVisible() == false) {
                    bullets.remove(i);
                }
            }
        }
    }

    /**
     * Update bullets and tank on the Board.
     */
    public static void updateBulletsTank() {
        ArrayList<Bullet> bullets = tank.getBullets();

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b.isVisible()) {
                b.move();
            } else if (b.isVisible() == false) {
                bullets.remove(i);
            }
        }
    }

    /**
     * Update blocks on the Board.
     */
    public static void updateBlocks() {
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            BlockType type = BlockType.getTypeFromInt(b.getType());
            if (type.equals(BlockType.RIVER)) {
                b.updateAnimation();
            } else if (type.equals(BlockType.BASE)) {
                b.updateAnimation();
            }
            if (b.isVisible() == false) {
                blocks.remove(i);
            }
        }
    }

    /**
     * Update animations on the Board.
     */
    public static void updateAnimations() {
        for (int i = 0; i < animations.size(); i++) {
            if (animations.get(i).vis == false) {
                animations.remove(i);
            } else {
                animations.get(i).updateAnimation();
            }
        }
    }

    /**
     * Update tank on the Board.
     */
    public static void updateTank() {
        if (tank.isVisible()) {
            tank.move();
        }
    }

    /**
     * Check for collisions on the board.
     */
    public static void checkCollisions() {
        ArrayList<Bullet> bullets = new ArrayList<>();
        bullets.addAll(tank.getBullets());
        for (SpriteClasses.TankAI tankAI : enemy) {
            bullets.addAll(tankAI.getBullets());
        }
        CollisionUtility.checkCollisionBulletsBlocks(bullets, blocks);
        CollisionUtility.checkCollisionBulletsTank(bullets, tank);
        CollisionUtility.checkCollisionBulletsTankAI(bullets, enemy);
        CollisionUtility.checkCollisionTankTankAI(enemy, tank);

    }

}
