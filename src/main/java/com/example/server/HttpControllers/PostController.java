package com.example.server.HttpControllers;

import com.example.server.database_conn.CommentDB;
import com.example.server.database_conn.LikeDB;
import com.example.server.database_conn.PostDB;
import com.example.server.models.Comment;
import com.example.server.models.Like;
import com.example.server.models.Post;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Post> getAllPosts() throws SQLException {
        return postDB.getAllPosts();
    }

    public static void addPost(Post post) throws SQLException {
        postDB.insertData(post);
    }

    public static void updatePost(Post post) throws SQLException {
        postDB.updateData(post);
    }

    public static Post getPost(int id) throws SQLException {
        return postDB.getPost(id);
    }

    public static ArrayList<Post> getPosts(String email) throws SQLException {
        return postDB.getPosts(email);
    }

    public static void deletePost(int id) throws SQLException {
        postDB.deleteData(id);
    }

    public static void likePost(Like like) throws SQLException {
        likeDB.insertData(like);
        postDB.likePost(like.getPostId());
    }

    public static void dislikePost(int postId) throws SQLException {
        likeDB.deleteData(postId);
        postDB.dislikePost(postId);
    }

    public static boolean likeExists(Like like) throws SQLException {
        return likeDB.likeExists(like);
    }

    public static ArrayList<Like> getAllLikes(int postId) throws SQLException {
        return likeDB.getLikes(postId);
    }

    public static void addCommentToPost(Comment comment) throws SQLException {
        commentDB.insertData(comment);
        postDB.increaseComment(comment.getPostId());
    }

    public static Comment getComment(int id) throws SQLException {
        return commentDB.getComment(id);
    }

    public static boolean commentExists(Comment comment) throws SQLException {
        return commentDB.commentExists(comment);
    }

    public static void deleteCommentFromPost(int commentId, int postId) throws SQLException {
        commentDB.deleteData(commentId);
        postDB.decreaseComment(postId);
    }
}
