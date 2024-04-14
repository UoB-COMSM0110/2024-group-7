package org.example;

import processing.core.PApplet;
import processing.core.PFont;

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    public static boolean menu=true, PVE=false, PVP=false;
//    public static boolean move=false, up=false, down=false, left=false, right=false;
    public static int rows = 15, cols = 31;
    public static boolean gameWon = false;
    public static boolean gameLost = false;

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
    }

    public void keyPressed() {
        if (key == 'w') {
            Player.players.get(0).up = true;
            Player.players.get(0).direction = 0;
        } else if (key == 's') {
            Player.players.get(0).down = true;
            Player.players.get(0).direction = 2;
        } else if (key == 'a') {
            Player.players.get(0).left = true;
            Player.players.get(0).direction = 3;
        } else if (key == 'd') {
            Player.players.get(0).right = true;
            Player.players.get(0).direction = 1;
        } else if (key == 'c' && Player.players.get(0).getMaxBombs() >= Bomb.findCurrentBombsNumber1()) {
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
        switch (key) {
            case 'w': Player.players.get(0).up = false; break;
            case 's': Player.players.get(0).down = false; break;
            case 'a': Player.players.get(0).left = false; break;
            case 'd': Player.players.get(0).right = false; break;
        }
        Player.players.get(0).move = Player.players.get(0).up || Player.players.get(0).down
                || Player.players.get(0).left || Player.players.get(0).right;

        if(PVP){
            switch (keyCode) {
                case UP: Player.players.get(1).up = false; break;
                case DOWN: Player.players.get(1).down = false; break;
                case LEFT: Player.players.get(1).left = false; break;
                case RIGHT: Player.players.get(1).right = false; break;
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