package com.userservice.controller;

import com.userservice.entity.Consumer;
import com.userservice.exception.UserNotFoundException;
import com.userservice.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<Consumer> users = consumerService.getAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllUsers() {
        String result = consumerService.deleteAll();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Consumer user = consumerService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId) {
        consumerService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
