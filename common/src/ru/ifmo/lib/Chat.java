package ru.ifmo.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Chat  {
    private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private List<Connection> connections = new ArrayList<>();

    public void addConnection(Connection connection){
        this.connections.add(connection);
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public void sendMessagesAllUser(){
        try {
            Message message = messages.take();
            for (Connection connection : connections) {
                if(!message.getSender().equalsIgnoreCase(connection.getUserName())){
                    try {
                        connection.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
