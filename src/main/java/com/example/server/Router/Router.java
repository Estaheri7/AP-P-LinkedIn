package com.example.server.Router;

import com.example.server.HttpHandlers.*;
import com.example.server.Server;

public class Router {
    public static void route(Server server) {
        server.post("/signup", UserHandler::postUserHandler);
        server.post("/login", AuthHandler::loginHandler);

        // by email
        server.get("/user", UserHandler::getUserHandler);
        server.get("/users", UserHandler::getAllUserHandler);

        // by email
        server.get("/user/skill", UserHandler::getSkillHandler);
        // get last education
        server.get("/user/education", UserHandler::getEducationHandler);
        // get all educations
        server.get("/user/educations", UserHandler::getAllEducationHandler);
        server.get("/user/contact", UserHandler::getContactHandler);

        // by email
        server.get("/profile", ProfileHandler::getProfileHandler);

        // by email
        server.put("/profile/update", ProfileHandler::userUpdateHandler);
        server.put("/profile/update/skill", ProfileHandler::skillUpdateHandler);
        server.post("/profile/add/education", ProfileHandler::addEducationHandler);
        server.put("/profile/update/education", ProfileHandler::educationUpdateHandler);
        server.put("/profile/update/contact", ProfileHandler::contactUpdateHandler);

        // by name-lastName
        server.get("/search", SearchHandler::searchByName);

        // All posts
        server.get("/posts/feeds", PostHandler::showAllPosts);
        // by email
        server.get("/posts", PostHandler::getPostHandler);
        server.post("/posts/add", PostHandler::addPostHandler);
        server.put("/posts/update", PostHandler::updatePostHandler);
        server.delete("/posts/delete", PostHandler::deletePostHandler);

        // email and id
        server.put("/posts/like", PostHandler::likePostHandler);
        server.put("/posts/dislike", PostHandler::dislikePostHandler);

        // by postId
        server.get("/posts/likes", PostHandler::getLikesHandler);

        // by postId
        server.get("/posts/comments", PostHandler::getCommentsHandler);
        // by postId
        server.post("/posts/add-comment", PostHandler::addCommentHandler);
        // by commentId
        server.put("/posts/update-comment", PostHandler::updateCommentHandler);
        server.delete("/posts/delete-comment", PostHandler::removeCommentHandler);

        // by email
        server.get("/followers", FollowHandler::getFollowersHandler);
        server.get("/followings", FollowHandler::getfollowingsHandler);
        // by email
        server.put("/follow", FollowHandler::followHandler);
        server.put("/unfollow", FollowHandler::unfollowHandler);

        server.post("/send-connect", ConnectionHandler::sendConnectionHandler);
        server.get("/connections", ConnectionHandler::getConnectionsHandler);
    }
}
