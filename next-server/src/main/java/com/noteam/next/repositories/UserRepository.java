package com.noteam.next.repositories;

import com.noteam.next.models.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final ArrayList<User> data = new ArrayList<User>(List.of(new User[]{
            new User(1, "test@test.com", BCrypt.hashpw("12345678", BCrypt.gensalt())),
            new User(2, "test2@test.com", BCrypt.hashpw("12345678", BCrypt.gensalt())),
    }));

    public ArrayList<User> getUsers() {
        return data;
    }

    public User createUser(@NotNull User user) {
        long lastId = data.getLast().getId();
        user.setId(lastId + 1);
        data.add(user);
        return user;
    }

    public Optional<User> findUserById(Long id) {
        Optional<User> result = Optional.empty();
        for (User user : data) {
            if (Objects.equals(user.getId(), id)) {
                result = Optional.of(user);
                break;
            }
        }
        return result;
    }

    public Optional<User> findUserByEmail(String email) {
        Optional<User> result = Optional.empty();
        for (User user : data) {
            if (Objects.equals(user.getEmail(), email)) {
                result = Optional.of(user);
                break;
            }
        }
        return result;
    }
}