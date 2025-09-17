package com.oop.examples.patterns.dnd;

public class ConsoleFightLogObserver implements FightLogObserver {
    @Override
    public void onLog(String message) {
        System.out.println(message);
    }
}
