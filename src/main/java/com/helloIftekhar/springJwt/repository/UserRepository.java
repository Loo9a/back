package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.User;
import com.helloIftekhar.springJwt.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {



    Optional<UserDto> getUserById(Long userId);
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);
}
