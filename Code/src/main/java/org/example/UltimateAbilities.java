package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class UltimateAbilities extends GameLoop{
    public static ArrayList<Integer> abilitiesList = new ArrayList<>();

    public static void generateUltimateFire(Player player){
        if(!player.useAbility || abilitiesList.isEmpty()) {
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
    }

    public static void generateFlameAround2(int startX, int startY, int flameRange) {
        for (int i = Math.max(0, startX - flameRange); i <= Math.min(cols - 1, startX + flameRange); i++) {
            for (int j = Math.max(0, startY - flameRange); j <= Math.min(rows - 1, startY + flameRange); j++) {
                if (i != startX || j != startY) {
                    if (i >= 0 && i < cols && j >= 0 && j < rows) {
                        Objects.ultimateFlames[i][j].appearUltimate();
                    }
                }
            }
        }
    }

    public static void generateFlameVertical1(int startX, int startY) {
        for (int i = 0; i <=cols-1; i++) {
            if (i != startX) {
                Objects.ultimateFlames[i][startY].appearUltimate();
            }
        }
        for (int i = 0; i <=rows-1; i++) {
            if (i != startY) {
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
                Objects.enemyNoHarmFlames[x][y].duration = 4000;
                Objects.enemyNoHarmFlames[x][y].appearUltimate();
            }
        }
    }

}
