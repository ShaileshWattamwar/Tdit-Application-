package com.userservice.controller;

import com.userservice.dto.ConsumerDTO;
import com.userservice.entity.Consumer;
import com.userservice.repository.ConsumerRepository;
import com.userservice.service.ConsumerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class ConsumerController {
    //pr
    @Autowired
    private ConsumerService service;

    @Autowired
    private ConsumerRepository repository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ConsumerDTO user) {
        try {

            String isSaved = service.saveNewUser(user);
            if (isSaved.equals("User registered successfully")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(isSaved);
            } else if (isSaved.equals("Email/Username already exists")){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(isSaved);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isSaved);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUserDetails(
            @PathVariable Long userId,
            @RequestBody ConsumerDTO userUpdateDTO) {
        String result = service.updateUserDetails(userId, userUpdateDTO);
        if (result.equals("User details updated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
    }
//    @DeleteMapping("/delete/{userId}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
//        service.deleteById(userId);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        ConsumerDTO userData = service.getCurrentUser(username);
        return ResponseEntity.ok(userData);
    }
}
