package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Enemy extends Character{
    /*PApplet parent;*/
    PImage enemyImage;

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
                    System.out.println(number);
                }
            }
        }
        return enemies;
    }

    public void handleEnemyMovement(){
        if(collisionDetect()){
            switch (direction) {
                case 0: this.up(); break;
                case 1: this.right(); break;
                case 2: this.down(); break;
                case 3: this.left(); break;
            }
        }else{
            direction = new Random().nextInt(4);
        }
    }

    public static void enemiesMove(){
        for(Enemy enemy : enemies){
            enemy.handleEnemyMovement();
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
            if (enemy.exist) {
                enemy.render();
            } else {
                iterator.remove();
            }
        }
    }
}