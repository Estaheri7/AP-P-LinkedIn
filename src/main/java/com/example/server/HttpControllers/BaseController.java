package com.example.server.HttpControllers;

import com.example.server.database_conn.*;

import java.sql.SQLException;

public class BaseController {
    protected static UserDB userDB;
    protected static SkillDB skillDB;
    protected static EducationDB educationDB;
    protected static ContactDB contactDB;

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
}
