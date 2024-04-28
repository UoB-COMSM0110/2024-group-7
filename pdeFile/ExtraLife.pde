
public static class ExtraLife extends Items{
    boolean markedForRemoval = false;
    PImage extraLifeImage;

    ExtraLife(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.extraLifeImage = parent.loadImage("images/health_up.png");
        System.out.println("extraLife: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(visible) {
            parent.image(this.extraLifeImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("extraLife item visible: " + visible);
    }

    public static void setExtraLives(PApplet parent){
        //HashSet<Integer> chosenIndexes = new HashSet<>();
        // Randomly select 2 rocks to place bomb firepower enhancement items
        for (int i=0; i<extraLife_items; i++) {
            int extraLifeIndex = (int) parent.random(Obstacle.rocks.size());
            while (!Items.chosenIndexes.add(extraLifeIndex)) {
                extraLifeIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock extraLifeRock = Obstacle.rocks.get(extraLifeIndex);
            ExtraLife extraLife = new ExtraLife(extraLifeRock.x(), extraLifeRock.y(), parent);
            extraLifeRock.setHideExtraLife(true);
            extraLifeRock.setHiddenExtraLife(extraLife); // Associate ExtraLife and BreakableRock
            extraLives.add(extraLife);
        }
    }

    public static void resetExtraLives(PApplet parent){
        for(ExtraLife extraLife : extraLives){
            int extraLifeIndex = (int) parent.random(Obstacle.rocks.size());
            while (!Items.chosenIndexes.add(extraLifeIndex)) {
                extraLifeIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock extraLifeRock = Obstacle.rocks.get(extraLifeIndex);
            extraLife.x = extraLifeRock.px;
            extraLife.y = extraLifeRock.py;
            extraLifeRock.hiddenExtraLife = extraLife;
            extraLifeRock.hideExtraLife = true;
            extraLife.visible = false;
            extraLife.markedForRemoval = false;
        }
    }

    public static void getExtraLife(PApplet parent){
        for (ExtraLife extraLife : Items.extraLives) {
            if (extraLife.visible) {
                extraLife.render(parent);
                // Check if player collects it
                Player player = Character.players.get(0);
                float distanceToExtraLife = dist(player.px, player.py, extraLife.x, extraLife.y);
                if (distanceToExtraLife < 30) {
                    extraLife.setVisible(false);
                    Character.players.get(0).increaseLife();
                    pick.play();
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
