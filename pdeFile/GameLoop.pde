
    import ddf.minim.*;
    import processing.sound.*;
    public static Minim minim;
    public static AudioPlayer menuBGM;
    public static AudioPlayer battleSmoothBGM;
    public static AudioPlayer battleFierceBGM;
    
    PGraphics mask;
    public static final int tile=30;
    public static final int fps=60;
    public static final int width=960;
    public static final int height=540;
    public static float shrinkNumber = 2.5f;
    public static boolean menu=true, PVE=false, PVP=false, settings=false,Achievements=false;
    public static boolean difficulty = false;
    public static boolean ESCpressed = false;
    public static int currentDeepness = 0;
    public static int biggestDeepness = 0;
    int m =0;
//    public static boolean move=false, up=false, down=false, left=false, right=false;
    public static boolean reset = false;
    public static int rows = 15, cols = 31;
    public static boolean gameWon = false;
    public static boolean gameLost = false;
    public static boolean timeStarted = false;
    public static boolean openShop = false;
    public static boolean overRectButton1,overRectButton2,overRectButton3 = false;
    static char upKey1 = 'w';static char downKey1 = 's';static char leftKey1 = 'a';static char rightKey1 = 'd';static char bombKey1 = 'j';

    static char bombKey2 = 'k';

    public static double pvpStartTime;
    
    public static SoundFile setBomb;
    public static SoundFile bomb;
    public static SoundFile pick;
    public static SoundFile blow;
    public static SoundFile twinkleSparkle;
    public static SoundFile coinDrop;
    public static SoundFile click;

    public void settings() {
        size(width, height);
    }
    public void setup(){
        frameRate(fps);

        ResourceManager.loadAllImages(this);
        minim = new Minim(this);
        menuBGM = minim.loadFile("sound/mainmenu.mp3");
        battleSmoothBGM = minim.loadFile("sound/battleSmooth.mp3");
        battleFierceBGM = minim.loadFile("sound/battleFierce.mp3");

        Character.players = Player.setPlayer1(this);
        Character.players = Player.setPlayer2(this);

       
        bomb= new SoundFile(this, "sound/bombExplode.mp3");
        setBomb= new SoundFile(this, "sound/bubbleSound_1.mp3");
        pick =  new SoundFile(this,"sound/pick.mp3");
        blow = new SoundFile(this,"sound/blow.mp3");
        twinkleSparkle = new SoundFile(this,"sound/twinkleSparkle.mp3");
        coinDrop = new SoundFile(this,"sound/coin-drop-38750.mp3");
        click = new SoundFile(this,"sound/click.mp3");
    }

    public void setupPVE() {
        Obstacle.shops.clear();
        Obstacle.rocks.clear();
        Character.enemies.clear();
        Coin.coinsInEmptySpace.clear();
        Items.chosenIndexes.clear();
        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);

        //generate shops
        Obstacle.shops = Shop.generateShops(rows, cols, this);

        //generate coins
        Coin.setCoinsInEmptySpaces(this);

        //generate rocks
        Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);

        Flame.initializeFlames(this);
        Flame.initializeUltimateFlames(this);
        Flame.initializeEnemyNoHarmFlames(this);

        Obstacle.initializeObstacleGrid();

        Character.enemies = Enemy.generateEnemies(this);

        Items.door = new Door(0, 0, this);
        Items.door.setDoor(this);


        BombPowerUp.powerUps.clear();
        BombPowerUp.setPowerUps(this);

        ExtraBomb.extraBombs.clear();
        ExtraBomb.setExtraBombs(this);

        SpeedUp.speedUps.clear();
        SpeedUp.setSpeedUps(this);

        ExtraLife.extraLives.clear();
        ExtraLife.setExtraLives(this);

        Coin.coins.clear();
        Coin.setCoins(this);
    }

    public void setupPVP() {
        Obstacle.shops.clear();
        Obstacle.rocks.clear();
        Character.enemies.clear();
        Coin.coinsInEmptySpace.clear();
        Items.chosenIndexes.clear();
        //generate walls
        Obstacle.walls = Wall.generateWalls(rows, cols, this);
        //generate rocks
        Obstacle.lessRocks.clear();
        Obstacle.rocks.clear();

        /*Obstacle.rocks = BreakableRock.generateRocks(rows,cols, this, 0.5f);*/
        Obstacle.lessRocks = BreakableRock.generateLessRocks(rows,cols, this, 0.3f);
        Obstacle.rocks = Obstacle.lessRocks;

        Flame.initializeFlames(this);

        Obstacle.initializeObstacleGridPVP();

    }

    public void draw() {
        if (menu) {
          playBGM(menuBGM);
          battleFierceBGM.pause();
          battleSmoothBGM.pause();
            m = 0;
            if(reset){
                Character.players.get(0).isHavingTheKey = false;

                Character.players.get(0).health = 3;
                Character.players.get(1).health = 3;

                Character.players.get(0).explosionDistance = 1;
                Character.players.get(0).maxBombs = 1;
                Character.players.get(0).speed = 1;
                Character.players.get(0).coin = 0;

                Character.players.get(0).px = 45;
                Character.players.get(0).py = 105;

                Character.players.get(1).px = 885;
                Character.players.get(1).py = 465;

                Player.players.get(0).otherPlayerWon = false;
                Player.players.get(1).otherPlayerWon = false;
                Player.players.get(0).exist = true;
                Player.players.get(1).exist = true;

                gameLost = false;

                PVPui.seconds = 11;
                UltimateAbilities.k = 0;
                UltimateAbilities.pvpClock = false;
                Flame.activeFlames.clear();
                Flame.resetFlames();

                //Obstacle.rocks.clear();
                //Obstacle.rocks.addAll(Obstacle.theOriginalRocks);

                reset = false;
                shrinkNumber = 2.5f;
                if(currentDeepness >= biggestDeepness) {
                    biggestDeepness = currentDeepness;
                }
                currentDeepness = 0;
            }
            background(ResourceManager.mainMenu);
            updateButton();
            PFont Daruma = createFont("fonts/DarumadropOne-Regular.ttf", 60);
            stroke(250,250,250);
            if(overRectButton1){
                fill(233,233,233,200);
            }else{
                fill(211,211,211,200);
            }
            rect(330,160,300,40,30);
            if(overRectButton2){
                fill(233,233,233,200);
            }else{
                fill(211,211,211,200);
            }
            rect(330,230,300,40,30);
            if(overRectButton3){
                fill(233,233,233,200);
            }else{
                fill(211,211,211,200);
            }
            rect(330,300,300,40,30);
            textFont(Daruma, 30);
            fill(236,69,118);
//            text("Dungeon", 80, 70, width, 100);
            image(ResourceManager.logo,380,-25,200,200);
            textAlign(CENTER);
            text("Single Player-Easy", 330, 170, 300, 40);
            text("Single Player-Hard", 330, 240, 300, 40);
            text("Two Players Duel", 330, 310, 300, 40);
            text("Settings", ((float) width / 4 * 3)+50, 470, ((float) width / 4), 100);
            image(ResourceManager.gear,810,465,30,30);
            textSize(100);
            fill(236,69,118);
            textSize(30);
            text("The deepest level reached: " + biggestDeepness, 20, 20, 200, 60);
            textSize(60);
            text(String.valueOf(biggestDeepness),20,80,200,60);
        }


        if(ESCpressed && !menu){
            if(PVE){
                PVE = false;
                m = 1;
            }else if(PVP){
                PVP = false;
                m = 2;
            }
            fill(165, 165, 165, 200); // 灰色半透明背景
            //rectMode(PConstants.CORNER);
            rect(0, 0, 960, 540);

            fill(87, 108, 164); // 蓝色矩形
            float rectWidth = 480;  //(float) width / 2
            float rectHeight = 270; //(float) height / 2
            float rectX = (960 - rectWidth) / 2;
            float rectY = (540 - rectHeight) / 2;
            rect(rectX, rectY, rectWidth, rectHeight);

            fill(250, 236, 0); // yellow word
            //textAlign(PConstants.CENTER, PConstants.CENTER);
            textSize(35);
            text("Do you want to quit?", 480, 270);

            fill(0, 0, 222); // blue word
            //textAlign(PConstants.CENTER, PConstants.CENTER);
            textSize(30);
            text("confirm", 120, 360, 480, 270);
            text("cancel", 380, 360, 480, 270);
        }

        if (PVE) {
            menu = false;
            menuBGM.pause();
            playBGM(battleSmoothBGM);
            background(9, 84, 159);

            if (!openShop && !gameWon && !gameLost && difficulty) {

                translate((float) width / 2, (float) height / 2);
                scale(shrinkNumber);
                translate(-Player.players.get(0).px, -Player.players.get(0).py);
            }

            fill(228, 197, 158);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);

            Wall.wallsRender();
            Shop.shopsRender();

            BreakableRock.rocksRender();

            Door.doorRender(this);

            Enemy.enemiesRender();

            Player.player1Render();

            //gameEndDetectPVE();

            BombPowerUp.getPowerUp(this);
            ExtraBomb.getExtraBomb(this);
            SpeedUp.getSpeedUp(this);
            ExtraLife.getExtraLife(this);
            Coin.getCoin(this);

            Bomb.bombRender();
            Flame.flameRender();

            Flame.ultimateFlameRender();

            Items.checkAndHandleBreakableUltimate();

            BreakableRock.removeRocks();
            Items.removeMarkedObjects();

            Player.player1Movement();
            Enemy.enemiesMove();

            Player.absorb1ToIntersection();

            Bomb.setBombIfPossible1(this);

            UltimateAbilities.generateUltimateFire(Player.players.get(0), this);

            Player.players.get(0).ifEnterTheNextWorld();

            if(difficulty) {
                float playerX = Player.players.get(0).px;
                float playerY = Player.players.get(0).py;
                int player1Health = Character.players.get(0).getHealth();
                int coinNumber = Character.players.get(0).getCoin();

                textSize(15);
                fill(0);
                text("Life:", playerX - 290, playerY - 100, 240, 540);
                text(String.valueOf(player1Health), playerX - 260, playerY - 100, 240, 540);
                text("Coin:", playerX - 220, playerY - 100, 240, 540);
                text(String.valueOf(coinNumber), playerX - 190, playerY - 100, 240, 540);
                text("Skill:", playerX - 150, playerY - 100, 240, 540);
                text(PVEui.getAbilityName(), playerX - 70, playerY - 100, 240, 540);
                text("key:", playerX + 10, playerY - 100, 240, 540);
                if (Player.players.get(0).isHavingTheKey) {
                    text("Yes", playerX + 40, playerY - 100, 240, 540);
                } else {
                    text("No", playerX + 40, playerY - 100, 240, 540);
                }
            }else{
                int player1Health = Character.players.get(0).getHealth();
                int coinNumber = Character.players.get(0).getCoin();
                textSize(30);
                fill(0);
                text("Life:", -70, 25, (float) width / 4, height);
                text(String.valueOf(player1Health), 0, 25, (float) width / 4, height);
                text("Coin:", 120, 25, (float) width / 4, height);
                text(String.valueOf(coinNumber), 170, 25, (float) width / 4, height);
                text("Skill:", 290, 25, (float) width / 4, height);
                text(PVEui.getAbilityName(), 445, 25, (float) width / 4, height);
                text("key:", 650, 25, (float) width / 4, height);
                if (Player.players.get(0).isHavingTheKey) {
                    text("Yes", 720, 25, (float) width / 4, height);
                } else {
                    text("No", 720, 25, (float) width / 4, height);
                }
            }


        }
        //PVE wining window
        if((gameWon || gameLost) && PVE) {
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
            text("VerticalFlames", 250, 100, (float) width / 4, height);
            text("RoundFlames", 250, 165, (float) width / 4, height);
            text("HiddenDoorFlash", 250, 230, (float) width / 4, height);
            text("KillAllEnemies", 250, 295, (float) width / 4, height);
            text("RemoveAllRocks", 250, 360, (float) width / 4, height);
            text("Key", 250, 425, (float) width / 4, height);

            textSize(30);
            fill(0,0,222); // Set color for dropdown text
            text("1 coin", 450, 100, (float) width / 4, height);
            text("2 coins", 450, 165, (float) width / 4, height);
            text("3 coins", 450, 230, (float) width / 4, height);
            text("4 coins", 450, 295, (float) width / 4, height);
            text("5 coins", 450, 360, (float) width / 4, height);
            text("6 coins", 450, 425, (float) width / 4, height);

            text("←Close", 220,480,(float) width / 2 , (float) height / 2);
        }

        if(PVP){
            menu = false;
            menuBGM.pause();
            playBGM(battleFierceBGM);
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

            pvpStartTime = millis();

            Player.player1Render();
            Player.player2Render();

            Player.players.get(0).PVPEnhancement();
            Player.players.get(1).PVPEnhancement();

            /*Bomb.bombRender();*/
            Bomb.bombRenderPVP1();
            Bomb.bombRenderPVP2();
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
        //PVP wining window
        if((Player.players.get(0).otherPlayerWon || Player.players.get(1).otherPlayerWon) && PVP) {
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
            text("DOWN KEY:", 150, 165, (float) width / 4, height);
            text("LEFT KEY:", 150, 230, (float) width / 4, height);
            text("RIGHT KEY:", 150, 295, (float) width / 4, height);
            text("BOMB KEY:", 150, 360, (float) width / 4, height);
            text("SKILL KEY:", 150, 425, (float) width / 4, height);

            textSize(35);
            fill(0);
            text(String.valueOf(upKey1), 350, 100, (float) width / 4, height);
            text(String.valueOf(downKey1), 350, 165, (float) width / 4, height);
            text(String.valueOf(leftKey1), 350, 230, (float) width / 4, height);
            text(String.valueOf(rightKey1), 350, 295, (float) width / 4, height);
            text(String.valueOf(bombKey1), 350, 360, (float) width / 4, height);
            text(String.valueOf(bombKey2), 350, 425, (float) width / 4, height);

            textSize(30);
            textAlign(CENTER);
            fill(0, 0, 222);
            text("←BACK", 0, 490, width, 500);

            textSize(30);
            textAlign(CENTER);
            fill(250, 236, 0);
            text("Tips:", 250, 180, width, 500);
            fill(0);
            text("Press the key first and then click the key you want to modify.", 600, 230, 280, 500);
        }
    }

    public void mouseClicked() {
        click.play();
        System.out.println(mouseX);
        System.out.println(mouseY);
        if (mouseX>=330 && mouseX<630 && mouseY>=160 && mouseY<200 && menu) {
            PVE=true;
            menu=false;
            setupPVE();
            GameLoop.difficulty = false;
        }

        if (mouseX>=330 && mouseX<630 && mouseY>=230 && mouseY<270 && menu) {
            PVE=true;
            menu=false;
            setupPVE();
            GameLoop.difficulty = true;
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

        if (mouseX>=330 && mouseX<630 && mouseY>=300 && mouseY<340 && menu) {
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

        if (mouseX>=290 && mouseX<430 && mouseY>=350 && mouseY<395 && ESCpressed) {
            reset = true;
            menu=true;
            PVE = false;
            PVP = false;
            ESCpressed = false;
        }
        if (mouseX>=560 && mouseX<670 && mouseY>=350 && mouseY<395 && ESCpressed) {
            System.out.println(ESCpressed);
            if(m == 1){
                PVE = true;
            }else if(m ==2) {
                PVP = true;
            }
            ESCpressed = false;
        }
    }

    public void keyPressed() {
        if(key == '1'){
            if(!menu) {
                ESCpressed = !ESCpressed;
            }
            if(m == 1 && !ESCpressed){
                PVE =true;
            }else if (m == 2 && !ESCpressed){
                PVP =true;
            }
        }
        if (key == upKey1) {
            if (Shop.isShopAt((Player.players.get(0).px-15) / tile, ((Player.players.get(0).py-75) / tile) - 1)) {
                enterShop();  // 如果上方有商店，则进入商店
            } else {
                Player.players.get(0).up = true;
                Player.players.get(0).direction = 0;
            }
        } else if (key == downKey1) {
            if (Shop.isShopAt((Player.players.get(0).px-15) / tile, ((Player.players.get(0).py-75) / tile) + 1)) {
                enterShop();  // 如果下方有商店，则进入商店
            } else {
                Player.players.get(0).down = true;
                Player.players.get(0).direction = 2;
            }
        } else if (key == leftKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) - 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();  // 如果左方有商店，则进入商店
            } else {
                Player.players.get(0).left = true;
                Player.players.get(0).direction = 3;
            }
        } else if (key == rightKey1) {
            if (Shop.isShopAt(((Player.players.get(0).px-15) / tile) + 1, (Player.players.get(0).py-75) / tile)) {
                enterShop();  // 如果右方有商店，则进入商店
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
                    enterShop();  // 如果上方有商店，则进入商店
                } else {
                    Player.players.get(1).up = true;
                    Player.players.get(1).direction = 0;
                }
            } else if (keyCode == DOWN) {
                if (Shop.isShopAt((Player.players.get(1).px-15) / tile, ((Player.players.get(1).py-75) / tile) + 1)) {
                    enterShop();  // 如果下方有商店，则进入商店
                } else {
                    Player.players.get(1).down = true;
                    Player.players.get(1).direction = 2;
                }
            } else if (keyCode == LEFT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) - 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();  // 如果左方有商店，则进入商店
                } else {
                    Player.players.get(1).left = true;
                    Player.players.get(1).direction = 3;
                }
            } else if (keyCode == RIGHT) {
                if (Shop.isShopAt(((Player.players.get(1).px-15) / tile) + 1, (Player.players.get(1).py-75) / tile)) {
                    enterShop();  // 如果右方有商店，则进入商店
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

    private void gameEndDetectPVE(){
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
            }
        }
    }

    private void gameEndDetectPVP() {
        if(Player.players.get(0).otherPlayerWon){
            System.out.println("Player 2 won!");
        }else if(Player.players.get(1).otherPlayerWon){
            System.out.println("Player 1 won!");
        }
    }

    public void updateButton(){
        if(overRectButton(330,160,300,40)){
            overRectButton1 = true;
            overRectButton2 = false;
            overRectButton3 = false;
        } else if (overRectButton(330,230,300,40)) {
            overRectButton1 = false;
            overRectButton2 = true;
            overRectButton3 = false;
        } else if (overRectButton(330,300,300,40)) {
            overRectButton1 = false;
            overRectButton2 = false;
            overRectButton3 = true;
        }
    }
    public boolean overRectButton(int x, int y, int width, int height)  {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }
    
    public void playBGM(AudioPlayer player){
      if (!player.isPlaying()){
            player.loop(); // Resume playing if paused
       }
    }
