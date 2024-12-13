package com.example.buoi2.repositories;

import com.example.buoi2.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
