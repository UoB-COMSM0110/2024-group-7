
public static class UltimateAbilities extends GameLoop{
    PApplet parent;
    public static boolean pvpClock;
    public static int k = 0;
    public static ArrayList<Integer> abilitiesList = new ArrayList<>();
    public static double time = 0;
    public static void generateUltimateFire(Player player, PApplet parent){
        //Player.players.get(0).speed = 3;
        //Player.players.get(0).health = 300;
        //Player.players.get(0).isHavingTheKey = true;
        //Player.players.get(0).maxBombs = 10;
        //removeTheRocks5();
        //for(int i =0;i<1;i++) {
        //    abilitiesList.add(5);
        //}

        if(!player.useAbility || abilitiesList.isEmpty()) {
            return;
        }
        if(parent.millis() - time < 1000){
          return;
        }
        int startX = ( player.px - 15 ) / tile;
        int startY = ( player.py - 75 ) / tile;

        switch(abilitiesList.get(0)){
            case 1:
                generateFlameVertical1(startX,startY);
                break;
            case 2:
                generateFlameAround2(startX,startY, 3);
                break;
            case 3:
                moveToTheDoor3(player);
                break;
            case 4:
                killTheEnemies4();
                break;
            case 5:
                removeTheRocks5();
                break;
        }
        abilitiesList.remove(0);
        time = parent.millis();
    }

    public static void generateFlameAround2(int startX, int startY, int flameRange) {
        for (int i = Math.max(0, startX - flameRange); i <= Math.min(cols - 1, startX + flameRange); i++) {
            for (int j = Math.max(0, startY - flameRange); j <= Math.min(rows - 1, startY + flameRange); j++) {
                if (i != startX || j != startY) {
                    if (i >= 0 && i < cols && j >= 0 && j < rows) {
                        if(!(i==startX - flameRange && j == startY - flameRange) &&
                                !(i==startX + flameRange && j == startY + flameRange) &&
                                !(i==startX + flameRange && j == startY - flameRange) &&
                                !(i==startX - flameRange && j == startY + flameRange)) {
                            Objects.enemyNoHarmFlames[i][j].duration = 2000;
                            Objects.ultimateFlames[i][j].appearUltimate();
                        }
                    }
                }
            }
        }
    }

    public static void generateFlameVertical1(int startX, int startY) {
        for (int i = 0; i <=cols-1; i++) {
            if (i != startX) {
                Objects.enemyNoHarmFlames[i][startY].duration = 2000;
                Objects.ultimateFlames[i][startY].appearUltimate();
            }
        }
        for (int i = 0; i <=rows-1; i++) {
            if (i != startY) {
                Objects.enemyNoHarmFlames[startX][i].duration = 2000;
                Objects.ultimateFlames[startX][i].appearUltimate();
            }
        }
    }

    public static void moveToTheDoor3(Player player) {
        for(BreakableRock rock : BreakableRock.rocks){
            if(rock.hideDoor){
                int x = (rock.px-15)/tile;
                int y = (rock.py-75)/tile;
                generateFlameAround2(x, y,1);
                Objects.ultimateFlames[x][y].appearUltimate();
                Objects.ultimateFlames[x][y].appearUltimate();
                player.px = rock.px;
                player.py = rock.py;
                break;
            }
        }
    }

    public static void killTheEnemies4() {
        for(Enemy enemy : Character.enemies){
            if(enemy.exist) {
                int x = (enemy.px - 15) / tile;
                int y = (enemy.py - 75) / tile;
                Objects.ultimateFlames[x][y].appearUltimate();
            }
        }
    }

    public static void removeTheRocks5() {
        for(BreakableRock rock : Obstacle.rocks){
            if(rock.rockExist) {
                int x = (rock.px - 15) / tile;
                int y = (rock.py - 75) / tile;
                Objects.enemyNoHarmFlames[x][y].duration = 3000;
                Objects.enemyNoHarmFlames[x][y].appearUltimate();
            }
        }
    }

    public static void generatePVPFlames() {
        //System.out.println(k);
        if(pvpClock){
            if(k < 7){
                k++;
            }
            for (int i = k; i < cols - k; i++) {
                Objects.flames[i][k].duration = 100000;//100s
                Objects.flames[i][k].appear();

                Objects.flames[i][rows - 1 - k].duration = 100000;
                Objects.flames[i][rows - 1 - k].appear();
            }
            for (int j = 1 + k; j < rows - k; j++) {
                Objects.flames[k][j].duration = 100000;
                Objects.flames[k][j].appear();

                Objects.flames[cols - 1 - k][j].duration = 100000;
                Objects.flames[cols - 1 - k][j].appear();
            }
            pvpClock = false;
        }
    }
}
