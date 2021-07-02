package ru.ifmo.lib;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Connection extends Thread {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Chat chat;
    private String userName;



    public Connection(Socket socket, Chat chat) throws IOException {
        this.socket = socket; // устанавливает соединение между программами
        this.chat = chat;
        this.output = new ObjectOutputStream(socket.getOutputStream()); // сначала открывается output потом input, иначе блокировка канала
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws IOException {
        System.out.println("Отправка сообщения");
        message.setDateTime();
        this.output.writeObject(message);
        this.output.flush(); // байты из программы уходят в поток (при работе с сетью)
    }

    public Message readMessage() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }

    @Override
    public void run() {
        try{
            while (!socket.isClosed()) {
                // серверная нить ждёт в канале чтения (inputstream) получения
                // данных клиента после получения данных считывает их
                Message message = readMessage();
                if(Objects.isNull(this.userName)){
                    this.userName = message.getSender();
                }
                chat.addMessage(message);
                chat.sendMessagesAllUser();

                // и выводит в консоль
                System.out.println(userName + ": Получено сообщение" + message);

            }
            output.close();
            input.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Соединение разорвано");
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }
}

