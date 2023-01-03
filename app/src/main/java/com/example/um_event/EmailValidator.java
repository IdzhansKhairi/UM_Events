package com.example.um_event;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@siswa.um.edu.my$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).find();
    }
}

