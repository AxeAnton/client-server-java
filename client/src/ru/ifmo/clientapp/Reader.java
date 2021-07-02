package ru.ifmo.clientapp;

import ru.ifmo.lib.Message;

import java.io.ObjectInputStream;

public class Reader extends Thread {
    private ObjectInputStream input;

    public Reader(ObjectInputStream input){
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true){
                Message newMessages = (Message) input.readObject();
                System.out.println(newMessages.getSender() +": " + newMessages.getText());
            }
        } catch (Exception e) {
            System.out.println("Соединение разорвано");
            throw new RuntimeException(e);
        }
    }
}
