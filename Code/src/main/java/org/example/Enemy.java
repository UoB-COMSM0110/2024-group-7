package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Character{
    /*PApplet parent;*/
    PImage enemyImage;

    Enemy(int x, int y, PApplet parent, PImage enemy) {
        /*super(x, y, parent, enemy);*/
        this.parent = parent;
        this.px=x;
        this.py=y;
        this.enemyImage = enemy;
        this.health = 1;
        this.speed = 3;
    }

    public static ArrayList<Enemy> generateEnemies(PApplet parent) {
        ArrayList<Enemy> enemies = new ArrayList<>();
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
                    System.out.println(number);
                }
            }
        }
        return enemies;
    }

    void render(){
        parent.image(enemyImage,px,py,30,30);
    }

    public static void enemiesRender(){
        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }
}