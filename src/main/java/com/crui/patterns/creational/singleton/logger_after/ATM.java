package com.crui.patterns.creational.singleton.logger_after;

public class ATM {
    
    private final Logger logger = new Logger();

    public void startTransaction(String user) {
        logger.info("Transacción iniciada para el usuario: " + user);
    }

    public void makeDeposit(double amount) {
        if (amount <= 0) {
            logger.error("Error: Intento de depositar una cantidad no válida: " + amount);
            return;
        }
        logger.info("Depósito de " + amount + " realizado con éxito.");
    }

    public void withdrawMoney(double amount) {
        if (amount > 200000) {
            logger.warning("Alerta: Se está intentando retirar una cantidad grande: " + amount);
        }
        logger.info("Retiro de " + amount + " completado.");
    }

    public void endTransaction() {
        logger.info("Transacción finalizada.");
    }
}