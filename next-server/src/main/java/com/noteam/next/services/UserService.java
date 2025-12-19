package com.noteam.next.services;

import com.noteam.next.models.User;
import com.noteam.next.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> findUserById(Long id) {
        return repo.findUserById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return repo.findUserByEmail(email);
    }

    public User createUser(User user) {
        return repo.createUser(user);
    }
}
