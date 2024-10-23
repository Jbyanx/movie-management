package com.bycorp.moviemanagement.services.validator;

import com.bycorp.moviemanagement.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

public class PasswordValidator {

    public static void validatePassword(String password, String confirmPassword) {
        if(!StringUtils.hasText(password) || !StringUtils.hasText(confirmPassword)) {
            throw new IllegalArgumentException("password must contain data");
        }

        if(!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(password, confirmPassword,"Passwords do not match");
        }

        if(!containsNumber(password)){
            throw new InvalidPasswordException(password, "Password must contains at least one number");
        }

        if(!containsUperCase(password)){
            throw new InvalidPasswordException(password, "Password must contains at least one uppercase letter");
        }

        if(!containsLowerCase(password)){
            throw new InvalidPasswordException(password, "Password must contains at least one lowercase letter");
        }

        if(!containsSpecialCharacter(password)){
            throw new InvalidPasswordException(password, "Password must contains at least one special character");
        }
    }

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }
}
