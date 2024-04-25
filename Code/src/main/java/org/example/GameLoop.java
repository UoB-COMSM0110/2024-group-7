package org.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class GameLoop extends PApplet{
    public static final int fps=120, tile=30, width=960, height=540, rows=12, cols=14, margin=15, hud=75;
    public static float shrinkNumber = 2.5f;
    public static double pvpStartTime;
    public static boolean menu=true, PVE=false, PVP=false, settings=false,Achievements=false, reset = false, gameWon = false, gameLost = false, timeStarted = false, openShop = false;
    boolean activeStack=false;
    boolean collision=false;
    boolean play=false;
    boolean move=false;
    boolean up=false;
    boolean down=false;
    boolean left=false;
    boolean right=false;
    boolean bomb=false;
    boolean placed=false;
    static boolean topLeft=true;
    boolean firstCollision=true;
    static char upKey1 = 'w', downKey1 = 's', leftKey1 = 'a', rightKey1 = 'd', bombKey1 = 'c', bombKey2 = 'x';
    public int stackIndex=0;
    public int stackCounter=0;
    public int speed=5;
    public int maxBombs=3;
    public int totBombs=0;
    public int totEnemies=1;
    public static int offsetX=(49*tile);
    public static int offsetY=(33*tile);
    public static int topLeftR=0;
    public static int topLeftC=0;
    public int cntCollision=0;
    public int CornerR=0;
    public int CornerC=0;
    public int corner=1;
    String currentMove="", lastMove="";
    Tile [][] tiles = new Tile[rows*9][cols*9];
    String [] stack = new String[20];

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);
        Sprites.load(this);
        Characters.createPlayers(this);
    }

    public void setupPVE() {
        Map.generator();
        tiles = Map.tiles;
    }

    public void setupPVP() {
        Obstacle.walls = Wall.generateWalls(rows, cols, this);
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);
        Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);
        Flame.initializeFlames(this);
        Obstacle.initializeObstacleGridPVP();
    }

    public void draw() {
        if (menu) {
            if(reset){
                Settings.reset();
            }
            Menu.render(this);
        }
        if (PVE) {
            Map.render(this);
            gameEndDetectPVE();
            Characters.players.get(0).move();
            Collision.test();
            Bomb.bombRender();
            Flame.flameRender();
            //Flame.ultimateFlameRender();
            //Items.checkAndHandleBreakableUltimate();


            Player.player1Movement();
            Enemy.enemiesMove();

            Player.absorb1ToIntersection();

            Bomb.setBombIfPossible1(this);

            UltimateAbilities.generateUltimateFire(Player.players.get(0));

            Player.players.get(0).ifEnterTheNextWorld();

            float playerX = Player.players.get(0).px;
            float playerY = Player.players.get(0).py;
            int player1Health = Characters.players.get(0).getHealth();
            int coinNumber = Characters.players.get(0).getCoin();

            textSize(15);
            fill(0);
            text("Life:", playerX - 250 , playerY - 100 , 240, 540);
            text(String.valueOf(player1Health), playerX - 220, playerY - 100, 240, 540);
            text("Coin:", playerX - 170  , playerY - 100 , 240, 540);
            text(String.valueOf(coinNumber),playerX - 140,playerY - 100, 240, 540);
            text("Skill:", playerX - 90  , playerY - 100 , 240, 540);
            text("Item", playerX - 40  , playerY - 100 , 240, 540);

        }
        if((gameWon || gameLost) && PVE) {
            PVEui.PVEUIShow();

            if (PVEui.PVEuivisible) {
                fill(165, 165, 165, 200);
                rectMode(PConstants.CORNER);
                rect(0, 0, 960, 540);

                fill(87, 108, 164);
                float rectWidth = 480;
                float rectHeight = 270;
                float rectX = (960 - rectWidth) / 2;
                float rectY = (540 - rectHeight) / 2;
                rect(rectX, rectY, rectWidth, rectHeight);

                fill(250, 236, 0);
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(35);
                if (gameWon) {
                    text("YOUR WON!", 480, 270);
                } else {
                    text("YOUR LOST!", 480, 270);
                }
                fill(0, 0, 222);
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(30);
                text("←Restart", 240, 240, 480, 270);
            }
        }
        if(openShop && PVE) {
            background(87, 108, 164);
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            textFont(Daruma, 35);
            fill(250, 236, 0);
            text("Shop", 350, 30, (float) width / 4, height);

            int coinNumber = Characters.players.get(0).getCoin();
            textFont(Daruma, 30);
            fill(0);
            text("Coin", 600, 30, (float) width / 4, height);
            text(String.valueOf(coinNumber), 650, 30, (float) width / 4, height);

            textSize(30);
            fill(0);
            text("VerticalFlames", 250, 100, (float) width / 4, height);
            text("RoundFlames", 250, 180, (float) width / 4, height);
            text("MoveToTheDoor", 250, 260, (float) width / 4, height);
            text("KillAllEnemies", 250, 340, (float) width / 4, height);
            text("RemoveAllRocks", 250, 420, (float) width / 4, height);

            textSize(30);
            fill(0,0,222);
            text("Buy", 450, 100, (float) width / 4, height);
            text("Buy", 450, 180, (float) width / 4, height);
            text("Buy", 450, 260, (float) width / 4, height);
            text("Buy", 450, 340, (float) width / 4, height);
            text("Buy", 450, 420, (float) width / 4, height);

            text("←Close", 220,480,(float) width / 2 , (float) height / 2);
        }

        if(PVP){
            menu = false;
            background(87, 108, 164);
            PVPui pvpui = new PVPui(this);
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);

            int player1Health = Characters.players.get(0).getHealth();
            int player2Health = Characters.players.get(1).getHealth();
            textSize(30);
            fill(0);
            text("P1 Life:", 25, 25, (float) width / 4, height);
            text(String.valueOf(player1Health), 105, 25, (float) width / 4, height);
            text("Time:", 330, 25, (float) width / 4, height);
            if (PVP && !timeStarted) {
                timeStarted = true;
                new Thread(() -> pvpui.clock()).start();
            }
            text(String.valueOf(PVPui.seconds),400,25, 240, 540);
            text("P2 Life:", 650, 25, (float) width / 4, height);
            text(String.valueOf(player2Health), 730, 25, (float) width / 4, height);

            Wall.wallsRender();
            BreakableRock.lessRocksRender();

            pvpStartTime = millis();

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

            UltimateAbilities.generatePVPFlames();

            gameEndDetectPVP();
        }
        if((Player.players.get(0).otherPlayerWon || Player.players.get(1).otherPlayerWon) && PVP) {
            PVPui.PVPuishow();
            if(PVPui.PVPuivisible){
                fill(165, 165, 165, 200);
                rectMode(PConstants.CORNER);
                rect(0, 0, width, height);

                fill(87, 108, 164);
                float rectWidth = (float) width / 2;
                float rectHeight = (float) height / 2;
                float rectX = (width - rectWidth) / 2;
                float rectY = (height - rectHeight) / 2;
                rect(rectX, rectY, rectWidth, rectHeight);

                fill(250, 236, 0);
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(35);
                if(Player.players.get(0).otherPlayerWon){
                    text("P2 WON!", (float) width / 2, (float) height / 2);
                }else{
                    text("P1 WON!", (float) width / 2, (float) height / 2);
                }
                fill(0, 0, 222);
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(30);
                text("←Restart", 240,240,(float) width / 2 , (float) height / 2);
            }
        }

        if (settings) {
            Settings.render(this);
        }
    }
    public void mouseClicked() {
        System.out.println(mouseX);
        System.out.println(mouseY);
        if (mouseX>=0 && mouseX<180 && mouseY>=450 && mouseY<540 && menu) {
            PVE=true;
            menu=false;
            setupPVE();
        }
        if (PVE) {
            if (mouseX>=420 && mouseX<=530 && mouseY>=350 && mouseY<390){
                PVEui.PVEUIHide();
                PVE = false;
                menu=true;
                reset = true;
            }
            if (openShop) {
                int x = mouseX;
                int y = mouseY;
                PVEui pveui = new PVEui(this);
                String op = pveui.pveShopMouseClicked(x,y);
                pveui.dealOperation(op);
            }
        }

        if (mouseX>=300 && mouseX<420 && mouseY>=450 && mouseY<540 && menu) {
            PVP=true;
            menu=false;
            setupPVP();
        }
        if (PVP) {
            if (mouseX>=420 && mouseX<=530 && mouseY>=350 && mouseY<390){
                PVPui.PVPUIHide();
                PVP=false;
                menu=true;
                reset = true;
            }
        }

        if (mouseX>=480 && mouseX<600 && mouseY>=450 && mouseY<540 && menu) {
            Achievements=true;
            menu=false;
        }


        if (mouseX>=760 && mouseX<width && mouseY>=450 && mouseY<540 && menu) {
            settings=true;
            menu=false;
        }
        if(settings){
            int x = mouseX;
            int y = mouseY;
            Settings settingsMenu = new Settings(this);
            String op = settingsMenu.settingsMouseClicked(x,y);
            char keyInput = key;
            settingsMenu.dealOperation(op,keyInput);
        }
    }

    public void keyPressed() {
        if (key == upKey1) {
            if (Shop.isShopAt((Player.players.get(0).px-15) / tile, ((Player.players.get(0).py-75) / tile) - 1)) {
                enterShop();
            } else {
                Player.players.get(0).up = true;
                Player.players.get(0).direction = 0;
            }
        } else if (key == downKey1) {
            if (Shop.isShopAt((Player.players.get(0).px-15) / tile, ((Player.players.get(0).py-75) / tile) + 1)) {
                enterShop();
            } else {
                Player.players.get(0).down = true;
                Player.players.get(0).direction = 2;
            }
        } else if (key == leftKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) - 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();
            } else {
                Player.players.get(0).left = true;
                Player.players.get(0).direction = 3;
            }
        } else if (key == rightKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) + 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();
            } else {
                Player.players.get(0).right = true;
                Player.players.get(0).direction = 1;
            }
        } else if (key == bombKey1 && Player.players.get(0).getMaxBombs() >= Bomb.findCurrentBombsNumber1()) {
            Player.players.get(0).bomb = true;
        } else if (key == bombKey2) {
            Player.players.get(0).useAbility = true;
        }
        Player.players.get(0).move = Player.players.get(0).up || Player.players.get(0).down
                || Player.players.get(0).left || Player.players.get(0).right;

        if(PVP) {
            if (keyCode == UP) {
                if (Shop.isShopAt((Player.players.get(1).px-15) / tile, ((Player.players.get(1).py-75) / tile) - 1)) {
                    enterShop();
                } else {
                    Player.players.get(1).up = true;
                    Player.players.get(1).direction = 0;
                }
            } else if (keyCode == DOWN) {
                if (Shop.isShopAt((Player.players.get(1).px-15) / tile, ((Player.players.get(1).py-75) / tile) + 1)) {
                    enterShop();
                } else {
                    Player.players.get(1).down = true;
                    Player.players.get(1).direction = 2;
                }
            } else if (keyCode == LEFT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) - 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();
                } else {
                    Player.players.get(1).left = true;
                    Player.players.get(1).direction = 3;
                }
            } else if (keyCode == RIGHT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) + 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();
                } else {
                    Player.players.get(1).right = true;
                    Player.players.get(1).direction = 1;
                }
            } else if (keyCode == ENTER && Player.players.get(0).getMaxBombs() >= Bomb.findCurrentBombsNumber2()) {
                Player.players.get(1).bomb = true;
            }
            Player.players.get(1).move = Player.players.get(1).up || Player.players.get(1).down
                    || Player.players.get(1).left || Player.players.get(1).right;
        }
    }

    public void enterShop() {
        System.out.println("Entering shop...");
        GameLoop.openShop=true;
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
        }else if (key == bombKey2) {
            Player.players.get(0).useAbility = false;
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
            Player player = Characters.players.get(0);
            float distance = dist(player.px, player.py, Items.doorKey.x, Items.doorKey.y);
            if (distance < 30) {
                Items.doorKey.setCollected();
            }
        }

        if (Items.doorKey != null && Items.doorKey.collected && !gameWon) {
            Player player = Characters.players.get(0);
            float distanceToDoor = dist(player.px, player.py, Items.door.x, Items.door.y);
            if (distanceToDoor < 30) {
                gameWon = true;
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
