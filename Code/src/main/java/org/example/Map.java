package org.example;
import processing.core.PApplet;
import java.util.Objects;
import java.util.Random;
import static java.util.Objects.nonNull;

public class Map extends GameLoop {
    public static final int gens=100;
    public static Tile [][] tiles = new Tile[rows*9][cols*9];
    static int [][] map = new int[rows][cols];
    static Random random = new Random();
    PApplet game;

    static void generator() {
        int rms=0;
        for(int h=0; h<rows; h++){
            for(int i=0; i<cols; i++){
                map[h][i]=0;
            }
        }
        for(int i=0; i<2; i++) {
            for(int j=0; j<4; j++) {
                map[rows/2-1-1+i][cols/2-1+j]=1;
            }
        }
        for(int a=0; a<gens; a++){
            for(int b=0; b<rows; b++){
                for(int c=0; c<cols; c++){
                    if(map[b][c]==1){
                        int counter=0;
                        for(int d=-1; d<2; d++){
                            for(int e=-1; e<2; e++){
                                if(b+d>=0 && b+d<rows && c+e>=0 && c+e<cols && map[b+d][c+e]!=0){
                                    counter++;
                                }
                            }
                        }
                        for(int d=-1; d<2; d++){
                            for(int e=-1; e<2; e++){
                                if(b+d>=0 && b+d<rows && c+e>=0 && c+e<cols){
                                    if((Math.abs(d)==1 && e==0) || (d==0 && Math.abs(e)==1)){
                                        if(map[b+d][c+e]==0){
                                            int r=random.nextInt(100);
                                            if(counter<3 && r<6){
                                                map[b+d][c+e]=1;
                                            }
                                            if(counter>=3 && counter<=5 && r<1){
                                                map[b+d][c+e]=1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int f=0; f<rows; f++){
            for(int g=0; g<cols; g++){
                if(map[f][g]==1){
                    rms++;
                }
            }
        }
        if(rms<(rows*cols/7) || rms>(rows*cols/3)){
            generator(game);
        }
        translator();
    }

    private void translator() {
        for(int h=0; h<rows; h++){
            for(int i=0; i<cols; i++){
                if(map[h][i]==1){
                    for(int j=0; j<8; j++){
                        for(int k=0; k<8; k++){
                            tiles[h*8+1+k][i*8+1+j]= new Tile(h*8+1+k, i*8+1+j, game,Sprites.wall, "wall");
                        }
                    }
                }
            }
        }
        for(int j=0; j<rows*8; j++){
            for(int k=0; k<cols*8; k++){
                if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "wall")){
                    tiles[j-1][k]=new Tile(j-1, k, game,Sprites.wall, "wall");
                    tiles[j][k-1]=new Tile(j, k-1, game,Sprites.wall, "wall");
                    tiles[j-1][k-1]=new Tile(j-1, k-1, game,Sprites.wall, "wall");
                }
            }
        }

        for(int j=0; j<rows*8; j++){
            for(int k=0; k<cols*8; k++){
                if(j-1>=0 && k-1>=0 && j+1<(rows*8) && k+1<(cols*8) && tiles[j-1][k-1]!=null && tiles[j-1][k]!=null && tiles[j-1][k+1]!=null
                        && tiles[j][k-1]!=null && tiles[j][k+1]!=null && tiles[j+1][k-1]!=null && tiles[j+1][k]!=null && tiles[j+1][k+1]!=null){
                    tiles[j][k].type="floor";
                }
            }
        }

        for(int j=0; j<rows*8; j++){
            for(int k=0; k<cols*8; k++){
                if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor") && Objects.equals(tiles[j - 1][k].type, "floor") && Objects.equals(tiles[j][k - 1].type, "floor") && Objects.equals(tiles[j - 1][k - 1].type, "floor")){
                    tiles[j][k].type="wall";
                    tiles[j][k].skin=Sprites.wall;
                }
            }
        }
        for(int j=0; j<rows*8; j++){
            for(int k=0; k<cols*8; k++){
                if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor")) {
                    boolean a=Objects.equals(tiles[j+1][k].type, "floor"), b=Objects.equals(tiles[j][k+1].type, "floor"), c=Objects.equals(tiles[j-1][k].type, "floor"), d=Objects.equals(tiles[j][k-1].type, "floor");
                    if (a ? (b || c || d) : b ? ( c || d) : c && d) {
                        tiles[j][k].crossRoads = true;
                    }
                }
            }
        }
        // breakable walls (rock tiles)
        for(int a=0; a<gens/5; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<2){
                        tiles[j][k].type="rock";
                        tiles[j][k].skin=Sprites.rock;
                    }
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor") && r<1){
                        tiles[j][k].type="rock";
                        tiles[j][k].skin=Sprites.rock;
                    }
                }
            }
        }
        // place shops
        for(int a=0; a<gens/5; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Settings.maxShops>0 && Objects.equals(tiles[j][k].type, "floor") && r<1){
                        tiles[j][k].type="shop";
                        tiles[j][k].skin=Sprites.shop;
                        Settings.maxShops--;
                    }
                }
            }
        }
        // place door
        for(int a=0; a<gens/5; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Settings.maxDoors>0 && Objects.equals(tiles[j][k].type, "floor") && r<1){
                        tiles[j][k].type="door";
                        tiles[j][k].skin=Sprites.door;
                        Settings.maxDoors--;
                    }
                }
            }
        }
        // place enemies
        for(int a=0; a<gens/5; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Settings.maxEnemies>0 && Objects.equals(tiles[j][k].type, "floor") && r<1){
                        tiles[j][k].type="enemy";
                        tiles[j][k].skin=Sprites.redEnemy;
                        Settings.maxDoors--;
                    }
                }
            }
        }
        // hide coins
        for(int a=0; a<gens/6; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1 && Settings.maxCoins>0){
                        tiles[j][k].type="coin";
                        Settings.maxCoins--;
                    }
                }
            }
        }
        // hide bombUp
        for(int a=0; a<gens/6; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1 && Settings.maxBombUp>0){
                        tiles[j][k].type="coin";
                        Settings.maxBombUp--;
                    }
                }
            }
        }
        // hide fireUp
        for(int a=0; a<gens/6; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1 && Settings.maxFireUp>0){
                        tiles[j][k].type="coin";
                        Settings.maxFireUp--;
                    }
                }
            }
        }
        // hide speedUp
        for(int a=0; a<gens/6; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1 && Settings.maxSpeedUp>0){
                        tiles[j][k].type="coin";
                        Settings.maxSpeedUp--;
                    }
                }
            }
        }
        // hide healthUp
        for(int a=0; a<gens/6; a++){
            for(int j=0; j<rows*8; j++){
                for(int k=0; k<cols*8; k++){
                    int r=random.nextInt(100);
                    if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1 && Settings.maxHealthUp>0){
                        tiles[j][k].type="coin";
                        Settings.maxHealthUp--;
                    }
                }
            }
        }
        // Assign coordinates to tiles
        for(int j=0; j<rows*8; j++){
            for(int k=0; k<cols*8; k++){
                if(tiles[j][k]!=null) {
                    tiles[j][k].px=(15+k*30);
                    tiles[j][k].py=(75+j*30);
                }
            }
        }
        // clear starting area (can clear shops and the door, fix it)
        for(int j=0; j<3; j++){
            for(int k=0; k<3; k++){
                if (tiles[37+j][63+k]!=null && !tiles[37+j][63+k].type.equals("wall")) {
                    tiles[37+j][63 +k].type="floor";
                }
            }
        }
    }
    public static void render(PApplet game) {
        GameLoop.menu = false;
        game.background(85, 85, 85);
        game.translate(-offsetX, -offsetY);
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
        Characters.players.get(0).render();
        fill(165, 165, 165);
        noStroke();
        rect(offsetX, offsetY, width, hud);
        rect(offsetX, offsetY+hud, margin, height-hud);
        rect(offsetX+width-margin, offsetY+hud, margin, height-hud);
        rect(offsetX, offsetY+height-margin, width, margin);
    }
}