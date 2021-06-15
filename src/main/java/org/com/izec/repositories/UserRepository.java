package org.com.izec.repositories;

import org.com.izec.domeins.User;
import org.springframework.data.repository.CrudRepository;



// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    User findByActivationCode(String code);
}
