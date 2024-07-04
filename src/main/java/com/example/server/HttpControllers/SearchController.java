package com.example.server.HttpControllers;

import com.example.server.database_conn.HashtagDB;
import com.example.server.database_conn.PostDB;
import com.example.server.database_conn.SkillDB;
import com.example.server.models.Hashtag;
import com.example.server.models.Post;
import com.example.server.models.Skill;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class SearchController extends BaseController {
    private static final HashtagDB hashtagDB;
    private static final PostDB postDB;

    static {
        try {
            hashtagDB = new HashtagDB();
            postDB = new PostDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> getUsersByName(String name) throws SQLException {
       return userDB.getUserByName(name);
    }

    public static ArrayList<Post> getPostsByHashtag(String hashtagToSearch) throws SQLException {
        ArrayList<Hashtag> hashtags = hashtagDB.getHashtag(hashtagToSearch);

        ArrayList<Post> posts = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            Post post = postDB.getPost(hashtag.getPostId());
            posts.add(post);
        }

        return posts;
    }

    public static ArrayList<Skill> getSkills(String searchKey) throws SQLException {
        return skillDB.getSkillsBySkill(searchKey);
    }
}
