package org.example;

import processing.core.PApplet;
import processing.core.PFont;

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    public static boolean menu=true, PVE=false, PVP=false, settings=false;
//    public static boolean move=false, up=false, down=false, left=false, right=false;
    public static int rows = 15, cols = 31;
    public static boolean gameWon = false;
    public static boolean gameLost = false;
    static char upKey1 = 'w';static char downKey1 = 's';static char leftKey1 = 'a';static char rightKey1 = 'd';static char bombKey1 = 'c';


    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);

        ResourceManager.loadAllImages(this);

        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);
        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);
        Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);

        Flame.initializeFlames(this);

        Obstacle.initializeObstacleGrid();
        Obstacle.initializeObstacleGridPVP();

        Character.enemies = Enemy.generateEnemies(this);

        Character.players = Player.setPlayer1(this);
        Character.players = Player.setPlayer2(this);

        Items.doorKey = new DoorKey(0,0,this);
        Items.doorKey.setKey(this);

        Items.door = new Door(0,0,this);
        Items.door.setDoor(this);

        BombPowerUp.setPowerUps(this);

        ExtraBomb.setExtraBombs(this);

        SpeedUp.setSpeedUps(this);

        ExtraLife.setExtraLives(this);

    }
    public void draw() {
        //System.out.println("x: "+ Character.players.get(0).x() + ",    y: " + Character.players.get(0).y());

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
            text("PVE", 0, 450, ((float) width / 4), 100);
            text("PVP", ((float) width / 4), 450, ((float) width / 4), 100);
            text("Achievements", ((float) width / 4 * 2), 450, ((float) width / 4), 100);
            text("Settings", ((float) width / 4 * 3), 450, ((float) width / 4), 100);
            textSize(100);
            text("Dungeon", 0, 250, width, 100);
        }

        if (PVE) {
            menu = false;
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);

            Wall.wallsRender();

            BreakableRock.rocksRender();

            DoorKey.doorKeyRender(this);
            Door.doorRender(this);

            Enemy.enemiesRender();

            Player.player1Render();

            gameEndDetectPVE();

            BombPowerUp.getPowerUp(this);
            ExtraBomb.getExtraBomb(this);
            SpeedUp.getSpeedUp(this);
            ExtraLife.getExtraLife(this);

            Bomb.bombRender();
            Flame.flameRender();

            BreakableRock.removeRocks();
            Items.removeMarkedObjects();

            Player.player1Movement();
            Enemy.enemiesMove();

            Player.absorb1ToIntersection();

            Bomb.setBombIfPossible1(this);
        }

        if(PVP){
            menu = false;
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);

            Wall.wallsRender();
            BreakableRock.lessRocksRender();

            Player.player1Render();
            Player.player2Render();

            Player.players.get(0).PVPEnhancement();
            Player.players.get(1).PVPEnhancement();

            Bomb.bombRender();
            Flame.flameRender();

            BreakableRock.removeLessRocks();

            Player.player1Movement();
            Player.player2Movement();

            Player.absorb1ToIntersection();
            Player.absorb2ToIntersection();

            Bomb.setBombIfPossible1(this);
            Bomb.setBombIfPossible2(this);

            gameEndDetectPVP();
        }

        if (settings) {
            menu = false;
            PVE = false;
            PVP = false;

            background(87, 108, 164);
            Settings settingsMenu = new Settings(this);

            settingsMenu.show();
            // Background black color for the settings menu
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            textFont(Daruma, 35);
            fill(250, 236, 0);
            text("Settings", 150, 30, (float) width / 4, height);
            text("P1", 350, 30, (float) width / 4, height);

            textSize(30);
            fill(0); // Set color for dropdown text
            text("UP KEY:", 150, 100, (float) width / 4, height); // Draw label for dropdown
            text("DOWN KEY:", 150, 180, (float) width / 4, height);
            text("LEFT KEY:", 150, 260, (float) width / 4, height);
            text("RIGHT KEY:", 150, 340, (float) width / 4, height);
            text("BOMB KEY:", 150, 420, (float) width / 4, height);

            textSize(35);
            fill(0);
            text(String.valueOf(upKey1), 350, 100, (float) width / 4, height);
            text(String.valueOf(downKey1), 350, 180, (float) width / 4, height);
            text(String.valueOf(leftKey1), 350, 260, (float) width / 4, height);
            text(String.valueOf(rightKey1), 350, 340, (float) width / 4, height);
            text(String.valueOf(bombKey1), 350, 420, (float) width / 4, height);

            textSize(30);
            textAlign(CENTER);
            fill(0, 0, 222);
            text("←BACK", 0, 490, width, 500);
        }
    }

    public void mouseClicked() {
        if (mouseX>=150 && mouseX<350) {
            link("https://github.com/UoB-COMSM0110/2024-group-7");
        }
        if (mouseX>=0 && mouseX<(width/4) && mouseY>=450 && mouseY<540) {
            PVE=true;
        }
        if (mouseX>=(width/4) && mouseX<(width/2) && mouseY>=450 && mouseY<540) {
            PVP=true;
        }
        if (mouseX>=(3*width/4) && mouseX<width && mouseY>=450 && mouseY<540) {
            settings=true;
        }
        int x = mouseX;
        int y = mouseY;
        Settings settingsMenu = new Settings(this);
        String op = settingsMenu.settingsMouseClicked(x,y);
        char keyInput = key;
        settingsMenu.dealOperation(op,keyInput);
    }

    public void keyPressed() {
        if (key == upKey1) {
            Player.players.get(0).up = true;
            Player.players.get(0).direction = 0;
        } else if (key == downKey1) {
            Player.players.get(0).down = true;
            Player.players.get(0).direction = 2;
        } else if (key == leftKey1) {
            Player.players.get(0).left = true;
            Player.players.get(0).direction = 3;
        } else if (key == rightKey1) {
            Player.players.get(0).right = true;
            Player.players.get(0).direction = 1;
        } else if (key == bombKey1 && Player.players.get(0).getMaxBombs() >= Bomb.findCurrentBombsNumber1()) {
            Player.players.get(0).bomb = true;
        }
        Player.players.get(0).move = Player.players.get(0).up || Player.players.get(0).down
                || Player.players.get(0).left || Player.players.get(0).right;

        if(PVP) {
            if (keyCode == UP) {
                Player.players.get(1).up = true;
                Player.players.get(1).direction = 0;
            } else if (keyCode == DOWN) {
                Player.players.get(1).down = true;
                Player.players.get(1).direction = 2;
            } else if (keyCode == LEFT) {
                Player.players.get(1).left = true;
                Player.players.get(1).direction = 3;
            } else if (keyCode == RIGHT) {
                Player.players.get(1).right = true;
                Player.players.get(1).direction = 1;
            } else if (keyCode == ENTER && Player.players.get(0).getMaxBombs() >= Bomb.findCurrentBombsNumber2()) {
                Player.players.get(1).bomb = true;
            }
            Player.players.get(1).move = Player.players.get(1).up || Player.players.get(1).down
                    || Player.players.get(1).left || Player.players.get(1).right;
        }
    }

    public void keyReleased() {
        if (key==upKey1){
            Player.players.get(0).up = false;
        }else if (key==downKey1){
            Player.players.get(0).down = false;
        }else if (key==leftKey1){
            Player.players.get(0).left = false;
        }else if (key==rightKey1){
            Player.players.get(0).right = false;
        }
        Player.players.get(0).move = Player.players.get(0).up || Player.players.get(0).down
                || Player.players.get(0).left || Player.players.get(0).right;

        if(PVP){
            if (keyCode==UP){
                Player.players.get(1).up = false;
            }else if (keyCode==DOWN){
                Player.players.get(1).down = false;
            }else if (keyCode==LEFT){
                Player.players.get(1).left = false;
            }else if (keyCode==RIGHT){
                Player.players.get(1).right = false;
            }
            Player.players.get(1).move = Player.players.get(1).up || Player.players.get(1).down
                    || Player.players.get(1).left || Player.players.get(1).right;
        }
    }

    private static void gameEndDetectPVE(){
        if(gameLost){
            System.out.println("GameOver");
        }
        if (Items.doorKey != null && !Items.doorKey.collected && Items.doorKey.visible) {
            Player player = Character.players.get(0);
            float distance = dist(player.px, player.py, Items.doorKey.x, Items.doorKey.y);
            if (distance < 30) {
                Items.doorKey.setCollected();
            }
        }

        if (Items.doorKey != null && Items.doorKey.collected && !gameWon) {
            Player player = Character.players.get(0);
            float distanceToDoor = dist(player.px, player.py, Items.door.x, Items.door.y);
            if (distanceToDoor < 30) {
                gameWon = true;
                println("You've won the game!");//Game finished menu need to be complete
            }
        }
    }

    private static void gameEndDetectPVP() {
        if(Player.players.get(0).otherPlayerWon){
            System.out.println("Player 2 won!");
        }else if(Player.players.get(1).otherPlayerWon){
            System.out.println("Player 1 won!");
        }
    }
}