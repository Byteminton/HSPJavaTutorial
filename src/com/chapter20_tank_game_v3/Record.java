package com.chapter20_tank_game_v3;

import java.io.*;
import java.util.Vector;

/**
 * Save game information, interact with files
 */
public class Record {
    private static int killedEnemiesNum = 0;
    private static BufferedReader br = null;
    private static BufferedWriter bo = null;
    private static String recordFilePath = "src/myRecord.txt";
    private static Hero hero;
    private static Vector<Enemy> enemies;
    private static TankRecordNode heroNode;
    private static Vector<TankRecordNode> enemyRecordNodes = new Vector<>();

    public static int getKilledEnemiesNum() {
        return killedEnemiesNum;
    }

    public static void setKilledEnemiesNum(int killedEnemiesNum) {
        Record.killedEnemiesNum = killedEnemiesNum;
    }

    public static String getRecordFilePath() {
        return recordFilePath;
    }

    public static void setRecordFilePath(String recordFilePath) {
        Record.recordFilePath = recordFilePath;
    }

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero hero) {
        Record.hero = hero;
    }

    public static Vector<Enemy> getEnemies() {
        return enemies;
    }

    public static void setEnemies(Vector<Enemy> enemies) {
        Record.enemies = enemies;
    }

    public static TankRecordNode getHeroNode() {
        return heroNode;
    }

    public static void setHeroNode(TankRecordNode heroNode) {
        Record.heroNode = heroNode;
    }

    public static Vector<TankRecordNode> getEnemyRecordNodes() {
        return enemyRecordNodes;
    }

    public static void setEnemyRecordNodes(Vector<TankRecordNode> enemyRecordNodes) {
        Record.enemyRecordNodes = enemyRecordNodes;
    }

    public static void addKilledEnemiesNum() {
        Record.killedEnemiesNum++;
    }
    public static boolean checkFinished() {
        try {
            br = new BufferedReader(new FileReader(recordFilePath));
            if ("Finished".equals(br.readLine())) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public static void extractRecord() {
        try {
            br = new BufferedReader(new FileReader(recordFilePath));
            killedEnemiesNum = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] info = line.split(" ");
                // The first line is hero's position and direction info
                if ("hero".equals(info[0])) {
                    heroNode = new TankRecordNode(Integer.parseInt(info[1]),
                            Integer.parseInt(info[2]), Integer.parseInt(info[3]));
                } else {
                    // The following lines are enemies' position and direction info
                    enemyRecordNodes.add(new TankRecordNode(Integer.parseInt(info[1]),
                            Integer.parseInt(info[2]), Integer.parseInt(info[3])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void keepRecord() {
        try {
            bo = new BufferedWriter(new FileWriter(recordFilePath));
            if (!hero.isLive() || enemies.size() == 0) {
                bo.write("Finished" + "\r\n");
            }
            bo.write(killedEnemiesNum + "\r\n");
            if (hero.isLive()) {
                bo.write("hero " + hero.getX() + " "
                        + hero.getY() + " " + hero.getDirection() + "\r\n");
            }
            for (Enemy enemy : enemies) {
                if (enemy.isLive()) {
                    bo.write("enemy " + enemy.getX() + " " +
                            enemy.getY() + " " + enemy.getDirection() + "\r\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
