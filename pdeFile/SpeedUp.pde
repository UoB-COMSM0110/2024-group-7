
public static class SpeedUp extends Items{
    /*int x, y;
    boolean visible = false;*/
    boolean markedForRemoval = false;
    PImage speedUpImage;

    public SpeedUp(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.speedUpImage = parent.loadImage("images/speed_up.png");
        System.out.println("SpeedUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(this.visible) {
            parent.image(this.speedUpImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("speedUp item visible: " + visible);
    }

    public static void setSpeedUps(PApplet parent){
        // HashSet<Integer> chosenIndexes = new HashSet<>();
        for (int i=0; i<speedUp_items; i++) {
            int speedUpIndex = (int) parent.random(Obstacle.rocks.size());
            while (!Items.chosenIndexes.add(speedUpIndex)) {
                speedUpIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock speedUpRock = Obstacle.rocks.get(speedUpIndex);
            SpeedUp speedUp = new SpeedUp(speedUpRock.x(), speedUpRock.y(), parent);
            speedUpRock.setHideSpeedUp(true);
            speedUpRock.setHiddenSpeedUp(speedUp);
            speedUps.add(speedUp);
        }
    }

    public static void getSpeedUp(PApplet parent){
        for (SpeedUp speedUp : Items.speedUps) {
            if (speedUp.isVisible()) {
                speedUp.render(parent);
                Player player = Character.players.get(0);
                float distanceToSpeedUp = dist(player.px, player.py, speedUp.x, speedUp.y);
                if (distanceToSpeedUp < 30) {
                    speedUp.setVisible(false);
                    Character.players.get(0).increaseSpeed();
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
