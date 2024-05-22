package com.example.server.Router;

import com.example.server.HttpHandlers.UserHandler;
import com.example.server.Server;

public class Router {
    public static void route(Server server) {
        server.post("/signup", UserHandler::userPostHandler);
    }
}
