package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import javax.print.CancelablePrintJob;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character {
    PImage playerImage;
    private int explosionDistance;
    private int maxBombs;
    /*public int direction; // 0 = up,1 = right,2 = down, 3 = left*/

    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
        this.health = 3;
        this.explosionDistance = 1;
        this.maxBombs = 1;
        this.speed = 3;
        this.exist = true;
        this.direction = -1;
    }

    public static ArrayList<Player> setPlayer1(PApplet parent) {
        ArrayList<Player> players = new ArrayList<>();
        int x = 15 + tile;
        int y = 15 + 3*tile;
        players.add(new Player(x, y, parent, ResourceManager.pinkBomber));
        return players;
    }

    public void playerMove(){
        /*if (direction == 0) {
            this.up();
        }
        if (direction == 2) {
            this.down();
        }
        if (direction == 3) {
            this.left();
        }
        if (direction == 1) {
            this.right();
        }*/
        if (up) {
            this.up();
        }
        if (down) {
            this.down();
        }
        if (left) {
            this.left();
        }
        if (right) {
            this.right();
        }
    }

    private void playerBombActivation(){
        for (Bomb bomb : Objects.bombs) {
            if (bomb.showed && !bomb.bombActive &&
                    dist(px + (float) tile / 2, py + (float) tile / 2, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) >= tile) {
                bomb.bombActive = true;
                /*System.out.println("the bomb is active");*/
            }
        }
    }

    public static void player1Movement(){
        Character.players.get(0).playerBombActivation();
        if(Character.players.get(0).collisionDetect()) {
            Character.players.get(0).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
        }
    }

    public static void absorbToIntersection(){
        if(!(up || right || left || down)) {
            Character.players.get(0).px = Math.round(Character.players.get(0).px / 15.0f) * 15;
            Character.players.get(0).py = Math.round(Character.players.get(0).py / 15.0f) * 15;
        }
    }

    void render(){
        parent.image(playerImage,px,py,30,30);
    }

    public void increasePower() {
        this.explosionDistance +=1;
    }

    public int getExplosionDistance() {
        return this.explosionDistance;
    }

    public void increaseMaxBomb() {
        this.maxBombs +=1;
    }

    public int getMaxBombs() {
        return this.maxBombs;
    }

    public void increaseSpeed() {this.speed +=1; }
    public void increaseLife() {this.health += 1;}

    public void ifTouchEnemy(){
        //System.out.println("?");
        for(Enemy enemy : enemies) {
            if (dist(px, py, enemy.x(), enemy.y()) < (float) tile / 2
                && parent.millis() - damageTime > 1000) {
                health -= 1;
                damageTime = parent.millis();
                if (health == 0) {
                    exist = false;
                }
            }
        }
    }

    public static void player1Render(){
        players.get(0).ifDamageCharacter();
        players.get(0).ifTouchEnemy();
        if(Character.players.get(0).exist){
            Character.players.get(0).render();
        }else {
            GameLoop.gameLost = true;
        }
    }
}
