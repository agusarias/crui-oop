package com.crui.patterns.behavioral.observer.messages;

public class MessageMain {
    public static void main(String[] args) {
        
        Notifier notifier = new Notifier();

        Message user1 = new Message("Hola, soy User1", "User1");
        Message user2 = new Message("Hola, soy User2", "User2");

        // Suscribir usuarios al notificador
        notifier.addSubscriber(user1);
        notifier.addSubscriber(user2);
        
        // Enviar notificaciones
        notifier.notifyStatus(true); // Notifica que están online
        notifier.notifyMessage("¡Aviso global!");
  
        notifier.notifyStatus(false); // Notifica que están offline
    }
}
