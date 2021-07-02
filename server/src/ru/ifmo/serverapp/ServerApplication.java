package ru.ifmo.serverapp;

public class ServerApplication {
    public static void main(String[] args) {
        new Server(8885).start();
    }
}
