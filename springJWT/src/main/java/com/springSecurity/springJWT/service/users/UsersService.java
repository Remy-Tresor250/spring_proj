package com.springSecurity.springJWT.service.users;

import com.springSecurity.springJWT.model.user.User;
import com.springSecurity.springJWT.model.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().peek(User::removePassword).collect(Collectors.toList());
    }

    @Transactional
    public User updateUser(UUID userId, Map<String, String> updates) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
                    return new IllegalStateException("User with id " + userId + " wasn't found!");
                }
        );
        String email = updates.get("email");
        String password = updates.get("password");
        String firstName = updates.get("firstName");
        String lastName = updates.get("lastName");

        if(email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)){
            user.setEmail(email);
        }

        if (password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)){
            user.setPassword(password);
        }

        if (firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)){
            user.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)){
            user.setLastName(lastName);
        }

        return user;
    }

    public Map<String, String> deleteUser(UUID userId) {

        boolean userExists = userRepository.existsById(userId);

        if (!userExists) throw new IllegalStateException("User with "+ userId +" not found!");

        userRepository.deleteById(userId);
        return new HashMap<>() {{
            put("message", "The User is deleted successfully!");
        }};
    }
}
