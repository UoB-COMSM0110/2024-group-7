package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    boolean menu=true, play=false,  move=false, up=false, down=false, left=false, right=false, bomb=false;
    int speed=5, cntGrid=0, totGrid=0, maxBombs=3, totBombs=0, totEnemies=1;
    PImage pinkBomber, basicBomb, unbreakable, redEnemy;
    Player [] players = new Player [2];
    Bomb [] bombs = new Bomb [maxBombs];
    int[] bombTimer ={0,0,0};
    Wall [] grid = new Wall [200];
    Enemy [] enemies = new Enemy [200];

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);
        unbreakable = loadImage("images/unbreakable_wall.png");
        pinkBomber = loadImage("images/player.png");
        basicBomb = loadImage("images/bomb.png");
        redEnemy =loadImage("images/red_mob.png");
        players[0] = new Player(45, 105,this, pinkBomber);
        enemies[0]= new Enemy(225, 105,this, redEnemy);
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
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);
            for (int i = 15; i < width - 15; i += tile) {
                grid[cntGrid] = new Wall(i, 75, this, unbreakable);
                grid[cntGrid].render();
                cntGrid++;
                grid[cntGrid] = new Wall(i, height-15-tile, this, unbreakable);
                grid[cntGrid].render();
                cntGrid++;
            }
            for (int j = 75; j < height - 15; j += tile) {
                grid[cntGrid] = new Wall(15, j, this, unbreakable);
                grid[cntGrid].render();
                cntGrid++;
                grid[cntGrid] = new Wall(width-15-tile, j, this, unbreakable);
                grid[cntGrid].render();
                cntGrid++;
            }
            for (int i = 75; i < width - 15-tile; i += 60) {
                for (int j = 135; j < height - 15-tile; j += 60) {
                    grid[cntGrid] = new Wall(i, j, this, unbreakable);
                    grid[cntGrid].render();
                    cntGrid++;
                }
            }
            totGrid=cntGrid;
            cntGrid=0;
            players[0].render();
            enemies[0].render();
        }
        if (move){
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
            boolean collision = false;
            for (int i = 0; i < 200; i++) {
                if (i < totGrid && dist(x, y, grid[i].x()+ (float) tile /2, grid[i].y()+ (float) tile /2)<tile){
                    collision = true;
                }
                if ( i < totBombs && dist(x, y, bombs[i].x()+ (float) tile /2, bombs[i].y()+ (float) tile /2)<tile){
                    collision = true;
                }
                if ( i < totEnemies && dist(x, y, enemies[i].x()+ (float) tile /2, enemies[i].y()+ (float) tile /2)<tile){
                    collision = true;
                }
            }
            if (!collision){
                if (up) {
                    players[0].up();
                }
                if (down) {
                    players[0].down();
                }
                if (left) {
                    players[0].left();
                }
                if (right) {
                    players[0].right();

                }
            }
            up=false;
            down=false;
            left=false;
            right=false;
            move=false;
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
        if (key=='w' && !move) {
            up=true;
            move=true;
        }
        if (key == 's' && !move){
            down=true;
            move=true;
        }
        if (key =='a' && !move){
            left=true;
            move=true;
        }
        if (key =='d' && !move){
            right=true;
            move=true;
        }
        if (keyCode == SHIFT){
            bomb=true;
        }
    }
    public static void main(String[] args){
        PApplet.main("org.example.GameLoop");
    }
}