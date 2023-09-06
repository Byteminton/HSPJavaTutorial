package com.chapter20_tank_game_v3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable, Serializable {
    int mapWidth = 1000;
    int mapHeight = 750;
    int infoWidth = 300;
    int infoHeight = 750;
    Hero hero;
    Vector<Enemy> enemies = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemySize = 3;
    Image smallBomb = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
    Image mediumBomb = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
    Image bigBomb = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));


    public MyPanel(String choice) {
        switch (choice) {
            case "y": {
                Record.extractRecord();
                if (Record.getHeroNode() != null) {
                    hero = new Hero(Record.getHeroNode().getX(), Record.getHeroNode().getY(),
                            Record.getHeroNode().getDirection());//initial own tank
                }
                for (int i = 0; i < Record.getEnemyRecordNodes().size(); i++) {
                    Enemy enemy = new Enemy(Record.getEnemyRecordNodes().get(i).getX(),
                            Record.getEnemyRecordNodes().get(i).getY(),
                            Record.getEnemyRecordNodes().get(i).getDirection());
                    enemies.add(enemy);
                    new Thread(enemy).start();
                }
                Record.setHero(hero);
                Record.setEnemies(enemies);
                break;
            }
            case "n": {
                hero = new Hero(100, 200);//initial own tank
                for (int i = 0; i < enemySize; i++) {
                    Enemy enemy = new Enemy(200 + 100 * i, 10);
                    enemies.add(enemy);
                    new Thread(enemy).start();
                }
                Record.setHero(hero);
                Record.setEnemies(enemies);
            }
        }
        new AePlayWave("src/111.wav").start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, mapWidth, mapHeight);//default black
        showInfo(g);
        if (hero != null && hero.isLive()) {
            drawTank(hero, g);
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString("You Lose", 100, 100);
        }
        drawBullet(hero, g);

        if (enemies.size() == 0) {
            g.setColor(Color.red);
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString("You Win", 100, 100);
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isLive()) {
                drawTank(enemy, g);
            }
            drawBullet(enemy, g);
        }
        /* If the first bomb is too quick, can add sleep here
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
         */
        drawBombs(g);
    }

    public void showInfo(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(mapWidth, 0, infoWidth, infoHeight);//default black
        g.setColor(Color.RED);
        g.setFont(new Font("宋体", Font.BOLD, 25));
        g.drawString("Total Score:", 1020, 30);
        drawTank(new Enemy(1020,60), g);
        g.setColor(Color.RED);
        g.drawString(Record.getKilledEnemiesNum() + "", 1080, 100);
    }

    public void checkEnemyLive() {
        int xLimit;
        int yLimit;

        // Check whether hero's bullet hit any enemy or not
        Vector<Bullet> heroBullets = hero.getBullets();

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.getDirection() == 0 || enemy.getDirection() == 2) {
                xLimit = 40;
                yLimit = 60;
            } else {
                xLimit = 60;
                yLimit = 40;
            }
            for (Bullet heroBullet : heroBullets) {
                //Bullet heroBullet = heroBullets.get(j);
                if (heroBullet.getX() > enemy.getX() - heroBullet.getDiameter() / 2 &&
                        heroBullet.getX() < enemy.getX() + xLimit &&
                        heroBullet.getY() > enemy.getY() - heroBullet.getDiameter() / 2 &&
                        heroBullet.getY() < enemy.getY() + yLimit) {
                    enemy.setLive(false);
                    heroBullet.setLive(false);
                    enemies.remove(enemy);
                    Record.addKilledEnemiesNum();
                    Bomb bomb = new Bomb(enemy.getX(), enemy.getY());
                    bombs.add(bomb);
                }
            }
        }
    }

    public void checkHeroLive() {
        // Check whether enemies' bullets hit the hero or not
        if (!hero.isLive()) {
            return;
        }
        int xLimit;
        int yLimit;
        for (Enemy enemy : enemies) {
            if (hero.getDirection() == 0 || hero.getDirection() == 2) {
                xLimit = 40;
                yLimit = 60;
            } else {
                xLimit = 60;
                yLimit = 40;
            }
            Vector<Bullet> enemyBullets = enemy.getBullets();
            for (Bullet enemyBullet : enemyBullets) {
                if (enemyBullet.getX() > hero.getX() - enemyBullet.getDiameter() / 2 &&
                        enemyBullet.getX() < hero.getX() + xLimit &&
                        enemyBullet.getY() > hero.getY() - enemyBullet.getDiameter() / 2 &&
                        enemyBullet.getY() < hero.getY() + yLimit) {
                    hero.setLive(false);
                    enemyBullet.setLive(false);
                    Bomb bomb = new Bomb(hero.getX(), hero.getY());
                    bombs.add(bomb);
                }
            }
        }
    }

    class Pair implements Serializable {
        boolean isOverlay;
        boolean faceToFace;

        public Pair(boolean isOverlay, boolean faceToFace) {
            this.isOverlay = isOverlay;
            this.faceToFace = faceToFace;
        }
    }

    public Pair Collision(Tank t1, Tank t2) {
        class Boundary {
            int xl, xu, yl, yu;

            public Boundary(int xl, int xu, int yl, int yu) {
                this.xl = xl;
                this.xu = xu;
                this.yl = yl;
                this.yu = yu;
            }
        }
        int x1 = t1.getX();
        int y1 = t1.getY();
        int x2 = t2.getX();
        int y2 = t2.getY();
        Boundary boundary;

        if (t2.isVertical()) {
            boundary = new Boundary(x2, x2 + 40, y2, y2 + 60);
        } else {
            boundary = new Boundary(x2, x2 + 60, y2, y2 + 40);
        }

        switch (t1.getDirection()) {
            case 0: {
                return new Pair((x1 >= boundary.xl && x1 <= boundary.xu
                        && y1 >= boundary.yl && y1 <= boundary.yu) ||
                        (x1 + 40 >= boundary.xl && x1 + 40 <= boundary.xu
                                && y1 >= boundary.yl && y1 <= boundary.yu),
                        t2.isVertical());
            }
            case 1:
                return new Pair((x1 + 60 >= boundary.xl && x1 + 60 <= boundary.xu
                        && y1 >= boundary.yl && y1 <= boundary.yu) ||
                        (x1 + 60 >= boundary.xl && x1 + 60 <= boundary.xu
                                && y1 + 40 >= boundary.yl && y1 + 40 <= boundary.yu),
                        !t2.isVertical());
            case 2:
                return new Pair((x1 >= boundary.xl && x1 <= boundary.xu
                        && y1 + 60 >= boundary.yl && y1 + 60 <= boundary.yu) ||
                        (x1 + 40 >= boundary.xl && x1 + 40 <= boundary.xu
                                && y1 + 60 >= boundary.yl && y1 + 60 <= boundary.yu),
                        t2.isVertical());
            case 3:
                return new Pair((x1 >= boundary.xl && x1 <= boundary.xu
                        && y1 >= boundary.yl && y1 <= boundary.yu) ||
                        (x1 >= boundary.xl && x1 <= boundary.xu
                                && y1 + 40 >= boundary.yl && y1 + 40 <= boundary.yu),
                        !t2.isVertical());
            default:
                System.out.println("An Error occurs while judging crush");
                return null;
        }
    }

    // Check whether enemies will collide with each other and the hero
    public void checkCollision(Vector<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (Collision(enemy, hero).isOverlay) {
                enemy.turnAround();
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                Enemy e1 = enemies.get(i);
                Enemy e2 = enemies.get(j);
                if (i != j) {
                    Pair pr = Collision(e1, e2);
                    if (pr.isOverlay) {
                        if (pr.faceToFace) {
                            e2.turnAround();
                        }
                        e1.turnAround();
                    }
                }
            }
        }
    }

    // Check whether the hero will collide with any enemies
    public boolean checkCollision(Hero hero, Vector<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (Collision(hero, enemy).isOverlay) {
                return false;
            }
        }
        return true;
    }

    public void drawBombs(Graphics g) {
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            // According to life, draw different image
            if (bomb.getLife() > 6) {
                g.drawImage(bigBomb, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(mediumBomb, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(smallBomb, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            bomb.lifeDown();
            if (!bomb.isLive()) {
                bombs.remove(bomb);
            }
        }
    }

    public void drawBullet(Tank tank, Graphics g) {
        Vector<Bullet> bullets = tank.getBullets();
        // use iterator version will generate currentModificationException
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet.isLive()) {
                g.setColor(tank.getColor());
                g.fillOval(bullet.getX(), bullet.getY(), bullet.getDiameter(), bullet.getDiameter());
            } else {
                bullets.remove(bullet);
            }
        }
    }

    /**
     * @param tank // certain tank
     * @param g    // Paint Brush
     */
    public void drawTank(Tank tank, Graphics g) {
        int x = tank.getX();
        int y = tank.getY();
        g.setColor(tank.getColor());
        // According to tank's direction, paint corresponding tanks
        switch (tank.getDirection()) {
            case 0: // up
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // body
                g.fillOval(x + 10, y + 20, 20, 20); // battery
                g.drawLine(x + 20, y, x + 20, y + 30); // cannon barrel
                break;
            case 1: // right
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // body
                g.fillOval(x + 20, y + 10, 20, 20); // battery
                g.drawLine(x + 60, y + 20, x + 30, y + 20); // cannon barrel
                break;
            case 2: // down
                g.fill3DRect(x, y, 10, 60, false); // left wheel
                g.fill3DRect(x + 30, y, 10, 60, false); // right wheel
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // body
                g.fillOval(x + 10, y + 20, 20, 20); // battery
                g.drawLine(x + 20, y + 60, x + 20, y + 30); // cannon barrel
                break;
            case 3: // left
                g.fill3DRect(x, y, 60, 10, false); // up wheel
                g.fill3DRect(x, y + 30, 60, 10, false); // down wheel
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // body
                g.fillOval(x + 20, y + 10, 20, 20); // battery
                g.drawLine(x, y + 20, x + 30, y + 20); // cannon barrel
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            if (!hero.touchUpperBoundary() && checkCollision(hero, enemies)) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            if (!hero.touchRightBoundary() && checkCollision(hero, enemies)) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            if (!hero.touchLowerBoundary() && checkCollision(hero, enemies)) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            if (!hero.touchLeftBoundary() && checkCollision(hero, enemies)) {
                hero.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            if (hero.isLive()) {
                hero.shot(Hero.BULLET_SPEED);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                // must lower than bullet sleep time, otherwise it looks very stuttering
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkEnemyLive();
            checkHeroLive();
            checkCollision(enemies);
            this.repaint();
        }
    }
}
