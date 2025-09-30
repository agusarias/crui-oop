package com.crui.patterns.behavioral.observer.messages;

public class Message implements Subscriber{
    private String message;
    private Boolean connect;
    private String user;

    public Message(String message, String user){
        this.message = message;
        this.user = user;
        this.connect = true;
    }

    @Override
    public void status(Boolean connect) {
        if(connect){
            System.out.println("Status: Online");
        }else{
            System.out.println("Status: Offline");
        }
    }

    @Override
    public void receive(String newMessage){
        System.out.println("New message: "+ newMessage);
    }

    public void setMessage(String newMessage){
        this.message = newMessage;
    }

    public String getMessage(){
        return this.message;
    }

    public void setConnect(Boolean connect){
        this.connect = connect;
    }

    public Boolean getConnect(){
        return this.connect;
    }

    @Override
    public String getUser(){
        return this.user;
    }

    public void setUser(String user){
        this.user = user;
    }


    
}
