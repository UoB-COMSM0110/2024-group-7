package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
    DoorKey doorKey;
    Door door;
    ArrayList<BombPowerUp> powerUps = new ArrayList<BombPowerUp>();
    int powerUp_items = 5;
    ArrayList<ExtraBomb> extraBombs = new ArrayList<ExtraBomb>();
    int extraBomb_items = 5;

    ArrayList<SpeedUp> speedUps = new ArrayList<SpeedUp>();
    int speedUp_items = 5;
    boolean gameWon = false;

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

        HashSet<Integer> chosenIndexes = new HashSet<>();

        int keyRockIndex = (int) random(rocks.size()); // Key hided at a random breakableRock
        while (!chosenIndexes.add(keyRockIndex)) {
            keyRockIndex = (int) random(rocks.size());
        }
        BreakableRock keyRock = rocks.get(keyRockIndex);
        keyRock.setHideKey(true);
        // Initialize key but is not visible
        doorKey = new DoorKey(keyRock.x(), keyRock.y(), this);
        doorKey.setVisible(false);

        /*int doorRockIndex;
        do {
            doorRockIndex = (int) random(rocks.size());
        } while (doorRockIndex == keyRockIndex);*/ // Make sure the door and key are not under the same rock
        int doorRockIndex = (int) random(rocks.size());
        while (!chosenIndexes.add(doorRockIndex)) {
            doorRockIndex = (int) random(rocks.size());
        }
        BreakableRock doorRock = rocks.get(doorRockIndex);
        doorRock.setHideDoor(true);
        door = new Door(doorRock.x(), doorRock.y(), this);
        door.setVisible(false);

        // Randomly select 5 rocks to place bomb firepower enhancement items
        for (int i=0; i<powerUp_items; i++) {
            int powerUpIndex = (int) random(rocks.size());
            while (!chosenIndexes.add(powerUpIndex)) {
                powerUpIndex = (int) random(rocks.size());
            }
            BreakableRock powerUpRock = rocks.get(powerUpIndex);
            BombPowerUp powerUp = new BombPowerUp(powerUpRock.x(), powerUpRock.y(), this);
            powerUpRock.setHidePowerUp(true);
            powerUpRock.setHiddenPowerUp(powerUp); // Associate BombPowerUp and BreakableRock
            powerUps.add(powerUp);
        }

        // Randomly select 5 rocks to hide ExtraBomb items
        for (int i=0; i<extraBomb_items; i++) {
            int extraBombIndex = (int) random(rocks.size());
            while (!chosenIndexes.add(extraBombIndex)) {
                extraBombIndex = (int) random(rocks.size());
            }
            BreakableRock extraBombRock = rocks.get(extraBombIndex);
            ExtraBomb extraBomb = new ExtraBomb(extraBombRock.x(), extraBombRock.y(), this);
            extraBombRock.setHideExtraBomb(true);
            extraBombRock.setHiddenExtraBomb(extraBomb);
            extraBombs.add(extraBomb);
        }

        for (int i=0; i<speedUp_items; i++) {
            int speedUpIndex = (int) random(rocks.size());
            while (!chosenIndexes.add(speedUpIndex)) {
                speedUpIndex = (int) random(rocks.size());
            }
            BreakableRock speedUpRock = rocks.get(speedUpIndex);
            SpeedUp speedUp = new SpeedUp(speedUpRock.x(), speedUpRock.y(), this);
            speedUpRock.setHideSpeedUp(true);
            speedUpRock.setHiddenSpeedUp(speedUp);
            speedUps.add(speedUp);
        }

    }
    public void draw() {

        //updateGame();

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
            walls = generateWalls(rows, cols);
            for (Wall wall : walls) {
                wall.render();
            }

            for (BreakableRock rock : rocks) {
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


            if (doorKey != null && !doorKey.collected && doorKey.visible) {
                Player player = players[0];
                float distance = dist(player.px, player.py, doorKey.x, doorKey.y);
                if (distance < 30) {
                    doorKey.setCollected();
                }
            }

            if (doorKey != null && doorKey.visible) {
                doorKey.render(this);
            }

            if (door != null && door.visible) {
                door.render(this);
            }

            if (doorKey != null && doorKey.collected && !gameWon) {
                Player player = players[0];
                float distanceToDoor = dist(player.px, player.py, door.x, door.y);
                if (distanceToDoor < 30) {
                    gameWon = true;
                    println("You've won the game!");
                }
            }

            for (BombPowerUp powerUp : powerUps) {
                if (powerUp.visible) {
                    powerUp.render(this);
                    // Check if player collects it
                    Player player = players[0];
                    float distanceToPowerUp = dist(player.px, player.py, powerUp.x, powerUp.y);
                    if (distanceToPowerUp < 30) {
                        powerUp.setVisible(false);
                        players[0].increasePower();
                    }
                }
            }

            for (ExtraBomb extraBomb : extraBombs) {
                if (extraBomb.visible) {
                    extraBomb.render(this);
                    // Check if player collects it
                    Player player = players[0];
                    float distanceToPowerUp = dist(player.px, player.py, extraBomb.x, extraBomb.y);
                    if (distanceToPowerUp < 30) {
                        extraBomb.setVisible(false);
                        players[0].increaseMaxBomb();
                    }
                }
            }

            for (SpeedUp speedUp : speedUps) {
                if (speedUp.isVisible()) {
                    speedUp.render(this);
                    Player player = players[0];
                    float distanceToSpeedUp = dist(player.px, player.py, speedUp.x, speedUp.y);
                    if (distanceToSpeedUp < 30) {
                        speedUp.setVisible(false);
                        players[0].increaseSpeed();
                    }
                }
            }

            players[0].render();
            enemies[0].render();
            for (Bomb[] row : bombs) {
                for (Bomb bomb : row) {
                    int packedNumber = bomb.update();
                    if (bomb.showed) {
                        bomb.render();
                    }
                    if (packedNumber != 0) {
                        int x = packedNumber / 10000;
                        int y = packedNumber % 10000;
                        //creatFlame(x,y,explosionDistance);
                        creatFlame(x, y);
                        packedNumber = 0;
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
            removeMarkedObjects();


        }
        if (move) {
            int x = 0, y = 0;
            if (up) {
                x = players[0].x() + tile / 2;
                y = players[0].y() + tile / 2 - players[0].getSpeed();
            }
            if (down) {
                x = players[0].x() + tile / 2;
                y = players[0].y() + tile / 2 + players[0].getSpeed();
            }
            if (left) {
                x = players[0].x() + tile / 2 - players[0].getSpeed();
                y = players[0].y() + tile / 2;
            }
            if (right) {
                x = players[0].x() + tile / 2 + players[0].getSpeed();
                y = players[0].y() + tile / 2;
            }
            //Collision detection
            boolean collision = false;
            for (Wall wall : walls) {
                if (dist(x, y, wall.x() + (float) tile / 2, wall.y() + (float) tile / 2) < tile) {
                    collision = true;
                }
            }
            for (BreakableRock rock : rocks) {
//                if (!rock.rockExist){
//                    collision = false;
//                }
                if (rock.rockExist && dist(x, y, rock.x() + (float) tile / 2, rock.y() + (float) tile / 2) < tile) {
                    collision = true;
                }
            }
            for (int i = 0; i < 200; i++) {
                if (i < totEnemies && dist(x, y, enemies[i].x() + (float) tile / 2, enemies[i].y() + (float) tile / 2) < tile) {
                    collision = true;
                }
            }
            for (Bomb[] row : bombs) {
                for (Bomb bomb : row) {
                    if (bomb.showed && !bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) >= tile) {
                        bomb.bombActive = true;
                    }
                    if (bomb.showed && bomb.bombActive && dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) < tile) {
                        collision = true;
                    }
                }
            }
            if (!collision) {
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
            up = false;
            down = false;
            left = false;
            right = false;
            move = false;
            // Hurt Check
            boolean hurt = false;
            for (Flame[] row : flames) {
                for (Flame flame : row) {
                    if (flame.showed && dist(x, y, flame.x() + (float) tile / 2, flame.y() + (float) tile / 2) < tile) {
                        hurt = true;
                    }
                }
            }
            for (int i = 0; i < 200; i++) {
                if (i < totEnemies && dist(x, y, enemies[i].x() + (float) tile / 2, enemies[i].y() + (float) tile / 2) < tile) {
                    hurt = true;
                }
            }
            if (hurt) {
                players[0].health -= 1;
                hurt = false;
            }
        }
        if (bomb) {
            /*if (totBombs<players[0].getMaxBombs()) {
                int x = players[0].x(), y = players[0].y();
                int col = (x - 15)/tile, row = (y - 75)/tile;
                bombs[col][row].showed = true ;
                bombs[col][row].timer = 3.0;
                totBombs++;
            }
            bomb=false;*/
            if (totBombs < players[0].getMaxBombs()) {
                int playerCenterX = players[0].x() + tile / 2;
                int playerCenterY = players[0].y() + tile / 2;

                int col = (playerCenterX - 15) / tile;
                int row = (playerCenterY - 75) / tile;

                //int bombX = col * tile + 15;
                //int bombY = row * tile + 75;

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
    /*void creatFlame(int x, int y, int explosionDistance){
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

    }*/

    /*void creatFlame (int x, int y) {
        // Get the player's current explosion range
        int explosionDistance = players[0].getExplosionDistance();

        int col = (x - 15)/tile;
        int row = (y - 75)/tile; // Adjusted to match the game grid's offset

        flames[col][row].appear();
        for (int i = 1; i <= explosionDistance; i++) {
            // Modify the grids to the left
            if (col - i >= 0) {
                flames[col - i][row].appear();
            }
            // Modify the grids to the right
            if (col + i < cols) {
                flames[col + i][row].appear();
            }
            // Modify the grids upside
            if (row - i >= 0) {
                flames[col][row - i].appear();

            }
//            // Modify the grids downside
            if (row + i < rows) {
                flames[col][row + i].appear();
            }
        }
    }*/

    void creatFlame(int x, int y) {
        int explosionDistance = players[0].getExplosionDistance();

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

                if (isWallAt(newCol, newRow)) break;

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
        return (door.x == col*tile+15 && door.y == row*tile+75) ||
                (doorKey.x == col*tile+15 && doorKey.y == row*tile+75);
    }

    boolean checkAndHandleBreakable(int col, int row) {
        boolean handled = false;

        for (BreakableRock rock : rocks) {
            int rockCol = (rock.x() - 15) / tile;
            int rockRow = (rock.y() - 75) / tile;
            if (rockCol == col && rockRow == row && rock.rockExist) {
                // Mark the rock for removal instead of immediately removing it
                rock.markForRemoval();
                handled = true;
                // Reveal any hidden items under the rock
                if (rock.hideDoor) {
                    door.setVisible(true); // 設置門為可見
                    rock.hideDoor = false; // 更新岩石的狀態，表示門已經不再隱藏
                }
                if (rock.hideKey) {
                    doorKey.setVisible(true); // 設置鑰匙為可見
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
        for (BombPowerUp powerUp : powerUps) {
            int powerUpCol = (powerUp.x - 15) / tile;
            int powerUpRow = (powerUp.y - 75) / tile;
            if (powerUpCol == col && powerUpRow == row && powerUp.isVisible()) {
                powerUp.markForRemoval();
                itemDestroyed = true;
                break;
            }
        }

        for (SpeedUp speedUp : speedUps) {
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
            for (ExtraBomb extraBomb : extraBombs) {
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

    void removeMarkedObjects() {
        // 移除被標記的岩石
        Iterator<BreakableRock> rockIterator = rocks.iterator();
        while (rockIterator.hasNext()) {
            BreakableRock rock = rockIterator.next();
            if (rock.isMarkedForRemoval()) {
                rockIterator.remove();
            }
        }

        // 移除被標記的增強道具
        Iterator<BombPowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            BombPowerUp powerUp = powerUpIterator.next();
            if (powerUp.isMarkedForRemoval()) {
                powerUpIterator.remove();
            }
        }

        // 移除被標記的額外炸彈
        Iterator<ExtraBomb> extraBombIterator = extraBombs.iterator();
        while (extraBombIterator.hasNext()) {
            ExtraBomb extraBomb = extraBombIterator.next();
            if (extraBomb.isMarkedForRemoval()) {
                extraBombIterator.remove();
            }
        }

        Iterator<SpeedUp> speedUpIterator = speedUps.iterator();
        while (speedUpIterator.hasNext()) {
            SpeedUp speedUp = speedUpIterator.next();
            if (speedUp.isMarkedForRemoval()) {
                speedUpIterator.remove();
            }
        }

        // 如果有其他類型的物件也需要被移除，繼續在這裡添加相應的邏輯
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
        if (key =='w' && !move) {
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


    /*public void checkRockDestruction() {
        for(BreakableRock rock : rocks) {
            if (!rock.rockExist) {
                if (rock.hideKey) {
                    doorKey.setVisible(true);
                    rock.setHideKey(false);
                    System.out.println("A key has been revealed at position: x=" + doorKey.x + ", y=" + doorKey.y);
                }
                if (rock.hideDoor) {
                    door.setVisible(true);
                    rock.setHideDoor(false);
                    System.out.println("A door has been revealed at position: x=" + door.x + ", y=" + door.y);
                }
                if (rock.hidePowerUp) {
                    BombPowerUp powerUp = rock.getAssociatedPowerUp();
                    if (powerUp != null) {
                        powerUp.setVisible(true);
                    }
                    rock.setHidePowerUp(false);
                }
                if (rock.hideExtraBomb) {
                    ExtraBomb extraBomb = rock.getAssociatedExtraBomb();
                    if (extraBomb != null) {
                        extraBomb.setVisible(true);
                    }
                    rock.setHideExtraBomb(false);
                }
            }
        }
    }*/
    public static void main(String[] args){
        PApplet.main("org.example.GameLoop");
    }
}