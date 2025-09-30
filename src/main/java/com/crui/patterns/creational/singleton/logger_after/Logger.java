package com.crui.patterns.creational.singleton.logger_after;

public class Logger {
    
    public Logger() {
    }

    public void info(String mensaje) {
        System.out.println("[INFO] " + mensaje);
    }

    public void error(String mensaje) {
        System.err.println("[ERROR] " + mensaje);
    }

    public void warning(String mensaje) {
        System.out.println("[WARNING] " + mensaje);
    }
}