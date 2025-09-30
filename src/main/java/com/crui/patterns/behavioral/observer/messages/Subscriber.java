package com.crui.patterns.behavioral.observer.messages;

public interface Subscriber {

    void status(Boolean connect);
    void receive(String message);
    String getUser();

}
