package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameLoop extends PApplet{
    boolean menu=true, play=false,  up=false, down=false, left=false, right=false, bomb=false;
    int width=960, height=540, bx=0, by=0, frameCount=0, uWallsCounter=0, uWallsTotal=0, bombsTotal=0, enemiesTotal=1;
    public void settings() {
        size(width, height);
        //surface.setTitle("GameLoop DUNGEON");
        //noLoop();
    }
    PImage player1Image;
    PImage bomb1Image;
    PImage uWall;

    PImage enemy1Image;
    Player pone;
    Bomb [] bombs = new Bomb [3];
    int[] bombTimer ={0,0,0};
    Wall [] uWalls = new Wall [200];
    Enemy [] enemies = new Enemy [200];

    //load variables
    public void setup(){
        frameRate(60);
        player1Image = loadImage("images/player.png");
        pone = new Player(45, 105,this, player1Image);
        bomb1Image = loadImage("images/bomb.png");
        uWall = loadImage("images/unbreakable_wall.png");
        enemy1Image =loadImage("images/red_mob.png");
        enemies[0]= new Enemy(225, 105,this, enemy1Image);
    }
    public void draw() {
        if(menu) {
            background(87, 108, 164);
            PFont Cherry = createFont("fonts/CherryBombOne-Regular.ttf", 60);
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            //String[] fontList = PFont.list();
            //printArray(fontList);
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
            menu=false;
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);
            for (int i = 15; i < width - 15; i += 30) {
                uWalls[uWallsCounter] = new Wall(i, 75, this, uWall);
                uWalls[uWallsCounter].render();
                uWallsCounter++;
                uWalls[uWallsCounter] = new Wall(i, height-15-30, this, uWall);
                uWalls[uWallsCounter].render();
                uWallsCounter++;
                //square(i, 75, 30);
                //square(i, height - 15 - 30, 30);
            }
            for (int j = 75; j < height - 15; j += 30) {
                uWalls[uWallsCounter] = new Wall(15, j, this, uWall);
                uWalls[uWallsCounter].render();
                uWallsCounter++;
                uWalls[uWallsCounter] = new Wall(width-15-30, j, this, uWall);
                uWalls[uWallsCounter].render();
                uWallsCounter++;
                //square(15, j, 30);
                //square(width - 15-30, j, 30);
            }
            for (int i = 75; i < width - 15-30; i += 60) {
                for (int j = 135; j < height - 15-30; j += 60) {
                    uWalls[uWallsCounter] = new Wall(i, j, this, uWall);
                    uWalls[uWallsCounter].render();
                    uWallsCounter++;
                    //square(i, j, 30);
                }
            }
            uWallsTotal=uWallsCounter;
            uWallsCounter=0;
            pone.render();
            enemies[0].render();
        }
        if(up){
            boolean collision=false;
            for (int i =0; i < uWallsTotal; i++) {
                if (pone.x()==uWalls[i].x() && pone.y()-30==uWalls[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < bombsTotal; i++) {
                if (pone.x()==bombs[i].x() && pone.y()-30==bombs[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < enemiesTotal; i++) {
                if (pone.x()==enemies[i].x() && pone.y()-30==enemies[i].y()){
                    collision=true;
                }
            }
            if (!collision){
                pone.up();
            }
            up=false;
        }
        if(down){
            boolean collision=false;
            for (int i =0; i < uWallsTotal; i++) {
                if (pone.x()==uWalls[i].x() && pone.y()+30==uWalls[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < bombsTotal; i++) {
                if (pone.x()==bombs[i].x() && pone.y()+30==bombs[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < enemiesTotal; i++) {
                if (pone.x()==enemies[i].x() && pone.y()+30==enemies[i].y()){
                    collision=true;
                }
            }
            if (!collision){
                pone.down();
            }
            down=false;
        }
        if(left){
            boolean collision=false;
            for (int i =0; i < uWallsTotal; i++) {
                if (pone.x() - 30 == uWalls[i].x() && pone.y() == uWalls[i].y()) {
                    collision = true;
                }
            }
            for (int i =0; i < bombsTotal; i++) {
                if (pone.x() - 30 == bombs[i].x() && pone.y() == bombs[i].y()) {
                    collision = true;
                }
            }
            for (int i =0; i < enemiesTotal; i++) {
                if (pone.x() - 30 == enemies[i].x() && pone.y() == enemies[i].y()) {
                    collision = true;
                }
            }
            if (!collision){
                pone.left();
            }
            left=false;
        }
        if(right){
            boolean collision=false;
            for (int i =0; i < uWallsTotal; i++) {
                if (pone.x()+30==uWalls[i].x() && pone.y()==uWalls[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < bombsTotal; i++) {
                if (pone.x()+30==bombs[i].x() && pone.y()==bombs[i].y()){
                    collision=true;
                }
            }
            for (int i =0; i < enemiesTotal; i++) {
                if (pone.x()+30==enemies[i].x() && pone.y()==enemies[i].y()){
                    collision=true;
                }
            }
            if (!collision){
                pone.right();
            }
            right=false;
        }
        if(bomb){
            //println(bombsTotal);
            if (bombsTotal<3) {
                bombs[bombsTotal] = new Bomb(bx, by, this, bomb1Image);
                bombs[bombsTotal].render();
                bombTimer[bombsTotal]=240;
                bombsTotal++;
            }
            bomb=false;
        }
        if (bombsTotal>0) {
            println(bombsTotal, bombTimer[0], bombTimer[1], bombTimer[2], "\n");
            for (int i = 0; i < bombsTotal; i++) {
                if (bombTimer[i] > 0) {
                    bombTimer[i]--;
                    bombs[i].render();
                }
                if (bombTimer[i] == 0 && i+1 == bombsTotal) {
                    bombsTotal--;
                }
                if (bombTimer[i] == 0 && i + 1 < bombsTotal) {
                    for( int j=i; j<bombsTotal-1; j++) {
                        bombs[j] = bombs[j+1];
                        bombTimer[j] = bombTimer[j+1];
                        bombTimer[j+1]=0;
                    }
                    bombsTotal--;
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
        if (key=='w') {
            up=true;
        }
        if (key == 's'){
            down=true;
        }
        if (key =='a'){
            left=true;
        }
        if (key =='d'){
            right=true;
        }
        if (keyCode == SHIFT){
            bomb=true;
            bx=pone.x();
            by=pone.y();
        }
    }
    public static void main(String[] args){
        //String[] processingArgs = {"GameLoop"};
        //GameLoop dungeon = new GameLoop();
        //PApplet.runSketch(processingArgs, dungeon);
        PApplet.main("org.example.GameLoop");
    }
}