package com.example.server.HttpControllers;

import com.example.server.DataValidator.UserValidator;
import com.example.server.database_conn.ContactDB;
import com.example.server.database_conn.EducationDB;
import com.example.server.database_conn.SkillDB;
import com.example.server.database_conn.UserDB;
import com.example.server.models.Contact;
import com.example.server.models.Education;
import com.example.server.models.Skill;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
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

    public static void addUser(User user) throws SQLException  {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        userDB.insertData(user);
        skillDB.insertData(new Skill(user.getEmail(), "", "", "", "", ""));
        educationDB.insertData(new Education(user.getEmail(), "", "", 0.0, null));
        contactDB.insertData(new Contact(user.getEmail(), "", ""));
    }

    public static User getUser(String email) throws SQLException {
        return userDB.getUser(email);
    }

    public static ArrayList<User> getAllUsers() throws SQLException  {
        return userDB.getAllUsers();
    }

    public static Skill getSkill(String email) throws SQLException {
        return skillDB.getSkill(email);
    }

    public static Education getEducation(String email) throws SQLException {
        return educationDB.getEducation(email);
    }

    public static ArrayList<Education> getAllEducations(String email) throws SQLException  {
        return educationDB.getAllEducations(email);
    }

    public static Contact getContact(String email) throws SQLException {
        return contactDB.getContact(email);
    }
}
