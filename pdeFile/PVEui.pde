

public static class PVEui {
    public static int seconds = 10;
    private static PApplet parent;
    static Boolean PVEuivisible = false;

    public PVEui(PApplet parent) {
        this.parent = parent;
    }

    public static void PVEUIShow() {
        PVEuivisible = true;
    }

    public static void PVEUIHide() {
        PVEuivisible = false;
    }
    public String pveShopMouseClicked(int x, int y) {
        if (x>=530 && x<625 && y>=90 && y<=130) {
            return "VerticalFlames";
        }

        if (x>=530 && x<625 && y>=155 && y<=195) {
            return "RoundFlames";
        }

        if (x>=530 && x<625 && y>=220 && y<=260) {
            return "MoveToTheDoor";
        }

        if (x>=530 && x<625 && y>=285 && y<=325) {
            return "KillAllEnemies";
        }

        if (x>=530 && x<625 && y>=350 && y<=390) {
            return "RemoveAllRocks";
        }

        if (x>=530 && x<625 && y>=415 && y<=455) {
            return "buyKey";
        }

        if (x>=400 && x<=520 && y>=470 && y<=500) {
            return "close";
        }
        else {
            return "";
        }
    }
    public void dealOperation(String op) {
        switch (op) {
            case "close":
                GameLoop.openShop = false;
                System.out.println("shop closed");
                break;

            case "VerticalFlames":
                System.out.println("buy VerticalFlames");
                if(Character.players.get(0).getCoin() >= 1){
                    UltimateAbilities.abilitiesList.add(1);
                    Character.players.get(0).coin -= 1;
                    coinDrop.play();
                }
                    break;

            case "RoundFlames":
                System.out.println("buy RoundFlames");
                if(Character.players.get(0).getCoin() >= 2){
                    UltimateAbilities.abilitiesList.add(2);
                    Character.players.get(0).coin -= 2;
                    coinDrop.play();
                }
                break;

            case "MoveToTheDoor":
                System.out.println("buy MoveToTheDoor");
                if(Character.players.get(0).getCoin() >= 3){
                    UltimateAbilities.abilitiesList.add(3);
                    Character.players.get(0).coin -= 3;
                    coinDrop.play();
                }
                break;

            case "KillAllEnemies":
                System.out.println("buy KillAllEnemies");
                if(Character.players.get(0).getCoin() >= 4){
                    UltimateAbilities.abilitiesList.add(4);
                    Character.players.get(0).coin -= 4;
                    coinDrop.play();
                }
                break;

            case "RemoveAllRocks":
                System.out.println("buy RemoveAllRocks");
                if(Character.players.get(0).getCoin() >= 5){
                    UltimateAbilities.abilitiesList.add(5);
                    Character.players.get(0).coin -= 5;
                    coinDrop.play();
                }
                break;

            case "buyKey":
                System.out.println("buy the key");
                if(Character.players.get(0).getCoin() >= 6){
                    Character.players.get(0).coin -= 6;
                    Character.players.get(0).isHavingTheKey = true;
                    coinDrop.play();
                }
                break;

            default:
                break;
        }
    }
    public static String getAbilityName(){
        if(UltimateAbilities.abilitiesList.isEmpty()){
            return "None";
        }
        switch(UltimateAbilities.abilitiesList.get(0)){
            case 1: return "VerticalFlames";
            case 2: return "RoundFlames";
            case 3: return "HiddenDoorFlash";
            case 4: return "KillAllEnemies";
            case 5: return "RemoveAllRocks";
            default: return "None";
        }
    }
}
