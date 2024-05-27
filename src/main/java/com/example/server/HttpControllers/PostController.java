package com.example.server.HttpControllers;

import com.example.server.database_conn.CommentDB;
import com.example.server.database_conn.LikeDB;
import com.example.server.database_conn.PostDB;

import java.sql.SQLException;

public class PostController extends BaseController {
    private final PostDB postDB;
    private final LikeDB likeDB;
    private final CommentDB commentDB;

    {
        try {
            postDB = new PostDB();
            likeDB = new LikeDB();
            commentDB = new CommentDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
