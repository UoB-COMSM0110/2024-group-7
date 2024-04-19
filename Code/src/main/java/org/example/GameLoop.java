package org.example;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

public class GameLoop extends PApplet{
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    public static float shrinkNumber = 2.5f;
    public static boolean menu=true, PVE=false, PVP=false, settings=false,Achievements=false;
//    public static boolean move=false, up=false, down=false, left=false, right=false;
    public static boolean reset = false;
    public static int rows = 15, cols = 31;
    public static boolean gameWon = false;
    public static boolean gameLost = false;
    public static boolean timeStarted = false;
    public static boolean openShop = false;
    static char upKey1 = 'w';static char downKey1 = 's';static char leftKey1 = 'a';static char rightKey1 = 'd';static char bombKey1 = 'c';

    static char bombKey2 = 'x';

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);

        ResourceManager.loadAllImages(this);

        Character.players = Player.setPlayer1(this);
        Character.players = Player.setPlayer2(this);

        /*//generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);

        //generate shops
        Obstacle.shops = Shop.generateShops(rows, cols, this);
        //generate coins
        Coin.setCoinsInEmptySpaces(this);

        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);
        Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);

        Flame.initializeFlames(this);
        Flame.initializeUltimateFlames(this);

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

        Coin.setCoins(this);*/

    }

    public void setupPVE() {
        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);
        //generate shops
        Obstacle.shops = Shop.generateShops(rows, cols, this);
        //generate coins
        Coin.setCoinsInEmptySpaces(this);
        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);
        //Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);

        Flame.initializeFlames(this);

        Obstacle.initializeObstacleGrid();
        //Obstacle.initializeObstacleGridPVP();
        Character.enemies = Enemy.generateEnemies(this);

        //Items.doorKey = new DoorKey(0, 0, this);
        //Items.doorKey.setKey(this);

        Items.door = new Door(0, 0, this);
        Items.door.setDoor(this);


        BombPowerUp.setPowerUps(this);

        ExtraBomb.setExtraBombs(this);

        SpeedUp.setSpeedUps(this);

        ExtraLife.setExtraLives(this);

        Coin.setCoins(this);
    }

    public void setupPVP() {
        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);
        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);
        Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);

        Flame.initializeFlames(this);

        //Obstacle.initializeObstacleGrid();
        Obstacle.initializeObstacleGridPVP();
        //Character.enemies = Enemy.generateEnemies(this);

        //Items.doorKey = new DoorKey(0, 0, this);
        //Items.doorKey.setKey(this);

        //Items.door = new Door(0, 0, this);
        //Items.door.setDoor(this);

    }

    public void draw() {

        if (menu) {
            if(reset){
                Character.players.get(0).health = 3;
                Character.players.get(1).health = 3;

                Character.players.get(0).px = 45;
                Character.players.get(0).py = 105;

                Character.players.get(1).px = 885;
                Character.players.get(1).py = 465;

                Player.players.get(0).otherPlayerWon = false;
                Player.players.get(1).otherPlayerWon = false;
                Player.players.get(0).exist = true;
                Player.players.get(1).exist = true;

                gameLost = false;

                reset = false;
                shrinkNumber = 2.5f;
            }
            //System.out.println("1 is: " + Player.players.get(0).health + "  2 is :" + Player.players.get(1).health);
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

            if (!openShop && !gameWon && !gameLost) {
                translate((float) width / 2, (float) height / 2);
                scale(shrinkNumber);
                translate(-Player.players.get(0).px, -Player.players.get(0).py);
            }

            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);

            Wall.wallsRender();
            Shop.shopsRender();

            BreakableRock.rocksRender();

            //DoorKey.doorKeyRender(this);
            Door.doorRender(this);

            Enemy.enemiesRender();

            Player.player1Render();

            gameEndDetectPVE();

            BombPowerUp.getPowerUp(this);
            ExtraBomb.getExtraBomb(this);
            SpeedUp.getSpeedUp(this);
            ExtraLife.getExtraLife(this);
            Coin.getCoin(this);

            Bomb.bombRender();
            Flame.flameRender();

            Flame.ultimateFlameRender();

            BreakableRock.removeRocks();
            Items.removeMarkedObjects();

            Player.player1Movement();
            Enemy.enemiesMove();

            Player.absorb1ToIntersection();

            Bomb.setBombIfPossible1(this);
<<<<<<< HEAD
            UltimateAbilities.generateUltimateFire(Player.players.get(0),this);
=======

            float playerX = Player.players.get(0).px;
            float playerY = Player.players.get(0).py;
            int player1Health = Character.players.get(0).getHealth();
            int coinNumber = Character.players.get(0).getCoin();

            textSize(15);
            fill(0);
            text("Life:", playerX - 250 , playerY - 100 , 240, 540);
            text(String.valueOf(player1Health), playerX - 220, playerY - 100, 240, 540);
            text("Coin:", playerX - 170  , playerY - 100 , 240, 540);
            text(String.valueOf(coinNumber),playerX - 140,playerY - 100, 240, 540);
            text("Skill:", playerX - 90  , playerY - 100 , 240, 540);
            text("Item", playerX - 40  , playerY - 100 , 240, 540);

        }
        //PVE wining window
        if((gameWon || gameLost) && PVE) {
            //System.out.println("1 is: " + Player.players.get(0).otherPlayerWon + "  2 is :" + Player.players.get(1).otherPlayerWon);
            PVEui.PVEUIShow();

            if (PVEui.PVEuivisible) {
                fill(165, 165, 165, 200); // 灰色半透明背景
                rectMode(PConstants.CORNER);
                rect(0, 0, 960, 540);

                fill(87, 108, 164); // 蓝色矩形
                float rectWidth = 480;  //(float) width / 2
                float rectHeight = 270; //(float) height / 2
                float rectX = (960 - rectWidth) / 2;
                float rectY = (540 - rectHeight) / 2;
                rect(rectX, rectY, rectWidth, rectHeight);

                fill(250, 236, 0); // yellow word
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(35);
                if (gameWon) {
                    text("YOUR WON!", 480, 270);
                } else {
                    text("YOUR LOST!", 480, 270);
                }
                fill(0, 0, 222); // blue word
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(30);
                text("←Restart", 240, 240, 480, 270);
            }
        }
        //PVE shop
        if(openShop && PVE) {
            background(87, 108, 164);
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            textFont(Daruma, 35);
            fill(250, 236, 0);
            text("Shop", 350, 30, (float) width / 4, height);

            int coinNumber = Character.players.get(0).getCoin();
            textFont(Daruma, 30);
            fill(0);
            text("Coin", 600, 30, (float) width / 4, height);
            text(String.valueOf(coinNumber), 650, 30, (float) width / 4, height);

            textSize(30);
            fill(0); // Set color for dropdown text
            text("Item1", 250, 100, (float) width / 4, height);
            text("Item2", 250, 180, (float) width / 4, height);
            text("Item3", 250, 260, (float) width / 4, height);
            text("Item4", 250, 340, (float) width / 4, height);
            text("Item5", 250, 420, (float) width / 4, height);

            textSize(30);
            fill(0,0,222); // Set color for dropdown text
            text("Buy", 450, 100, (float) width / 4, height);
            text("Buy", 450, 180, (float) width / 4, height);
            text("Buy", 450, 260, (float) width / 4, height);
            text("Buy", 450, 340, (float) width / 4, height);
            text("Buy", 450, 420, (float) width / 4, height);

            text("←Close", 220,480,(float) width / 2 , (float) height / 2);
>>>>>>> main
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

            int player1Health = Character.players.get(0).getHealth();
            int player2Health = Character.players.get(1).getHealth();
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
        //PVP wining window
        if((Player.players.get(0).otherPlayerWon || Player.players.get(1).otherPlayerWon) && PVP) {
            //System.out.println("1 is: " + Player.players.get(0).otherPlayerWon + "  2 is :" + Player.players.get(1).otherPlayerWon);
            PVPui.PVPuishow();
            if(PVPui.PVPuivisible){
                fill(165, 165, 165, 200); // 灰色半透明背景
                rectMode(PConstants.CORNER);
                rect(0, 0, width, height);

                fill(87, 108, 164); // 蓝色矩形
                float rectWidth = (float) width / 2;
                float rectHeight = (float) height / 2;
                float rectX = (width - rectWidth) / 2;
                float rectY = (height - rectHeight) / 2;
                rect(rectX, rectY, rectWidth, rectHeight);

                fill(250, 236, 0); // yellow word
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(35);
                if(Player.players.get(0).otherPlayerWon){
                    text("P2 WON!", (float) width / 2, (float) height / 2);
                }else{
                    text("P1 WON!", (float) width / 2, (float) height / 2);
                }
                fill(0, 0, 222); // blue word
                textAlign(PConstants.CENTER, PConstants.CENTER);
                textSize(30);
                text("←Restart", 240,240,(float) width / 2 , (float) height / 2);
            }
        }


        if (settings) {
            menu = false;

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
        System.out.println(mouseX);
        System.out.println(mouseY);
        /*if (mouseX>=150 && mouseX<350 && menu) {
            link("https://github.com/UoB-COMSM0110/2024-group-7");
        }*/

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
                enterShop();  // 如果上方有商店，则进入商店
            } else {
                Player.players.get(0).up = true;
                Player.players.get(0).direction = 0;
            }
            //Player.players.get(0).up = true;
            //Player.players.get(0).direction = 0;
        } else if (key == downKey1) {
            if (Shop.isShopAt((Player.players.get(0).px-15) / tile, ((Player.players.get(0).py-75) / tile) + 1)) {
                enterShop();  // 如果下方有商店，则进入商店
            } else {
                Player.players.get(0).down = true;
                Player.players.get(0).direction = 2;
            }
            //Player.players.get(0).down = true;
            //Player.players.get(0).direction = 2;
        } else if (key == leftKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) - 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();  // 如果左方有商店，则进入商店
            } else {
                Player.players.get(0).left = true;
                Player.players.get(0).direction = 3;
            }
            //Player.players.get(0).left = true;
            //Player.players.get(0).direction = 3;
        } else if (key == rightKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) + 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();  // 如果右方有商店，则进入商店
            } else {
                Player.players.get(0).right = true;
                Player.players.get(0).direction = 1;
            }
            //Player.players.get(0).right = true;
            //Player.players.get(0).direction = 1;
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
                    enterShop();  // 如果上方有商店，则进入商店
                } else {
                    Player.players.get(1).up = true;
                    Player.players.get(1).direction = 0;
                }
                //Player.players.get(1).up = true;
                //Player.players.get(1).direction = 0;
            } else if (keyCode == DOWN) {
                if (Shop.isShopAt((Player.players.get(1).px-15) / tile, ((Player.players.get(1).py-75) / tile) + 1)) {
                    enterShop();  // 如果下方有商店，则进入商店
                } else {
                    Player.players.get(1).down = true;
                    Player.players.get(1).direction = 2;
                }
                //Player.players.get(1).down = true;
                //Player.players.get(1).direction = 2;
            } else if (keyCode == LEFT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) - 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();  // 如果左方有商店，则进入商店
                } else {
                    Player.players.get(1).left = true;
                    Player.players.get(1).direction = 3;
                }
                //Player.players.get(1).left = true;
                //Player.players.get(1).direction = 3;
            } else if (keyCode == RIGHT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) + 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();  // 如果右方有商店，则进入商店
                } else {
                    Player.players.get(1).right = true;
                    Player.players.get(1).direction = 1;
                }
                //Player.players.get(1).right = true;
                //Player.players.get(1).direction = 1;
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
        // 顯示商店界面
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
                //println("You've won the game!");//Game finished menu need to be complete
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
