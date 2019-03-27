package kz.epam.darling.util;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static boolean isValid(String email) {
        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).find();
    }
}
