package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import javax.print.CancelablePrintJob;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character {
    PImage playerImage;
    public int playerNumber = 0;
    private int explosionDistance;
    private int maxBombs;
    boolean bomb=false;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    boolean move=false, up=false, down=false, left=false, right=false;

    boolean otherPlayerWon = false;

    public double setBombTime = 0;

    Player(int playerNumber, int x, int y, PApplet parent, PImage playerImage){
        this.playerNumber = playerNumber;
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
        this.coin = 0;
    }

    public static ArrayList<Player> setPlayer1(PApplet parent) {
        int x = 15 + tile;
        int y = 75 + tile;
        players.add(new Player(1, x, y, parent, ResourceManager.pinkBomber));
        return players;
    }

    public static ArrayList<Player> setPlayer2(PApplet parent) {
        int x = 885;
        int y = 465;
        players.add(new Player(2, x, y, parent, ResourceManager.pinkBomber));
        return players;
    }

    public void PVPEnhancement(){
        speed = 3;
        maxBombs = 3;
        explosionDistance = 3;
    }

    public void playerMove(){
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

    private static void playerBombActivation(){
        for (Bomb bomb : Player.players.get(0).bombs) {
            if (bomb.showed && !bomb.bombActive &&
                    dist(Player.players.get(0).px + (float) tile / 2,
                            Player.players.get(0).py + (float) tile / 2,
                            bomb.x() + (float) tile / 2,
                            bomb.y() + (float) tile / 2) >= tile) {
                bomb.bombActive = true;
                /*System.out.println("the bomb is active");*/
            }
        }
        for (Bomb bomb : Player.players.get(1).bombs) {
            if (bomb.showed && !bomb.bombActive &&
                    dist(Player.players.get(1).px + (float) tile / 2,
                            Player.players.get(1).py + (float) tile / 2,
                            bomb.x() + (float) tile / 2,
                            bomb.y() + (float) tile / 2) >= tile) {
                bomb.bombActive = true;
                /*System.out.println("the bomb is active");*/
            }
        }
    }

    public static void player1Movement(){
        playerBombActivation();
        if(PVP) {
            if (Character.players.get(0).collisionDetectPVP()) {
                Character.players.get(0).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
            }
        }else{
            if (Character.players.get(0).collisionDetect()) {
                Character.players.get(0).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
            }
        }
    }

    public static void player2Movement(){
        playerBombActivation();
        if(Character.players.get(1).collisionDetectPVP()) {
            Character.players.get(1).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
        }
    }

    public static void absorb1ToIntersection(){
        if(!(players.get(0).up || players.get(0).right || players.get(0).left || players.get(0).down)) {
            players.get(0).px = Math.round(Character.players.get(0).px / 15.0f) * 15;
            players.get(0).py = Math.round(Character.players.get(0).py / 15.0f) * 15;
        }
    }

    public static void absorb2ToIntersection(){
        if(!(players.get(1).up || players.get(1).right || players.get(1).left || players.get(1).down)) {
            players.get(1).px = Math.round(Character.players.get(1).px / 15.0f) * 15;
            players.get(1).py = Math.round(Character.players.get(1).py / 15.0f) * 15;
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
    public void increaseCoin() {this.coin += 1;}

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
            players.get(0).otherPlayerWon = true;
            shrinkNumber = 1f;
        }
    }

    public static void player2Render(){
        players.get(1).ifDamageCharacter();
        //players.get(1).ifTouchEnemy();
        if(Character.players.get(1).exist){
            Character.players.get(1).render();
        }else {
            players.get(1).otherPlayerWon = true;
        }
    }

    public int getHealth() {
        return this.health;
    }
}
