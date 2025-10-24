package ca.mcgill.ecse428.letterbook.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ca.mcgill.ecse428.letterbook.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);
}
