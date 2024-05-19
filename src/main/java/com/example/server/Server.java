package com.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Server {
    private HttpServer server;

    public Server(int port) throws IOException {
        createServer(port);
    }

    private void createServer(int port) throws IOException {
        InetAddress localAddress = InetAddress.getByName("127.0.0.1");
        this.server = HttpServer.create(new InetSocketAddress(localAddress, port), 0);

        server.start();
    }

    public HttpServer getServer() {
        return server;
    }

    public void get(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("GET")) {
                handler.handle(exchange);
            } else {

            }
        });
    }

    public void post(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("POST")) {
                handler.handle(exchange);
            } else {

            }
        });
    }

    public void delete(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("DELETE")) {
                handler.handle(exchange);
            } else {

            }
        });
    }

    public void put(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("PUT")) {
                handler.handle(exchange);
            } else {

            }
        });
    }
}
