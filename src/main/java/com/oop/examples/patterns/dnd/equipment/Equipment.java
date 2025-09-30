package com.oop.examples.patterns.dnd.equipment;

// Abstract class for equipment
abstract static class Equipment {
    protected String type;
    protected int stat;

    public Equipment(String type, int stat) {
        this.type = type;
        this.stat = stat;
    }

    public String getType() {
        return type;
    }

    public int getStat() {
        return stat;
    }
}