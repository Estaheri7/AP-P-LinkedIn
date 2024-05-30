package com.example.server.HttpControllers;
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

    public static ArrayList<Follow> getFollowers(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return followDB.getFollowed(email);
    }

    public static ArrayList<Follow> getFollowing(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return followDB.getFollow(email);
    }

    public static void follow(String followerEmail, String followedEmail) throws SQLException {
        User follower = userDB.getUser(followerEmail);
        User followed = userDB.getUser(followedEmail);
        if (follower == null || followed == null) {
            throw new IllegalArgumentException("Invalid follower or followed email");
        }

        Follow follow = new Follow(0, followerEmail, followedEmail);
        userDB.increaseFollowers(followedEmail);
        userDB.increaseFollowings(followerEmail);
        followDB.insertData(follow);
    }

    public static void unfollow(String followerEmail, String followedEmail) throws SQLException {
        User follower = userDB.getUser(followerEmail);
        User followed = userDB.getUser(followedEmail);
        if (follower == null || followed == null) {
            throw new IllegalArgumentException("Invalid follower or followed email");
        }
        if (!followDB.isFollowed(followerEmail, followedEmail)) {
            throw new IllegalArgumentException("you have to follow first!");
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



