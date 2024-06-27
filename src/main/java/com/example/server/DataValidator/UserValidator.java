package com.example.server.DataValidator;

import com.example.server.models.User;

public class UserValidator {
    public static boolean nameValidator(String name) {
        return name != null && name.matches("^[a-zA-Z]+$");
    }

    public static boolean emailValidator(String email) {
        return email != null && email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    public static boolean passwordValidator(String password) {
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$") && password.length() >= 8;
    }

    public static boolean isValid(User user) {
        return nameValidator(user.getFirstName()) && nameValidator(user.getLastName())
                && emailValidator(user.getEmail()) && passwordValidator(user.getPassword());
    }

    public static boolean validationWithoutPassword(User user) {
        return nameValidator(user.getFirstName()) && nameValidator(user.getLastName())
                && emailValidator(user.getEmail());
    }
}
