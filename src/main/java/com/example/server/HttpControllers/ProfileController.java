package com.example.server.HttpControllers;

import com.example.server.DataValidator.UserValidator;
import com.example.server.database_conn.ContactDB;
import com.example.server.database_conn.EducationDB;
import com.example.server.database_conn.SkillDB;
import com.example.server.database_conn.UserDB;
import com.example.server.models.User;

import java.sql.SQLException;

public class ProfileController {
    private static final UserDB userDB;
    private static final SkillDB skillDB;
    private static final EducationDB educationDB;
    private static final ContactDB contactDB;

    static {
        try {
            userDB = new UserDB();
            skillDB = new SkillDB();
            educationDB = new EducationDB();
            contactDB = new ContactDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(User user) throws SQLException {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        userDB.updateData(user);
    }
}
