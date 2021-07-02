package ru.ifmo.lib;

import java.io.IOException;
import java.io.ObjectOutputStream;
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

    public Message getMessages(){
        try {
            return messages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

//    @Override
//    public void run() {
//        while (true){
//            try {
//                Message message = messages.take();
//
//                System.out.println("take " +message);
//                this.output.writeObject(message);
//                this.output.flush();
//            } catch (InterruptedException | IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
}
