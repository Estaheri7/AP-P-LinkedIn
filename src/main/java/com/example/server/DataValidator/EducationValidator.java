package com.example.server.DataValidator;

import com.example.server.models.Education;

public class EducationValidator {
    public static boolean schoolNameValidator(String schoolName) {
        return schoolName != null;
    }

    public static boolean fieldValidator(String field) {
        return field != null;
    }

    public static boolean isValid(Education education) {
        return schoolNameValidator(education.getSchoolName()) && fieldValidator(education.getField());
    }
}
