package com.example.server.Router;

import com.example.server.HttpControllers.MediaController;
import com.example.server.HttpControllers.ProfileController;
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
        server.get("/user/avatar", MediaHandler::getAvatarHandler);
        server.get("/user/background", MediaHandler::getBackgroundHandler);
        server.put("/upload-avatar", MediaHandler::updateAvatarHandler);
        server.put("/upload-background", MediaHandler::updateBackgroundHandler);

        // by email
        server.get("/profile", ProfileHandler::getProfileHandler);

        // by id
        server.get("/post/media", MediaHandler::getPostMediaHandler);

        // by email
        server.put("/profile/update", ProfileHandler::userUpdateHandler);
        server.put("/profile/update/skill", ProfileHandler::skillUpdateHandler);
        server.post("/profile/add/education", ProfileHandler::addEducationHandler);
        server.put("/profile/update/education", ProfileHandler::educationUpdateHandler);
        server.put("/profile/update/contact", ProfileHandler::contactUpdateHandler);
        server.put("/profile/contact/visibility", ProfileHandler::changeVisibilityHandler);

        // by query
        server.get("/search", SearchHandler::searchHandler);

        // All posts
        server.get("/posts/feeds", PostHandler::showAllPosts);

        // by id
        server.get("/post", PostHandler::getPostByIdHandler);

        // by email
        server.get("/posts", PostHandler::getPostHandler);
        server.get("/lastPost", PostHandler::getLastPostHandler);
        server.post("/posts/add", PostHandler::addPostHandler);
        server.put("/posts/add-media", MediaHandler::addMediaToPostHandler);
        server.put("/posts/update", PostHandler::updatePostHandler);
        server.delete("/posts/delete", PostHandler::deletePostHandler);

        // by postId
        server.get("/posts/isLiked", PostHandler::likeExistsHandler);
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
        server.get("/hasFollowed", FollowHandler::checkFollowHandler);
        server.get("/followers", FollowHandler::getFollowersHandler);
        server.get("/followings", FollowHandler::getFollowingsHandler);
        // by email
        server.put("/follow", FollowHandler::followHandler);
        server.put("/unfollow", FollowHandler::unfollowHandler);

        // by email
        server.get("/connections", ConnectionHandler::getConnectionsHandler);
        server.get("/connections/receiver", ConnectionHandler::getReceiverNotificationHandler);
        server.get("/connections/sender", ConnectionHandler::getSenderNotificationHandler);
        server.get("/connections/pending/", ConnectionHandler::getPendingHandlers);
        server.get("/connect/isAccepted/", ConnectionHandler::isAcceptedHandler);
        server.post("/send-connect", ConnectionHandler::sendConnectionHandler);

        // by receiver email and sender query email
        server.put("/accept-connection", ConnectionHandler::acceptConnectionHandler);
        server.put("/decline-connection", ConnectionHandler::declineConnectionHandler);

        server.get("/chats", ChatHandler::displayChatHandler);
        server.get("/user/chat", ChatHandler::getReceiverChatHandler);
        server.post("/send-message", ChatHandler::sendMessageHandler);
        server.put("/send-file", MediaHandler::sendMediaChatHandler);

        // by unique file
        server.get("/chat-media", MediaHandler::getChatMediaHandler);

        // by email
        server.get("/notifications", NotificationHandler::getAllNotificationsHandler);
        server.post("/add-notification", NotificationHandler::addNotificationHandler);
    }
}
