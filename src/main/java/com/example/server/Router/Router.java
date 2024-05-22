package com.example.server.Router;

import com.example.server.HttpHandlers.UserHandler;
import com.example.server.Server;

public class Router {
    public static void route(Server server) {
        server.get("/user", UserHandler::getUserHandler);
        server.get("/users", UserHandler::getAllUserHandler);
        server.post("/signup", UserHandler::postUserHandler);

        server.get("/user/skill", UserHandler::getSkillHandler);
        server.get("/user/education", UserHandler::getEducationHandler);
        server.get("/user/educations", UserHandler::getAllEducationHandler);
        server.get("/user/contact", UserHandler::getContactHandler);
    }
}
