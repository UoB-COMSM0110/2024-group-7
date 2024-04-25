package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.Objects;

import static java.util.Objects.nonNull;


public class oldgameloop extends PApplet{
    public static final int tile=30;
    public static final int fps=120;
    public static final int width=960;
    public static final int height=540;
    public static final int rows=12;
    public static final int cols=14;
    public static final int margin=15;
    public static final int hud=75;
    boolean ready=true, activeStack=false, collision=false, menu=true, play=false,  move=false, up=false, down=false, left=false, right=false, bomb=false, placed=false, topLeft=true, firstCollision=true;
    public int stackIndex=0, stackCounter=0, speed=5, maxBombs=3, totBombs=0, totEnemies=1, offsetX=(49*tile), offsetY=(33*tile), topLeftR=0, topLeftC=0, cntCollision=0, CornerR=0, CornerC=0, corner=1;
    String currentMove="", lastMove="";
    PImage pinkBomber, basicBomb, wall, rock, crystal, redEnemy;
    Player [] players = new Player [2];
    Bomb [] bombs = new Bomb [maxBombs];
    int[] bombTimer ={0,0,0};
    Tile [][] tiles = new Tile[rows*9][cols*9];
    Enemy [] enemies = new Enemy [200];
    String [] stack = new String[20];

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);
        wall = loadImage("images/unbreakable_wall.png");
        rock = loadImage("images/rock.png");
        crystal = loadImage("images/crystal.png");
        pinkBomber = loadImage("images/player.png");
        basicBomb = loadImage("images/bomb.png");
        redEnemy =loadImage("images/red_mob.png");
        players[0] = new Player(15+offsetX+14*tile, 75+offsetY+5*tile,this, pinkBomber);
        enemies[0]= new Enemy(255+offsetX, 105+offsetY,this, redEnemy);
        Map map = new Map(this, wall, rock, crystal, redEnemy);
        tiles = map.instructions();
    }

    public void draw() {
        if(menu) {
            background(87, 108, 164);
            PFont Cherry = createFont("fonts/CherryBombOne-Regular.ttf", 60);
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            textFont(Cherry, 150);
            textAlign(CENTER);
            fill(237, 87, 33);
            text("ボンバーマン", 0, 150, width, 300);
            textFont(Daruma, 35);
            textAlign(CENTER);
            fill(250, 236, 0);
            text("Play", 0, 450, ((float) width / 4), 100);
            text("Character", ((float) width / 4), 450, ((float) width / 4), 100);
            text("Achievements", ((float) width / 4 * 2), 450, ((float) width / 4), 100);
            text("Settings", ((float) width / 4 * 3), 450, ((float) width / 4), 100);
            textSize(100);
            text("Dungeon", 0, 250, width, 100);
        }
        if(play){
            menu = false;
            background(85, 85, 85);
            translate(-offsetX, -offsetY);
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    if(tiles[j][k]!=null && tiles[j][k].px>offsetX-tile && tiles[j][k].px<offsetX+width && tiles[j][k].py>offsetY+hud-tile && tiles[j][k].py<offsetY+height){
                        tiles[j][k].render();
                        if (topLeft) {
                            topLeftR = j;
                            topLeftC = k;
                            topLeft=false;
                        }
                    }
                }
            }
            players[0].render();
            for (int i = 0; i < totEnemies; i++) {
                if ( nonNull(enemies[i]) && enemies[i].x()>offsetX && enemies[i].x()<offsetX+width-margin && enemies[i].y()>offsetY+hud && enemies[i].y()<offsetY+height){
                    enemies[i].render();
                }
            }
        }
        fill(165, 165, 165);
        noStroke();
        rect(offsetX, offsetY, width, hud);
        rect(offsetX, offsetY+hud, margin, height-hud);
        rect(offsetX+width-margin, offsetY+hud, margin, height-hud);
        rect(offsetX, offsetY+height-margin, width, margin);
        if (activeStack){
            //ready=false;
            println(players[0].x(), players[0].y());
            if (Objects.equals(stack[stackCounter], "up")) {
                players[0].up();
                offsetY -= speed;
                lastMove="up";
            }
            if (Objects.equals(stack[stackCounter], "down")) {
                players[0].down();
                offsetY += speed;
                lastMove="down";
            }
            if (Objects.equals(stack[stackCounter], "left")) {
                players[0].left();
                offsetX -= speed;
                lastMove="left";
            }
            if (Objects.equals(stack[stackCounter], "right")) {
                players[0].right();
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
            println(players[0].x(), players[0].y());
            int x=0, y=0;
            if (up) {
                x=players[0].x()+tile/2;
                y=players[0].y()+tile/2-speed;
            }
            if (down) {
                x=players[0].x()+tile/2;
                y=players[0].y()+tile/2+speed;
            }
            if (left) {
                x=players[0].x()+tile/2-speed;
                y=players[0].y()+tile/2;
            }
            if (right) {
                x=players[0].x()+tile/2+speed;
                y=players[0].y()+tile/2;
            }
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (!Objects.equals(tiles[topLeftR+4+i][topLeftC+14+k].type(), "floor") && dist(x, y, tiles[topLeftR+4+i][topLeftC+14+k].x() + (float) tile / 2, tiles[topLeftR+4+i][topLeftC+14+k].y() + (float) tile / 2) < tile) {
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
                    int dx = players[0].x() + tile / 2 - tiles[CornerR][CornerC].x() - tile / 2;
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
                    int dy = players[0].y() + tile / 2 - tiles[CornerR][CornerC].y() - tile / 2;
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
                    players[0].up();
                    offsetY -= speed;
                    lastMove="up";
                }
                if (down) {
                    players[0].down();
                    offsetY += speed;
                    lastMove="down";
                }
                if (left) {
                    players[0].left();
                    offsetX -= speed;
                    lastMove="left";
                }
                if (right) {
                    players[0].right();
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
            // Maybe make bomb only appear after moving
            if (totBombs>=1 && dist(x, y, bombs[totBombs-1].x()+ (float) tile /2, bombs[totBombs-1].y()+ (float) tile /2)>tile){
                placed=false;
            }
        }
        if(bomb){
            if (totBombs<maxBombs) {
                bombs[totBombs] = new Bomb(players[0].x(), players[0].y(), this, basicBomb);
                bombs[totBombs].render();
                bombTimer[totBombs]=240;
                totBombs++;
            }
            bomb=false;
        }
        if (totBombs>0) {
            for (int i = 0; i < totBombs; i++) {
                if (bombTimer[i] > 0) {
                    bombTimer[i]--;
                    bombs[i].render();
                }
                if (bombTimer[i] == 0 && i+1 == totBombs) {
                    totBombs--;
                }
                if (bombTimer[i] == 0 && i + 1 < totBombs) {
                    for( int j=i; j<totBombs-1; j++) {
                        bombs[j] = bombs[j+1];
                        bombTimer[j] = bombTimer[j+1];
                        bombTimer[j+1]=0;
                    }
                    totBombs--;
                }
            }
        }
    }
    public void mouseClicked() {
        if (mouseX>=150 && mouseX<350) {
            link("https://github.com/UoB-COMSM0110/2024-group-7");
        }
        if (mouseX>=0 && mouseX<(width/4) && mouseY>=450 && mouseY<540) {
            play=true;
        }
    }
    public void keyPressed(){
        //if (!ready) {
        if (key == 'w' && !move) {
            currentMove = "up";
            up = true;
            move = true;
        }
        if (key == 's' && !move) {
            currentMove = "down";
            down = true;
            move = true;
        }
        if (key == 'a' && !move) {
            currentMove = "left";
            left = true;
            move = true;
        }
        if (key == 'd' && !move) {
            currentMove = "right";
            right = true;
            move = true;
        }
        if (keyCode == SHIFT) {
            bomb = true;
            placed = true;
        }
        //}
        //ready=false;
    }
    public static void main(String[] args){
        PApplet.main("org.example.GameLoop");
    }
}