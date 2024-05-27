package com.example.server.Router;

import com.example.server.HttpHandlers.*;
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

        server.get("/search", SearchHandler::searchByName);

        server.get("/posts/feeds", PostHandler::showAllPosts);
        server.get("/posts", PostHandler::getPostHandler);
        server.post("/posts/add", PostHandler::addPostHandler);
        server.put("/posts/update", PostHandler::updatePostHandler);
        server.delete("/posts/delete", PostHandler::deletePostHandler);
    }
}
