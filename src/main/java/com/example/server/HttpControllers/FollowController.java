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
            return null;
        }
        return followDB.getFollowed(email);
    }

    public static ArrayList<Follow> getFollowing(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            return null;
        }
        return followDB.getFollow(email);
    }


}



