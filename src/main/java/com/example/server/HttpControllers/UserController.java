package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.AccountExistsException;
import com.example.server.CustomExceptions.NotFoundException;
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

public class UserController extends BaseController {

    public static void addUser(User user) throws SQLException, AccountExistsException {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        if (userDB.getUser(user.getEmail()) != null) {
            throw new AccountExistsException("Email already exists");
        }

        userDB.insertData(user);
        skillDB.insertData(new Skill(user.getEmail(), "", "", "", "", ""));
        educationDB.insertData(new Education(user.getEmail(), "", "", 0.0, null));
        contactDB.insertData(new Contact(user.getEmail(), "", ""));
    }

    public static User getUser(String email) throws SQLException, NotFoundException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public static ArrayList<User> getAllUsers() throws SQLException  {
        return userDB.getAllUsers();
    }

    public static Skill getSkill(String email) throws SQLException, NotFoundException {
        Skill skill = skillDB.getSkill(email);
        if (skill == null) {
            throw new NotFoundException("Skill not found");
        }

        return skill;
    }

    public static Education getEducation(String email) throws SQLException, NotFoundException {
        Education education = educationDB.getEducation(email);
        if (education == null) {
            throw new NotFoundException("Education not found");
        }

        return education;
    }

    public static ArrayList<Education> getAllEducations(String email) throws SQLException  {
        return educationDB.getAllEducations(email);
    }

    public static Contact getContact(String email, String viewerEmail) throws SQLException, NotFoundException {
        Contact contact = contactDB.getContact(email, viewerEmail);
        if (contact == null) {
            throw new NotFoundException("Contact not found");
        }

        return contact;
    }
}
