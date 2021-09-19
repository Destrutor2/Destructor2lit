package com.destructor.destructor2lit.enums;

public enum BwUpgrade {
    SHARPNESS,PROTECTION,HASTE,FORGE,HEALPOOL,DRAGONBUFF;


    int level;
    BwUpgrade(int level){
        this.level=level;
    }
    BwUpgrade(){
        this.level=0;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
