package com.crui.patterns.behavioral.observer.messages;

import java.util.ArrayList;
import java.util.List;

/*Observer es un patrón de diseño de comportamiento 
que te permite definir un mecanismo de suscripción para NOTIIFICAR a varios objetos sobre cualquier evento que le suceda al objeto que están observando. */

public class Notifier{
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void notifyStatus(Boolean status){
        for (Subscriber subscriber : subscribers) {
            System.out.print(subscriber.getUser()+": ");
            subscriber.status(status);
            
        }
        System.out.println("\n");
    }

    public void notifyMessage(String message){
        for (Subscriber subscriber : subscribers) {
            System.out.print(subscriber.getUser()+": ");
            subscriber.receive(message);
        }
        System.out.println("\n");
    }
}
