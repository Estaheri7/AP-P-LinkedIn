package com.example.server.HttpControllers;
import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.database_conn.FollowDB;
import com.example.server.database_conn.UserDB;
import com.example.server.models.Follow;
import com.example.server.models.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FollowController extends BaseController {

    private static FollowDB followDB;

    static {
        try {
            followDB = new FollowDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Follow> getFollowers(String email) throws SQLException, NotFoundException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return followDB.getFollowed(email);
    }

    public static ArrayList<Follow> getFollowing(String email) throws SQLException, NotFoundException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return followDB.getFollow(email);
    }

    public static void follow(String followerEmail, String followedEmail) throws SQLException, NotFoundException, DuplicateDataException {
        User follower = userDB.getUser(followerEmail);
        User followed = userDB.getUser(followedEmail);
        if (follower == null || followed == null) {
            throw new NotFoundException("User not found");
        }

        if (follower.equals(followed)) {
            throw new IllegalAccessError("Follower and Followed are the same");
        }

        if (isFollowed(followerEmail, followedEmail)) {
            throw new DuplicateDataException("You already followed this user");
        }

        Follow follow = new Follow(0, followerEmail, followedEmail);
        userDB.increaseFollowers(followedEmail);
        userDB.increaseFollowings(followerEmail);
        followDB.insertData(follow);
    }

    public static void unfollow(String followerEmail, String followedEmail) throws SQLException, NotFoundException {
        User follower = userDB.getUser(followerEmail);
        User followed = userDB.getUser(followedEmail);
        if (follower == null || followed == null) {
            throw new NotFoundException("Invalid follower or followed email");
        }

        if (follower.equals(followed)) {
            throw new IllegalAccessError("Follower and Followed are the same");
        }

        if (!isFollowed(followerEmail, followedEmail)) {
            throw new IllegalAccessError("you have to follow first!");
        }

        Follow follow = followDB.getFollow(followerEmail, followedEmail);
        userDB.decreaseFollowers(followedEmail);
        userDB.decreaseFollowings(followerEmail);
        followDB.deleteData(follow.getId());
    }

    public static boolean isFollowed(String followerEmail, String followedEmail) throws SQLException {
        return followDB.isFollowed(followerEmail, followedEmail);
    }
}



