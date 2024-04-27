import java.util.HashSet;
public static class BombPowerUp extends Items{
    /*int x, y;
    boolean visible = false;*/
    boolean markedForRemoval = false;
    PImage powerUpImage;

    public BombPowerUp(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.powerUpImage = parent.loadImage("images/fire_up.png");
        System.out.println("PowerUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(this.visible) {
            parent.image(this.powerUpImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("powerUp item visible: " + visible);
    }

    public static void setPowerUps(PApplet parent){
        //HashSet<Integer> chosenIndexes = new HashSet<>();
        // Randomly select 5 rocks to place bomb firepower enhancement items
        for (int i=0; i<powerUp_items; i++) {
            int powerUpIndex = (int) parent.random(Obstacle.rocks.size());
            while (!Items.chosenIndexes.add(powerUpIndex)) {
                powerUpIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock powerUpRock = Obstacle.rocks.get(powerUpIndex);
            BombPowerUp powerUp = new BombPowerUp(powerUpRock.x(), powerUpRock.y(), parent);
            powerUpRock.setHidePowerUp(true);
            powerUpRock.setHiddenPowerUp(powerUp); // Associate BombPowerUp and BreakableRock
            powerUps.add(powerUp);
        }
    }

    public static void getPowerUp(PApplet parent){
        for (BombPowerUp powerUp : Items.powerUps) {
            if (powerUp.visible) {
                powerUp.render(parent);
                // Check if player collects it
                Player player = Character.players.get(0);
                float distanceToPowerUp = dist(player.px, player.py, powerUp.x, powerUp.y);
                if (distanceToPowerUp < 30) {
                    powerUp.setVisible(false);
                    Character.players.get(0).increasePower();
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
