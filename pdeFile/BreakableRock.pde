import java.util.ArrayList;
import java.util.Iterator;

public static class BreakableRock extends Obstacle{
    PImage rock;
    int health;
    boolean rockExist;
    boolean hideKey = false;
    boolean hideDoor = false;
    boolean hidePowerUp = false;
    boolean hideExtraBomb = false;
    boolean hideSpeedUp = false;
    boolean hideExtraLife = false;
    boolean hideCoin = false;
    public BombPowerUp hiddenPowerUp;
    public ExtraBomb hiddenExtraBomb;
    public SpeedUp hiddenSpeedUp;
    public ExtraLife hiddenExtraLife;
    public Coin hiddenCoin;
    boolean markedForRemoval = false;
    BreakableRock(int x, int y, PApplet parent, PImage rock) {
        /*super(x, y, parent, rock);*/
        this.px=x;
        this.py=y;
        this.rock = rock;
        this.parent =parent;
        this.health = 1;
        this.rockExist = true;
    }

    public static ArrayList<BreakableRock> generateRocks(int rows, int cols, PApplet parent, float chanceOfRock) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Randomly decide whether to place a rock in this grid cell
                // Exclude the area around where player initially stand
                if (parent.random(1) < chanceOfRock && !Wall.isWallAt(i, j) && !Shop.isShopAt(i,j) && !Coin.isCoinsInEmptySpaceAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    rocks.add(new BreakableRock(x, y, parent, ResourceManager.rock));
                }
            }
        }
        return rocks;
    }

    public static ArrayList<BreakableRock> generateLessRocks(int rows, int cols, PApplet parent, float chanceOfRock) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Randomly decide whether to place a rock in this grid cell
                // Exclude the area around where player initially stand
                if (parent.random(1) < chanceOfRock && !Wall.isWallAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    lessRocks.add(new BreakableRock(x, y, parent, ResourceManager.rock));
                }
            }
        }
        return lessRocks;
    }

    // 移除被標記的岩石
    public static void removeRocks() {
        Iterator<BreakableRock> rockIterator = Obstacle.rocks.iterator();
        while (rockIterator.hasNext()) {
            BreakableRock rock = rockIterator.next();
            if (rock.isMarkedForRemoval()) {
                rock.rockExist = false;
                Obstacle.removeRockFromObstacleGrid(rock);
                //rockIterator.remove();
            }
        }
    }

    public static void removeLessRocks() {
        Iterator<BreakableRock> rockIterator = Obstacle.lessRocks.iterator();
        while (rockIterator.hasNext()) {
            BreakableRock rock = rockIterator.next();
            if (rock.isMarkedForRemoval()) {
                Obstacle.removeRockFromObstacleGridPVP(rock);
                rockIterator.remove();
            }
        }
    }


    public static void updateRockPositions(PApplet parent) {
        for (BreakableRock rock : rocks) {
            boolean validPosition = false;
            while (!validPosition) {
                int i = (int) (parent.random(cols));
                int j = (int) (parent.random(rows));

                // 检查新位置是否符合条件：不在特殊区域内，不是墙也不是商店和不是硬币所在位置
                if (!Wall.isWallAt(i, j) && !Shop.isShopAt(i, j) && !Coin.isCoinsInEmptySpaceAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4)) {
                    rock.px = 15 + i * tile;
                    rock.py = 75 + j * tile;
                    rock.rockExist = true;
                    rock.markedForRemoval = false;
                    rock.hideCoin = false;
                    validPosition = true;
                }
            }
        }
    }


    public void ifDestroyRock(){
        //handle the interaction between rocks and flames
        if (Flame.flameCheck(this.x(), this.y())) {
            this.health -= 1;
            if (this.health == 0) {
                this.rockExist = false;
            }
        }
    }

    void render(){
        parent.image(ResourceManager.rock,px,py,30,30);
    }

    public void setHideKey(boolean hideKey) {
        this.hideKey = hideKey;
    }

    public void setHideDoor(boolean hideDoor) {
        this.hideDoor = hideDoor;
    }

    public void setHideSpeedUp(boolean hideSpeedUp) {
        this.hideSpeedUp = hideSpeedUp;
    }

    // Method to set the hidden power-up
    public void setHiddenSpeedUp(SpeedUp SpeedUp) {
        this.hiddenSpeedUp = SpeedUp;
    }

    // Method to get the associated power-up
    public BombPowerUp getAssociatedPowerUp() {
        return this.hiddenPowerUp;
    }

    public void setHideExtraBomb(boolean hideExtraBomb) {
        this.hideExtraBomb = hideExtraBomb;
    }

    // Method to set the hidden extraBomb
    public void setHiddenExtraBomb(ExtraBomb extraBomb) {
        this.hiddenExtraBomb = extraBomb;
    }

    // Method to get the associated extraBomb
    public ExtraBomb getAssociatedExtraBomb() {
        return this.hiddenExtraBomb;
    }

    public void setHidePowerUp(boolean hidePowerUp) {
        this.hidePowerUp = hidePowerUp;
    }

    // Method to set the hidden power-up
    public void setHiddenPowerUp(BombPowerUp powerUp) {
        this.hiddenPowerUp = powerUp;
    }
    public void setHideExtraLife(boolean hideExtraLife) {
        this.hideExtraLife = hideExtraLife;
    }

    public void setHiddenExtraLife(ExtraLife extraLife) {
        this.hiddenExtraLife = extraLife;
    }

    public void setHideCoin(boolean hideCoin) {
        this.hideCoin = hideCoin;
    }

    public void setHiddenCoin(Coin coin) {
        this.hiddenCoin = coin;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return this.markedForRemoval;
    }

    public static void rocksRender(){
        for (BreakableRock rock : rocks) {
            if (rock.rockExist) {
                rock.render();
            }
            rock.ifDestroyRock();
        }
    }

    public static void lessRocksRender(){
        for (BreakableRock rock : lessRocks) {
            if (rock.rockExist) {
                rock.render();
            }
            rock.ifDestroyRock();
        }
    }
}
