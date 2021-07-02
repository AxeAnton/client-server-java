package ru.ifmo.serverapp;

import ru.ifmo.lib.Chat;
import ru.ifmo.lib.Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private Chat chat;

    public Server(int port) {
        this.port = port;
        this.chat = new Chat();
    }

    public void start(){
        try(
                ServerSocket server = new ServerSocket(port);
        ){
            System.out.println("Сервер запущен");
            while (!server.isClosed()){
                System.out.println("Есть клиент");

                Socket client =  server.accept(); // момент установки соединения с клиентом

                Connection connection = new Connection(client, chat);
                chat.addConnection(connection);

                connection.start();

            }
        } catch (IOException e) {
            System.out.println("Ошибка сервера");
        }
    }
}
