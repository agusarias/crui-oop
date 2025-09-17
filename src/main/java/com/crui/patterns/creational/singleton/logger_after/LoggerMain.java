package com.crui.patterns.creational.singleton.logger_after;

public class LoggerMain {
    public static void main(String[] args) {
        Logger logger1 = new Logger();
        Logger logger2 = new Logger();

        if (logger1 == logger2) {
            System.out.println("No importa su contenido, ocupan el mismo espacio en memoria");
        } else {
            System.out.println("Las variables logger1 y logger2 apuntan a instancias diferentes.");
        }

        logger1.info("--- Simulación del Cajero ---");

        ATM atm = new ATM();

        atm.startTransaction("Diego");
        atm.makeDeposit(100000);
        atm.withdrawMoney(250000); 
        atm.makeDeposit(-10000); 
        atm.endTransaction();

        logger2.info("--- Verificación Final ---");

        logger1.info("Simulación terminada desde logger1.");
        logger2.info("Mensaje de otro logger (logger2).");
    }
}