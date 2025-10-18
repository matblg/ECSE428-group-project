package ca.mcgill.ecse428.letterbook.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse428.letterbook.model.User;

public interface UserRepository extends CrudRepository<User, String> {
}
