package com.springSecurity.springJWT.controllers.users;

import com.springSecurity.springJWT.service.users.UsersService;
import com.springSecurity.springJWT.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers () {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.getAllUsers());
    }

    @PatchMapping(path = "/user/{id}/update")
    public ResponseEntity<User> updateUser (@PathVariable(name = "id") UUID userId, @RequestBody Map<String, String> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(userId, updates));
    }

    @DeleteMapping(path = "/user/{id}/delete")
    public ResponseEntity<Map<String, String>> deleteUser (@PathVariable(name = "id") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.deleteUser(userId));
    }
}
