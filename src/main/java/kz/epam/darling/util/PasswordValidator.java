package kz.epam.darling.util;

import java.util.regex.Pattern;

public class PasswordValidator {
    public static boolean isValid(String password) {
        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=*])(?=\\S+$).{6,}$").matcher(password).find();
    }
}
