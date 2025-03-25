package be.integration.repository;


import be.integration.models.entities.User;

public class DatabaseIUserRepository implements IUserRepository {

    // Inject your JPA Repository or JDBC Template, for example

    @Override
    public User findByUsername(String username) {
        // return userJpaRepository.findByUsername(username);
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public User findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public User findByPartialClientId(String partialClientId) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public void save(User user) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean existsByUsername(String username) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
