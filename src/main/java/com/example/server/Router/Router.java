package com.example.server.Router;

import com.example.server.HttpHandlers.AuthHandler;
import com.example.server.HttpHandlers.ProfileHandler;
import com.example.server.HttpHandlers.UserHandler;
import com.example.server.Server;

public class Router {
    public static void route(Server server) {
        server.post("/signup", UserHandler::postUserHandler);
        server.post("/login", AuthHandler::loginHandler);

        server.get("/user", UserHandler::getUserHandler);
        server.get("/users", UserHandler::getAllUserHandler);

        server.get("/user/skill", UserHandler::getSkillHandler);
        server.get("/user/education", UserHandler::getEducationHandler);
        server.get("/user/educations", UserHandler::getAllEducationHandler);
        server.get("/user/contact", UserHandler::getContactHandler);

        server.get("/profile", ProfileHandler::getProfileHandler);

        server.put("/profile/update", ProfileHandler::userUpdateHandler);
        server.put("/profile/update/skill", ProfileHandler::skillUpdateHandler);
        server.post("/profile/add/education", ProfileHandler::addEducationHandler);
        server.put("/profile/update/education", ProfileHandler::educationUpdateHandler);
        server.put("/profile/update/contact", ProfileHandler::contactUpdateHandler);
    }
}
