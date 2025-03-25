package be.integration.repository;

import be.integration.models.entities.User;

public interface IUserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPartialClientId(String partialClientId);
    void save(User user);
    boolean existsByUsername(String username);
}

