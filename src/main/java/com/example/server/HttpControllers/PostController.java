package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.DataValidator.PostValidator;
import com.example.server.database_conn.CommentDB;
import com.example.server.database_conn.LikeDB;
import com.example.server.database_conn.PostDB;
import com.example.server.models.Comment;
import com.example.server.models.Like;
import com.example.server.models.Notification;
import com.example.server.models.Post;
import com.mysql.cj.jdbc.exceptions.NotUpdatable;

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
        if (!PostValidator.isValid(post)) {
            throw new IllegalArgumentException("Invalid title");
        }

        postDB.insertData(post);
        ArrayList<Post> allPosts = postDB.getPosts(post.getAuthor());
        Post addedPost = allPosts.get(allPosts.size() - 1);
        NotificationController.addNotification(new Notification(post.getAuthor(), "Post_Notice", "Added a new Post", addedPost.getId()));
    }

    public static void updatePost(Post post) throws SQLException {
        if (!PostValidator.isValid(post)) {
            throw new IllegalArgumentException("Invalid title");
        }

        postDB.updateData(post);
    }

    public static Post getPost(int id) throws SQLException, NotFoundException {
        Post post = postDB.getPost(id);
        if (post == null) {
            throw new NotFoundException("Post not found");
        }

        return post;
    }

    public static ArrayList<Post> getPosts(String email) throws SQLException {
        return postDB.getPosts(email);
    }

    public static Post getLastPost(String email) throws SQLException {
        return postDB.getLastPost(email);
    }

    public static void deletePost(int id, Post requestPost) throws SQLException, NotFoundException {
        Post postToDelete = getPost(id);
        if (!postToDelete.equals(requestPost)) {
            throw new IllegalAccessError("You are not allowed to delete this post");
        }

        postDB.deleteData(id);
    }

    public static void likePost(Like like) throws SQLException, DuplicateDataException {
        if (likeExists(like)) {
            throw new DuplicateDataException("Liked already");
        }

        likeDB.insertData(like);
        postDB.likePost(like.getPostId());
        NotificationController.addNotification(new Notification(like.getEmail(), "Like_Notice", "Liked the post", like.getPostId()));
    }

    public static void dislikePost(Like like, String email) throws SQLException, NotFoundException {
        if (!likeExists(like)) {
            throw new NotFoundException("You didn't like this post yet");
        }

        likeDB.deleteData(like.getPostId(), email);
        postDB.dislikePost(like.getPostId());
    }

    public static boolean likeExists(Like like) throws SQLException {
        return likeDB.likeExists(like);
    }

    public static ArrayList<Like> getAllLikes(int postId) throws SQLException, NotFoundException {
        Post post = getPost(postId);
        return likeDB.getLikes(postId);
    }

    public static void addCommentToPost(Comment comment) throws SQLException, NotFoundException {
        Post post = getPost(comment.getPostId());

        commentDB.insertData(comment);
        postDB.increaseComment(comment.getPostId());
        NotificationController.addNotification(new Notification(comment.getEmail(), "Comment_Notice", "Added a new Comment", comment.getPostId()));
    }

    public static Comment getComment(int id) throws SQLException, NotFoundException {
        Comment comment = commentDB.getComment(id);
        if (comment == null) {
            throw new NotFoundException("Comment not found");
        }

        return comment;
    }

    public static boolean commentExists(Comment comment) throws SQLException {
        return commentDB.commentExists(comment);
    }

    public static void deleteCommentFromPost(int commentId, String viewerEmail) throws SQLException, NotFoundException {
        Comment comment = getComment(commentId);
        Post post = getPost(comment.getPostId());
        if (!comment.getEmail().equals(viewerEmail)) {
            throw new IllegalAccessError("You are not allowed to delete this comment");
        }

        commentDB.deleteData(commentId);
        postDB.decreaseComment(comment.getPostId());
    }

    public static void updateComment(int commentId, Comment newVersionComment) throws SQLException, NotFoundException {
        Comment comment = getComment(commentId);
        if (!comment.getEmail().equals(newVersionComment.getEmail())) {
            throw new IllegalAccessError("You are not allowed to update this comment");
        }
        Post post = getPost(comment.getPostId());

        comment.setComment(newVersionComment.getComment());
        commentDB.updateData(comment);
    }

    public static ArrayList<Comment> getAllComments(int postId) throws SQLException, NotFoundException {
        Post post = getPost(postId);
        return commentDB.getCommentsByPostId(postId);
    }
}
