package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    boolean menu=true, play=false,  move=false, up=false, down=false, left=false, right=false, bomb=false;
    int speed=5, maxBombs=3, totBombs=0, totEnemies=1, explosionDistance = 1;
    int rows = 15, cols = 31;
    float chanceOfRock = 0.5F;
    PImage pinkBomber, basicBomb, unbreakable, redEnemy, rock, flame;
    Player [] players = new Player [2];
    Bomb [][] bombs = new Bomb [cols][rows];
    Flame [][] flames = new Flame[cols][rows];
    ArrayList<Wall> walls;
    ArrayList<BreakableRock> rocks;
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
        rock =loadImage("images/rock.png");
        flame = loadImage("images/fire.png");
        players[0] = new Player(45, 105,this, pinkBomber);
        enemies[0]= new Enemy(225, 105,this, redEnemy);
        rocks = generateRocks(rows,cols,chanceOfRock);
        initializeBombs();
        initializeFlames();
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
            walls = generateWalls(rows, cols);
            for (Wall wall : walls){
                wall.render();
            }
            for (BreakableRock rock : rocks){
                if(rock.rockExist) {
                    rock.render();
                }
                //handle the interaction between rocks and flames
                if(flameCheck(rock.x(),rock.y())){
                    rock.health -=1;
                    if(rock.health == 0){
                        rock.rockExist = false;
                    }
                }
            }
            players[0].render();
            enemies[0].render();
            for (Bomb [] row :bombs) {
                for (Bomb bomb : row) {
                    int packedNumber = bomb.update();
                    if(bomb.showed){
                        bomb.render();
                    }
                    if (packedNumber != 0){
                        int x = packedNumber / 10000;
                        int y = packedNumber % 10000;
                        creatFlame(x,y,explosionDistance);
                        packedNumber = 0;
                    }
                }
            }
            for (Flame [] row :flames) {
                for (Flame flame : row) {
                    if (flame.showed){
                        flame.render();
                    }
                    flame.update();
                }
            }

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
            //Collision detection
            boolean collision = false;
            for (Wall wall : walls){
                if(dist(x, y, wall.x()+ (float) tile /2, wall.y()+ (float) tile /2)<tile){
                    collision = true;
                }
            }
            for(BreakableRock rock : rocks){
//                if (!rock.rockExist){
//                    collision = false;
//                }
                if(rock.rockExist && dist(x, y, rock.x()+ (float) tile /2, rock.y()+ (float) tile /2)<tile){
                    collision = true;
                }
            }
            for (int i = 0; i < 200; i++) {
                if ( i < totEnemies && dist(x, y, enemies[i].x()+ (float) tile /2, enemies[i].y()+ (float) tile /2)<tile){
                    collision = true;
                }
            }
            for (Bomb [] row :bombs){
                for (Bomb bomb :row){
                    if (bomb.showed &&!bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) >= tile) {
                        bomb.bombActive = true;
                    }
                    if (bomb.showed && bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) < tile) {
                        collision = true;
                    }
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
            // Hurt Check
            boolean hurt = false;
            for (Flame [] row : flames){
                for (Flame flame : row){
                    if(flame.showed && dist(x,y,flame.x() +(float) tile / 2, flame.y() + (float) tile / 2) < tile){
                        hurt = true;
                    }
                }
            }
            for (int i = 0; i < 200; i++) {
                if ( i < totEnemies && dist(x, y, enemies[i].x()+ (float) tile /2, enemies[i].y()+ (float) tile /2)<tile){
                    hurt = true;
                }
            }
            if (hurt){
                players[0].health -=1;
                hurt = false;
            }
        }
        if(bomb){
            if (totBombs<maxBombs) {
                int x = players[0].x(), y = players[0].y();
                int col = (x - 15)/tile, row = (y - 75)/tile;
                bombs[col][row].showed = true ;
                bombs[col][row].timer = 3.0;
                totBombs++;
            }
            bomb=false;
        }
        if (totBombs>0) {
            for (Bomb[] row : bombs) {
                for (Bomb bomb : row) {
                    if (!bomb.hasExpired()) {
                        bomb.render();
                    } else {
                        bomb.showed = false;
                        totBombs--;
                    }
                }
            }
        }
    }
    ArrayList<Wall> generateWalls(int rows, int cols) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        // Generate walls in a grid pattern
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Add wall to the outer border and every other grid cell
                if (i == 0 || j == 0 || i == cols - 1 || j == rows - 1 || (i % 2 == 0 && j % 2 == 0)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    walls.add(new Wall(x, y, this, unbreakable));
                }
            }
        }
        return walls;
    }
    ArrayList<BreakableRock> generateRocks(int rows, int cols, float chanceOfRock) {
        ArrayList<BreakableRock> rocks = new ArrayList<BreakableRock>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Randomly decide whether to place a rock in this grid cell
                // Exclude the area around where player initially stand
                if (random(1) < chanceOfRock && !isWallAt(i, j) && !(i <4 && j <4)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    rocks.add(new BreakableRock(x, y, this,rock));
                }
            }
        }
        return rocks;
    }
    boolean isWallAt(int i, int j) {
        return i == 0 || j == 0 || i == cols - 1 || j == rows - 1 || (i % 2 == 0 && j % 2 == 0);
    }
    void initializeBombs(){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                bombs[col][row] = new Bomb(x,y,this,basicBomb);
            }
        }
    }
    void initializeFlames(){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                flames[col][row] = new Flame(x,y,this,flame);
            }
        }
    }
    void creatFlame(int x, int y, int explosionDistance){
        int col = (x - 15)/tile;
        int row = (y - 15)/tile-2;
        flames[col][row].appear();
        for (int i = 1; i <= explosionDistance; i++) {
            // Modify the grids to the left
            if (col - i >= 0) {
                flames[col - i][row].appear();
            }
            // Modify the grids to the right
            if (col + i < rows) {
                flames[col + i][row].appear();
            }
            // Modify the grids upside
            if (row - i >= 0) {
                flames[col][row - i].appear();

            }
//            // Modify the grids downside
            if (row + i < cols) {
                flames[col][row + i].appear();
            }
        }

    }
    // Check if flame exist in a position
    boolean flameCheck(int x,int y){
        int col = (x - 15)/tile;
        int row = (y - 15)/tile-2;
        return flames[col][row].showed;
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
        if (key == 'c'){
            bomb=true;
        }
    }
    public static void main(String[] args){
        PApplet.main("org.example.GameLoop");
    }
}