package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;
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

    public static UserProfile getProfile(String email, String viewerEmail) throws SQLException, NotFoundException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        Skill skill = skillDB.getSkill(email);
        Contact contact = contactDB.getContact(email, viewerEmail);
        ArrayList<Education> educations = educationDB.getAllEducations(email);

        return new UserProfile(user, skill, contact, educations);
    }

    public static void updateUser(User user) throws SQLException {
        if (!UserValidator.validationWithoutPassword(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        userDB.updateData(user);
        NotificationController.addNotification(new Notification(user.getEmail(), "intro_update", "Updated their profile information", 0));
    }

    public static void updateSkill(Skill skill) throws SQLException {
        skillDB.updateData(skill);
        NotificationController.addNotification(new Notification(skill.getEmail(), "skill_update", "Updated their skills information", 0));
    }

    public static void addEducation(Education education) throws SQLException {
        if (!EducationValidator.isValid(education)) {
            throw new IllegalArgumentException("SchoolName or Field cannot be empty");
        }

        educationDB.insertData(education);
        NotificationController.addNotification(new Notification(education.getEmail(), "education_update", "Added new education to their profile", 0));
    }

    public static void updateEducation(Education education) throws SQLException {
        if (!EducationValidator.isValid(education)) {
            throw new IllegalArgumentException("SchoolName or Field cannot be empty");
        }

        educationDB.updateData(education);
        NotificationController.addNotification(new Notification(education.getEmail(), "education_update", "Updated their education information", 0));
    }

    public static void updateContact(Contact contact) throws SQLException {
        if (!ContactValidator.isValid(contact)) {
            throw new IllegalArgumentException("view link is null or phone number is not valid");
        }

        contactDB.updateData(contact);
        NotificationController.addNotification(new Notification(contact.getEmail(), "contact_update", "Updated their contact information", 0));
    }

    public static void updateVisibility(String email, String visibility) throws SQLException {
        contactDB.changeVisibility(email, visibility);
        visibility = visibility.replace("_", " ");
        NotificationController.addNotification(new Notification(email, "visibility_update", "Updated their birth date visibility to " + visibility, 0));
    }
}
