package be.integration.services;

import be.integration.models.entities.User;
import be.integration.repository.IUserRepository;
import be.integration.utils.PasswordChecker;


public class UserServices {

    private final IUserRepository IUserRepository;
    private final TokenServices tokenServices;

    public UserServices(IUserRepository IUserRepository, TokenServices tokenServices) {
        this.IUserRepository = IUserRepository;
        this.tokenServices = tokenServices;
    }

    public User registerUser(String username, String password, String email) {
        if (IUserRepository.existsByUsername(username)) {
            System.out.println("[UserServices] Username already exists: " + username);
            return null;
        }

        if (!PasswordChecker.isPasswordStrong(password)) {
            System.out.println("[UserServices] Weak password provided for: " + username);
            return null;
        }

        User newUser = new User(username, password, email);
        IUserRepository.save(newUser);

        System.out.println("[UserServices] User registered successfully: " + username);
        return newUser;
    }

    public String loginUserByEmailOrClientId(String identifier, String password) {
        User user;

        if (isEmail(identifier)) {
            user = IUserRepository.findByEmail(identifier);
        } else if (isPartialClientId(identifier)) {
            user = IUserRepository.findByPartialClientId(identifier);
        } else {
            System.out.println("[UserServices] Invalid identifier format: " + identifier);
            return null;
        }

        if (user == null) {
            System.out.println("[UserServices] User not found for identifier: " + identifier);
            return null;
        }

        if (!user.checkPassword(password)) {
            System.out.println("[UserServices] Incorrect password for user: " + user.getUsername());
            return null;
        }

        String token = tokenServices.generateToken(user.getId());
        System.out.println("[UserServices] Login successful, Token issued for user: " + user.getUsername());

        return token;
    }

    private boolean isEmail(String identifier) {
        return identifier != null && identifier.contains("@");
    }

    private boolean isPartialClientId(String identifier) {
        return identifier != null && identifier.length() == 4 && identifier.matches("[a-zA-Z0-9]+");
    }
}
