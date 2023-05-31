package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder encoder;
    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder encoder) { this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Override
    public List<User> allUsers() {
        return userDAO.findAll();
    }

    @Transactional(readOnly = false)
    @Override
    public void add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(long id) {
        userDAO.deleteById(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void edit(User user, long id) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.save(user);
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userDAO.findById(id);
    }

    @Transactional
    @Override   // from UserDetailsService
    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .disabled(false)
                .build();
    }
}
