package com.example.server.HttpControllers;

import com.example.server.database_conn.UserDB;
import com.example.server.models.User;
import com.example.server.utils.JwtUtil;


public class AuthController extends BaseController {
    public static String loginUser(String email, String password) throws Exception {
        User user = userDB.getUser(email);

        if (user != null && user.getPassword().equals(password)) {
            return JwtUtil.generateToken(email);
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public static boolean isAuthenticated(String token) throws Exception {
        return JwtUtil.validateToken(token);
    }
}
