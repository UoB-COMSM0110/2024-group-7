package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Objects;

public class Player extends Characters {
    PImage playerImage;
    int timer = 120;
    public boolean useAbility= false;
    public boolean isHavingTheKey= true;
    public int explosionDistance;
    public int maxBombs;
    boolean bomb=false;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    boolean move=false, up=false, down=false, left=false, right=false;

    boolean otherPlayerWon = false;

    public double setBombTime = 0;

    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
        this.health = 3;
        this.explosionDistance = 1;
        this.maxBombs = 1;
        this.speed = 1;
        this.exist = true;
        this.direction = -1;
        this.coin = 0;
    }

    void up(){
        py-=5;
    }
    void down() {
        py+=5;
    }
    void left() {
        px-=5;
    }
    void right() {
        px+=5;
    }

    int x(){return px;}
    int y(){return py;}

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
            if (Characters.players.get(0).collisionDetectPVP()) {
                Characters.players.get(0).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
            }
        }else{
            if (Characters.players.get(0).collisionDetect()) {
                Characters.players.get(0).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
            }
        }
    }

    public static void player2Movement(){
        playerBombActivation();
        if(Characters.players.get(1).collisionDetectPVP()) {
            Characters.players.get(1).playerMove();
//            System.out.println("x = " + Character.players.get(0).px + "  y = " + Character.players.get(0).py);
        }
    }

    public static void absorb1ToIntersection(){
        if(!(players.get(0).up || players.get(0).right || players.get(0).left || players.get(0).down)) {
            players.get(0).px = Math.round(Characters.players.get(0).px / 15.0f) * 15;
            players.get(0).py = Math.round(Characters.players.get(0).py / 15.0f) * 15;
        }
    }

    public static void absorb2ToIntersection(){
        if(!(players.get(1).up || players.get(1).right || players.get(1).left || players.get(1).down)) {
            players.get(1).px = Math.round(Characters.players.get(1).px / 15.0f) * 15;
            players.get(1).py = Math.round(Characters.players.get(1).py / 15.0f) * 15;
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
        if(Characters.players.get(0).exist){
            Characters.players.get(0).render();
        }else {
            GameLoop.gameLost = true;
            players.get(0).otherPlayerWon = true;
            shrinkNumber = 1f;
        }
    }

    public static void player2Render(){
        players.get(1).ifDamageCharacter();
        //players.get(1).ifTouchEnemy();
        if(Characters.players.get(1).exist){
            Characters.players.get(1).render();
        }else {
            players.get(1).otherPlayerWon = true;
        }
    }

    public void ifEnterTheNextWorld(){
        if(!this.isHavingTheKey){
            return;
        }
        int distance = (int)dist(Door.door.x, Door.door.y, this.px, this.py);
        if(distance < 30){
            timer -= 1;
        }else{
            timer = 120;
        }
        if(timer <= 0){
            this.px = 45;
            this.py = 105;
            Shop.resetShops();
            BreakableRock.updateRockPositions(parent);
            Obstacle.initializeObstacleGrid();
            Enemy.updateEnemiesPositions(parent);

            Items.removeOtherItems();

            ExtraLife.resetExtraLives(parent);
            Coin.resetCoins(parent);
            Door.door.resetDoor();
        }
    }

    public int getHealth() {
        return this.health;
    }
    public int getCoin() {
        return this.coin;
    }


    public void move() {
        if (activeStack){
            if (java.util.Objects.equals(stack[stackCounter], "up")) {
                up();
                offsetY -= speed;
                lastMove="up";
            }
            if (java.util.Objects.equals(stack[stackCounter], "down")) {
                down();
                offsetY += speed;
                lastMove="down";
            }
            if (java.util.Objects.equals(stack[stackCounter], "left")) {
                left();
                offsetX -= speed;
                lastMove="left";
            }
            if (java.util.Objects.equals(stack[stackCounter], "right")) {
                right();
                offsetX += speed;
                lastMove="right";
            }
            stackCounter++;
            stackIndex--;
            if (stackIndex==0){
                activeStack=false;
                stackCounter=0;
                //ready=true;
            }
        }
        if (move && !activeStack){ //&& !ready
            println(x(), y());
            int x=0, y=0;
            if (up) {
                x=x()+tile/2;
                y=y()+tile/2-speed;
            }
            if (down) {
                x=x()+tile/2;
                y=y()+tile/2+speed;
            }
            if (left) {
                x=x()+tile/2-speed;
                y=y()+tile/2;
            }
            if (right) {
                x=x()+tile/2+speed;
                y=y()+tile/2;
            }
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (!java.util.Objects.equals(tiles[topLeftR+4+i][topLeftC+14+k].type(), "floor") && dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() + (float) tile / 2, tiles[topLeftR+4+i][topLeftC+14+k].y() + (float) tile / 2) < tile) {
                        if (dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() + (float) tile / 2, tiles[topLeftR+4+i][topLeftC+14+k].y() + (float) tile / 2) < tile) {
                            collision = true;
                            cntCollision++;
                            if (firstCollision) {
                                CornerR = topLeftR+4+i;
                                CornerC = topLeftC+14+k;
                                firstCollision = false;
                            }
                        }
                    }
                    if (Objects.equals(tiles[topLeftR+4+i][topLeftC+14+k].type(), "floor") && tiles[topLeftR+4+i][topLeftC+14+k].crossRoads) {
                        boolean a=dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() , tiles[topLeftR+4+i][topLeftC+14+k].y()) < (float) tile /2, b=dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() + tile, tiles[topLeftR+4+i][topLeftC+14+k].y()) < (float) tile /2, c=dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x(), tiles[topLeftR+4+i][topLeftC+14+k].y() + tile) < (float) tile /2, d=dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() + tile, tiles[topLeftR+4+i][topLeftC+14+k].y() + tile) < (float) tile /2;
                        if (a||b||c||d) {
                            collision = true;
                        }
                    }
                }
                if ( i < totBombs && dist(x, y, bombs[i].x()+ (float) tile /2, bombs[i].y()+ (float) tile /2)<tile && !placed){
                    collision = true;
                    cntCollision++;
                }
                if ( i < totEnemies && dist(x, y, enemies[i].x()+ (float) tile /2, enemies[i].y()+ (float) tile /2)<tile){
                    collision = true;
                    cntCollision++;
                }
            }
            if (cntCollision==1 && !currentMove.equals(lastMove)){
                if (up || down) {
                    int dx = x() + tile / 2 - tiles[CornerR][CornerC].x() - tile / 2;
                    if (lastMove.equals("left") && dx <0) {
                        corner=abs((tile+dx))/speed;
                        for (int i = 0; i <corner; i++) {
                            stack[stackIndex] = "left";
                            stackIndex++;
                        }
                    }
                    if (lastMove.equals("right") && dx > 0) {
                        corner=abs((tile - dx))/speed;
                        for (int i=0; i<corner; i++){
                            stack[stackIndex] = "right";
                            stackIndex++;
                        }
                    }
                }
                if (left || right) {
                    int dy = y() + tile / 2 - tiles[CornerR][CornerC].y() - tile / 2;
                    if (lastMove.equals("up") && dy<0) {
                        corner=abs((tile+dy))/speed;
                        for (int i=0; i<corner; i++){
                            stack[stackIndex] = "up";
                            stackIndex++;
                        }
                    }
                    if (lastMove.equals("down") && dy>0) {
                        corner=abs((tile-dy))/speed;
                        for (int i=0; i<corner; i++){
                            stack[stackIndex] = "down";
                            stackIndex++;
                        }
                    }
                }
                if (stackIndex >=1) {
                    activeStack = true;
                    for (int i=0; i<6; i++){
                        stack[stackIndex+i]=currentMove;
                        stackIndex++;
                    }
                    //ready=true;
                }
            }
            if (!collision && !activeStack) {
                if (up) {
                    up();
                    offsetY -= speed;
                    lastMove="up";
                }
                if (down) {
                    down();
                    offsetY += speed;
                    lastMove="down";
                }
                if (left) {
                    left();
                    offsetX -= speed;
                    lastMove="left";
                }
                if (right) {
                    right();
                    offsetX += speed;
                    lastMove="right";

                }
            }
            /*
            if (!activeStack) {
                ready=true;
            }
            */
            collision=false;
            cntCollision=0;
            up=false;
            down=false;
            left=false;
            right=false;
            move=false;
            topLeft=true;
            firstCollision=true;
            topLeftR=0;
            topLeftC=0;
    }
}
