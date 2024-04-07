package org.example;

import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

//还差炸弹跟消除道具

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    public static boolean menu=true, play=false, move=false, up=false, down=false, left=false, right=false, bomb=false;
    int totBombs=0, totEnemies=1;
    public static int rows = 15, cols = 31;
    public static Bomb [][] bombs = new Bomb [cols][rows];
    Flame [][] flames = new Flame[cols][rows];
    boolean gameWon = false;

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);

        ResourceManager.loadAllImages(this);

        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);

        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this);

        initializeBombs();
        initializeFlames();

        Obstacle.initializeObstacleGrid(rows, cols);

        Character.enemies = Enemy.generateEnemies(this);

        Character.players = Player.setPlayer1(this);

        Items.doorKey = new DoorKey(0,0,this);
        Items.doorKey.setKey(this);

        Items.door = new Door(0,0,this);
        Items.door.setDoor(this);

        BombPowerUp.setPowerUps(this);

        ExtraBomb.setExtraBombs(this);

        SpeedUp.setSpeedUps(this);

    }
    public void draw() {
        System.out.println("x: "+ Character.players.get(0).x() + ",    y: " + Character.players.get(0).y());

        if (menu) {
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

        if (play) {
            menu = false;
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);
            for (Wall wall : Obstacle.walls) {
                wall.render();
            }
            for (Enemy enemy : Character.enemies) {
                enemy.render();
            }

            for (BreakableRock rock : Obstacle.rocks) {
                if (rock.rockExist) {
                    rock.render();
                }
                //handle the interaction between rocks and flames
                if (flameCheck(rock.x(), rock.y())) {
                    rock.health -= 1;
                    if (rock.health == 0) {
                        rock.rockExist = false;
                    }
                }
            }

            if (Items.doorKey != null && !Items.doorKey.collected && Items.doorKey.visible) {
                Player player = Character.players.get(0);
                float distance = dist(player.px, player.py, Items.doorKey.x, Items.doorKey.y);
                if (distance < 30) {
                    Items.doorKey.setCollected();
                }
            }

            if (Items.doorKey != null && Items.doorKey.visible) {
                Items.doorKey.render(this);
            }

            if (Items.door != null && Items.door.visible) {
                Items.door.render(this);
            }

            if (Items.doorKey != null && Items.doorKey.collected && !gameWon) {
                Player player = Character.players.get(0);
                float distanceToDoor = dist(player.px, player.py, Items.door.x, Items.door.y);
                if (distanceToDoor < 30) {
                    gameWon = true;
                    println("You've won the game!");
                }
            }

            BombPowerUp.getPowerUp(this);

            ExtraBomb.getExtraBomb(this);

            SpeedUp.getSpeedUp(this);

            Character.players.get(0).render();

            //generate enemies
            for(Enemy singleEnemy : Character.enemies){
                singleEnemy.render();
            }

            for (Bomb[] row : bombs) {
                for (Bomb bomb : row) {
                    int packedNumber = bomb.update();
                    if (bomb.showed) {
                        bomb.render();
                    }
                    if (packedNumber != 0) {
                        int x = packedNumber / 10000;
                        int y = packedNumber % 10000;
                        creatFlame(x, y);
                    }
                }
            }

            for (Flame[] row : flames) {
                for (Flame flame : row) {
                    if (flame.showed) {
                        flame.render();
                    }
                    flame.update();
                }
            }
            Items.removeMarkedObjects();
        }

        if (move) {
            if(Character.players.get(0).collisionDetect()) {
                Character.players.get(0).playerMove();
            }
        }

        if (bomb) {
            if (totBombs < Character.players.get(0).getMaxBombs()) {
                int playerCenterX = Character.players.get(0).x() + tile / 2;
                int playerCenterY = Character.players.get(0).y() + tile / 2;

                int col = (playerCenterX - 15) / tile;
                int row = (playerCenterY - 75) / tile;

                bombs[col][row].showed = true;
                bombs[col][row].timer = 3.0;
                totBombs++;
            }
            bomb = false;
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
        if(!(up || right || left || down)) {
            Character.players.get(0).px = Math.round(Character.players.get(0).px / 15.0f) * 15;
            Character.players.get(0).py = Math.round(Character.players.get(0).py / 15.0f) * 15;
        }
    }

    void initializeBombs(){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                bombs[col][row] = new Bomb(x,y,this,ResourceManager.basicBomb);
            }
        }
    }
    void initializeFlames(){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                flames[col][row] = new Flame(x,y,this,ResourceManager.flame);
            }
        }
    }

    void creatFlame(int x, int y) {
        int explosionDistance = Character.players.get(0).getExplosionDistance();

        int col = (x-15)/tile;
        int row = (y-75)/tile;

        // Mark the center position where the bomb is placed to explode
        flames[col][row].appear();

        // Direction to check: left, right, up, down
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] direction : directions) {
            for (int i=1; i<=explosionDistance; i++) {
                int newCol = col + direction[0]*i;
                int newRow = row + direction[1]*i;

                if (Wall.isWallAt(newCol, newRow)) break;

                // Otherwise, place flame
                flames[newCol][newRow].appear();

                boolean stopSpreading = checkAndHandleBreakable(newCol, newRow)
                                        || isDoorOrKeyAt(newCol, newRow)
                                        || checkAndDestroyExposedItems(newCol, newRow);
                if(stopSpreading) break;
            }
        }
    }

    boolean isDoorOrKeyAt(int col, int row) {
        return (Items.door.x == col*tile+15 && Items.door.y == row*tile+75) ||
                (Items.doorKey.x == col*tile+15 && Items.doorKey.y == row*tile+75);
    }

    boolean checkAndHandleBreakable(int col, int row) {
        boolean handled = false;

        for (BreakableRock rock : Obstacle.rocks) {
            int rockCol = (rock.x() - 15) / tile;
            int rockRow = (rock.y() - 75) / tile;
            if (rockCol == col && rockRow == row && rock.rockExist) {
                // Mark the rock for removal instead of immediately removing it
                rock.markForRemoval();
                handled = true;
                // Reveal any hidden items under the rock
                if (rock.hideDoor) {
                    Items.door.setVisible(true); // 設置門為可見
                    rock.hideDoor = false; // 更新岩石的狀態，表示門已經不再隱藏
                }
                if (rock.hideKey) {
                    Items.doorKey.setVisible(true); // 設置鑰匙為可見
                    rock.hideKey = false; // 更新岩石的狀態，表示鑰匙已經不再隱藏
                }
                if (rock.hidePowerUp && rock.hiddenPowerUp != null) {
                    rock.hiddenPowerUp.setVisible(true);
                    rock.hidePowerUp = false;
                }
                if (rock.hideExtraBomb && rock.hiddenExtraBomb != null) {
                    rock.hiddenExtraBomb.setVisible(true);
                    rock.hideExtraBomb = false;
                }
                if (rock.hideSpeedUp && rock.hiddenSpeedUp != null) {
                    rock.hiddenSpeedUp.setVisible(true);
                    rock.hideSpeedUp = false;
                }
                break;
            }
        }
        return handled;
    }

    boolean checkAndDestroyExposedItems(int col, int row) {
        boolean itemDestroyed = false;

        // Check and mark power-ups for removal
        for (BombPowerUp powerUp : Items.powerUps) {
            int powerUpCol = (powerUp.x - 15) / tile;
            int powerUpRow = (powerUp.y - 75) / tile;
            if (powerUpCol == col && powerUpRow == row && powerUp.isVisible()) {
                powerUp.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        for (SpeedUp speedUp : Items.speedUps) {
            int speedUpCol = (speedUp.x - 15) / tile;
            int speedUpRow = (speedUp.y - 75) / tile;
            if (speedUpCol == col && speedUpRow == row && speedUp.isVisible()) {
                speedUp.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        // Check and mark extra bombs for removal
        if (!itemDestroyed) {
            for (ExtraBomb extraBomb : Items.extraBombs) {
                int extraBombCol = (extraBomb.x - 15) / tile;
                int extraBombRow = (extraBomb.y - 75) / tile;
                if (extraBombCol == col && extraBombRow == row && extraBomb.isVisible()) {
                    extraBomb.markForRemoval();
                    itemDestroyed = true;
                    break;
                }
            }
        }
        return itemDestroyed;
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

    public void keyPressed() {
        switch (key) {
            case 'w': up = true; break;
            case 's': down = true; break;
            case 'a': left = true; break;
            case 'd': right = true; break;
            case 'c': bomb = true; break;
        }
        move = up || down || left || right;
    }

    public void keyReleased() {
        switch (key) {
            case 'w': up = false; break;
            case 's': down = false; break;
            case 'a': left = false; break;
            case 'd': right = false; break;
        }
        move = up || down || left || right;
    }
}