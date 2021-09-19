package Archives;

import net.minecraft.server.v1_8_R3.Block;

import javax.xml.stream.Location;

public enum BwTeam {
    RED,BLUE,GREEN,YELLOW,AQUA,WHITE,PINK,GRAY;

    private Boolean hasBed;
    private Location spawn;
    private Location gen;
    private Block bed1;
    private Block bed2;

    BwTeam(){
        this.hasBed=true;
    }



    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getGen() {
        return gen;
    }

    public void setGen(Location gen) {
        this.gen = gen;
    }

    public Block getBed1() {
        return bed1;
    }

    public void setBed1(Block bed1) {
        this.bed1 = bed1;
    }




    public Boolean getHasBed() {
        return hasBed;
    }

    public void setHasBed(Boolean hasBed) {
        this.hasBed = hasBed;
    }
}
