package com.example.server;

import com.example.server.Router.Router;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server {
    private HttpServer server;

    public Server(int port) throws IOException {
        createServer(port);
    }

    private void createServer(int port) throws IOException {
        InetAddress localAddress = InetAddress.getByName("127.0.0.1");
        this.server = HttpServer.create(new InetSocketAddress(localAddress, port), 0);

        Router.route(this);

        server.start();
    }

    public HttpServer getServer() {
        return server;
    }

    public void get(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("GET")) {
                HashMap<String, String> params = this.getQueryParams(exchange.getRequestURI().getQuery());
                exchange.setAttribute("queryParams", params);
                handler.handle(exchange);
            } else {
                // TO-DO bad request error
            }
        });
    }

    public void post(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("POST")) {
                HashMap<String, String> params = this.getQueryParams(exchange.getRequestURI().getQuery());
                exchange.setAttribute("queryParams", params);
                handler.handle(exchange);
            } else {
                // TO-DO bad request error
            }
        });
    }

    public void delete(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("DELETE")) {
                HashMap<String, String> params = this.getQueryParams(exchange.getRequestURI().getQuery());
                exchange.setAttribute("queryParams", params);
                handler.handle(exchange);
            } else {
                // TO-DO bad request error
            }
        });
    }

    public void put(String path, HttpHandler handler) {
        server.createContext(path, (exchange) -> {
            if (exchange.getRequestMethod().equals("PUT")) {
                HashMap<String, String> params = this.getQueryParams(exchange.getRequestURI().getQuery());
                exchange.setAttribute("queryParams", params);
                handler.handle(exchange);
            } else {
                // TO-DO bad request error
            }
        });
    }

    private HashMap<String, String> getQueryParams(String query) {
        HashMap<String, String> queryParams = new HashMap<>();

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                int idx = param.indexOf("=");
                queryParams.put(param.substring(0, idx), param.substring(idx + 1));
            }
        }

        return queryParams;
    }
}
