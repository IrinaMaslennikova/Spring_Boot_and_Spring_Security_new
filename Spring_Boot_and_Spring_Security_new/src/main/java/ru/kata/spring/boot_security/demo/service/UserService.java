package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService{
    List<User> allUsers();

    void add(User user);

    void delete(long id);

    void edit(User user, long id);

    Optional<User> getUserById(long id);

    // from UserDetailsService
    User findUserByUsername(String username);
}
