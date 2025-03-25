package be.integration.models.entities;

import be.integration.utils.PasswordChecker;
import be.integration.utils.PasswordHasher;

import java.util.UUID;

/**
 * Represents a user entity within the system.
 * Passwords are hashed upon user creation and verified during authentication.
 * The unique identifier for each user is automatically generated as a UUID.
 */
public class User {

    private final String id;
    private String username;
    private final String hashedPassword;
    private String role;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String address;
    private String city;

    /**
     * Constructs a new {@code User} with the provided username, raw (plain-text) password,
     * role, and email. The password is automatically hashed before storage, and a unique
     * identifier (UUID) is generated for the user.
     *
     * @param username    The username chosen by the user.
     * @param rawPassword The clear (plain-text) password provided by the user.
     * @param email       The email address of the user.
     */
    public User(String username, String rawPassword, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.hashedPassword = hashPassword(rawPassword);
        this.email = email;
    }

    /**
     * Hashes the provided raw password using the {@link PasswordHasher} utility.
     *
     * @param rawPassword The plain-text password to hash.
     * @return The hashed password.
     */
    private String hashPassword(String rawPassword) {
        return PasswordHasher.hashPassword(rawPassword);
    }

    /**
     * Verifies whether the provided plain-text password matches the stored hashed password.
     *
     * @param rawPassword The plain-text password to verify.
     * @return {@code true} if the provided password matches the stored hash; {@code false} otherwise.
     */
    public boolean checkPassword(String rawPassword) {
        return PasswordChecker.checkPasswordHash(rawPassword, this.hashedPassword);
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return The UUID representing the user's unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the hashed password of the user.
     *
     * @return The hashed password stored in the user object.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getEmail(){ return email; }

    public String getUsername() { return username; }

}
