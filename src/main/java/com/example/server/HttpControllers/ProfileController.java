package com.example.server.HttpControllers;

import com.example.server.DataValidator.ContactValidator;
import com.example.server.DataValidator.EducationValidator;
import com.example.server.DataValidator.UserValidator;
import com.example.server.database_conn.ContactDB;
import com.example.server.database_conn.EducationDB;
import com.example.server.database_conn.SkillDB;
import com.example.server.database_conn.UserDB;
import com.example.server.models.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileController extends BaseController {

    public static UserProfile getProfile(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            return null;
        }

        Skill skill = skillDB.getSkill(email);
        Contact contact = contactDB.getContact(email);
        ArrayList<Education> educations = educationDB.getAllEducations(email);

        return new UserProfile(user, skill, contact, educations);
    }

    public static void updateUser(User user) throws SQLException {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        userDB.updateData(user);
    }

    public static void updateSkill(Skill skill) throws SQLException {
        skillDB.updateData(skill);
    }

    public static void addEducation(Education education) throws SQLException {
        if (!EducationValidator.isValid(education)) {
            throw new IllegalArgumentException("School name or Field cannot be empty");
        }

        educationDB.insertData(education);
    }

    public static void updateEducation(Education education) throws SQLException {
        if (!EducationValidator.isValid(education)) {
            throw new IllegalArgumentException("School name or Field cannot be empty");
        }

        educationDB.updateData(education);
    }

    public static void updateContact(Contact contact) throws SQLException {
        if (!ContactValidator.isValid(contact)) {
            throw new IllegalArgumentException("view link is null or phone number is not valid");
        }

        contactDB.updateData(contact);
    }
}
