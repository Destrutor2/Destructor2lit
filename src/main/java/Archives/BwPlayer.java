package Archives;

public class BwPlayer {

//    Ca me parait mieux d'utiliser de la metadata sur des Player à la place
 /*   Player player;
    BwTeam team;
    BwUpgrade[] upgrades={BwUpgrade.SHARPNESS,BwUpgrade.PROTECTION,BwUpgrade.HASTE,BwUpgrade.FORGE,BwUpgrade.HEALPOOL,BwUpgrade.HEALPOOL};
    int pickTier=0;


    int axeTier=0;

    public BwPlayer(Player player){
        this.player=player;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addUpgrade(BwUpgrade upgrade){
        int id=0;
        outer:
        for(BwUpgrade up:upgrades){
            if(up==upgrade){
                break outer;
            }
            id++;
        }
        this.upgrades[id].setLevel(this.upgrades[id].getLevel()+1);
    }

    public void addUpgrade(BwUpgrade upgrade,int lvl){
        int id=0;
        outer:
        for(BwUpgrade up:upgrades){
            if(up==upgrade){
                break outer;
            }
            id++;
        }
        this.upgrades[id].setLevel(this.upgrades[id].getLevel()+lvl);
    }

    public int getPickTier() {
        return pickTier;
    }

    public int getAxeTier() {
        return axeTier;
    }
    public void addPickTier(){
        pickTier++;
    }
    public void addAxeTier(){
        axeTier++;
    }*/
//    du coup je vais utiliser ce truc pour mettre toute la metadata du joueur
/*    int pickLevel=0;
    int axeLevel=0;

    public int getPickLevel() {
        return pickLevel;
    }

    public void addPickLevel() {
        pickLevel++;
    }

    public int getAxeLevel() {
        return axeLevel;
    }

    public void addAxeLevel() {
        axeLevel++;
    }*/
//    j'ai changé d'avis lol, au final je vais faire simple set mettre une metadata par valeur

}
