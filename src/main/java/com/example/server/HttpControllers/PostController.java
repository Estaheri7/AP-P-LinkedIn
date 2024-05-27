package com.example.server.HttpControllers;

import com.example.server.database_conn.CommentDB;
import com.example.server.database_conn.LikeDB;
import com.example.server.database_conn.PostDB;
import com.example.server.models.Post;

import java.sql.SQLException;

public class PostController extends BaseController {
    private static final PostDB postDB;
    private static final LikeDB likeDB;
    private static final CommentDB commentDB;

    static {
        try {
            postDB = new PostDB();
            likeDB = new LikeDB();
            commentDB = new CommentDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addPost(Post post) throws SQLException {
        postDB.insertData(post);
    }
}
