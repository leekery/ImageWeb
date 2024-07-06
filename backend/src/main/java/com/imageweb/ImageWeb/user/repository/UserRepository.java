package com.imageweb.ImageWeb.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imageweb.ImageWeb.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
}
