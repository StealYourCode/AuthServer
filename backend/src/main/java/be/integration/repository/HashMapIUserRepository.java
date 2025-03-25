package be.integration.repository;


import be.integration.models.entities.User;

import java.util.HashMap;
import java.util.Map;

public class HashMapIUserRepository implements IUserRepository {

    private final Map<String, User> userDatabase = new HashMap<>();

    @Override
    public User findByUsername(String username) {
        return userDatabase.get(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDatabase.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findByPartialClientId(String partialClientId) {
        return userDatabase.values().stream()
                .filter(user -> user.getId().startsWith(partialClientId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(User user) {
        userDatabase.put(user.getUsername(), user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDatabase.containsKey(username);
    }
}
