package ca.mcgill.ecse428.letterbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.letterbook.model.User;

public interface UserRepository extends CrudRepository<User, String> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmail(String email);
    public List<User> findAll();
}
