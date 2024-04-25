package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.awt.Point;
import java.util.Collections;

public class Coin extends Items {
    boolean markedForRemoval = false;
    PImage coinImage;

    public Coin(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.coinImage = parent.loadImage("images/coin.png");
        System.out.println("Coin: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(this.visible) {
            parent.image(this.coinImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("coin item visible: " + visible);
    }

    public static void resetCoins(PApplet parent){
        for(Coin coin : coins){
            int coinIndex = (int) parent.random(Obstacle.rocks.size());
            while (!Items.chosenIndexes.add(coinIndex)) {
                coinIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock coinRock = Obstacle.rocks.get(coinIndex);
            coin.x = coinRock.px;
            coin.y = coinRock.py;
            coinRock.hiddenCoin= coin;
            coinRock.hideCoin = true;
            coin.visible = false;
            coin.markedForRemoval = false;
        }
    }

    public static ArrayList<Point> getAvailableSpacesForCoins(PApplet parent) {
        ArrayList<Point> availableSpaces = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (!Wall.isWallAt(i, j) && !Shop.isShopAt(i, j)) {
                    availableSpaces.add(new Point(i * tile + 15, j * tile + 75));
                }
            }
        }
        return availableSpaces;
    }

    public static void setCoinsInEmptySpaces(PApplet parent) {
        ArrayList<Point> availableSpaces = getAvailableSpacesForCoins(parent);
        Collections.shuffle(availableSpaces); // Randomize the available positions
        for (int i = 0; i < coinInEmptySpace_items && i < availableSpaces.size(); i++) {
            Point selectedPoint = availableSpaces.get(i);
            Coin newCoin = new Coin(selectedPoint.x, selectedPoint.y, parent);
            newCoin.setVisible(true); // These coins are visible immediately
            coinsInEmptySpace.add(newCoin);
            //System.out.println("Coin placed in empty space at: (" + selectedPoint.x + ", " + selectedPoint.y + ")");
        }
    }

    public static boolean isCoinsInEmptySpaceAt(int i, int j) {
        int x = 15 + i * tile;
        int y = 75 + j * tile;

        // Iterate over all shops to see if any is at the (x, y) position
        for (Coin coinInEmptySpace : coinsInEmptySpace) {
            if (coinInEmptySpace.x() == x && coinInEmptySpace.y() == y) {
                return true;  // Found a shop at the given grid position
            }
        }
        return false;  // No shop found at this grid position
    }



    public static void getCoin(PApplet parent){
        for (Coin coin : Items.coins) {
            if (coin.visible) {
                coin.render(parent);
                // Check if player collects it
                Player player = Characters.players.get(0);
                float distanceToCoin = dist(player.px, player.py, coin.x, coin.y);
                if (distanceToCoin < 30) {
                    coin.setVisible(false);
                    Characters.players.get(0).increaseCoin();
                }
            }
        }
        for (Coin coinInEmptySpace : Items.coinsInEmptySpace) {
            if (coinInEmptySpace.visible) {
                coinInEmptySpace.render(parent);
                // Check if player collects it
                Player player = Characters.players.get(0);
                float distanceToCoin = dist(player.px, player.py, coinInEmptySpace.x, coinInEmptySpace.y);
                if (distanceToCoin < 30) {
                    coinInEmptySpace.setVisible(false);
                    Characters.players.get(0).increaseCoin();
                }
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return this.markedForRemoval;
    }
}

