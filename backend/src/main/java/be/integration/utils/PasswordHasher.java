package be.integration.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    private static final int WORKLOAD = 12; // BCrypt work factor
    /**
     * Hash a password using BCrypt
     * @param password The plain password to hash
     * @return A hashed password string containing the salt, cost, and hash
     */
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(WORKLOAD, password.toCharArray());
    }
    // IF TIME REHASH DETECTION
}