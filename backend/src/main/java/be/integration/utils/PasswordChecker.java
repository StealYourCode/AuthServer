package be.integration.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordChecker {
    /**
     * Verify if password respect strength requirements
     * @param password The plain password to check
     * @return True if the password respect all condition, false otherwise
     */
    public static boolean isPasswordStrong(String password) {
        if (password.length() < 12) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[a-z].*")) return false;
        if (!password.matches(".*\\d.*")) return false;
        if (!password.matches(".*[@#$%^&+=!].*")) return false;
        return true;
    }

    /**
     * Verify a password against a BCrypt hashed password
     * @param password The plain password to check
     * @param hashedPassword The hashed password from storage
     * @return True if the password matches the hash, false otherwise
     */
    public static boolean checkPasswordHash(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }

}