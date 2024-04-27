package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Enemy extends Character{
    /*PApplet parent;*/
    PImage enemyImage;
    double lastChange = 0;

    /*int currentDirection;// 0 = up,1 = right,2 = down, 3 = left*/

    Enemy(int x, int y, PApplet parent, PImage enemy) {
        /*super(x, y, parent, enemy);*/
        this.parent = parent;
        this.px=x;
        this.py=y;
        this.enemyImage = enemy;
        this.health = 1;
        this.speed = 1;
        this.exist = true;
        this.direction = new Random().nextInt(4);
    }

    public static ArrayList<Enemy> generateEnemies(PApplet parent) {
        int number =0;
        Random random = new Random();
        while(number < enemyNumber){
            int randomX = random.nextInt(cols);
            int randomY = random.nextInt(rows);
            if (!(randomX <4 && randomY <4)) {
                int x = 15 + randomX * tile;
                int y = 75 + randomY * tile;
                if(!Obstacle.areThereRocks(x,y) && !Wall.isWallAt(x, y)){
                    enemies.add(new Enemy(x, y, parent, ResourceManager.redEnemy));
                    number ++;
                }
            }
        }
        return enemies;
    }

    public void handleEnemyMovement(){
        if((this.x()-15) % 30 == 0 && (this.y()-75) % 30 == 0){
            if(millis() - lastChange > 3000){
                direction = new Random().nextInt(4);
                lastChange = millis();
            }
        }
        if(collisionDetect()){
            switch (direction) {
                case 0: this.up(); break;
                case 1: this.right(); break;
                case 2: this.down(); break;
                case 3: this.left(); break;
            }
        }else{
            direction = new Random().nextInt(4);
            lastChange = millis();
        }
    }

    public static void enemiesMove(){
        for(Enemy enemy : enemies){
            enemy.handleEnemyMovement();
        }
    }

    public static void updateEnemiesPositions(PApplet parent) {
        for (Enemy enemy : enemies) {
            boolean validPosition = false;
            while (!validPosition) {
                int i = (int) (parent.random(cols));
                int j = (int) (parent.random(rows));

                // 检查新位置是否符合条件：不在特殊区域内，不是墙也不是商店和不是硬币所在位置
                if (!Wall.isWallAt(i, j) && !Shop.isShopAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4) && !Obstacle.obstacleGrid[i][j]) {
                    enemy.px = 15 + i * tile;
                    enemy.py = 75 + j * tile;
                    enemy.exist = true;
                    validPosition = true;
                }
            }
        }
    }

    void render(){
        parent.image(enemyImage,px,py,30,30);
    }

    public static void enemiesRender() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.ifDamageCharacter();
            enemy.ifUltimateDamageCharacter();
            if (enemy.exist) {
                enemy.render();
            } /*else {
                iterator.remove();
            }*/
        }
    }
}