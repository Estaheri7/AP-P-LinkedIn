package com.example.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server httpServer = new Server(8080);
    }
}
