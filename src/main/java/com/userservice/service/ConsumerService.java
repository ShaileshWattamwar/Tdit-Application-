package com.userservice.service;

import com.userservice.dto.ConsumerDTO;
import com.userservice.entity.Consumer;
import com.userservice.enums.Role;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.ConsumerRepository;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
public class ConsumerService {
    //pr
           @Autowired
           private ConsumerRepository repository;
           private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    public String saveNewUser(ConsumerDTO user1) {
        try {
            Consumer user = new Consumer();
            user.setUserName(user1.getUserName());
            user.setPassword(passwordEncoder.encode(user1.getPassword()));
            user.setEmail(user1.getEmail());
            user.setRoles(user1.getRole());
//            user.setRoles(Arrays.asList("USER"));
            repository.save(user);
            return "User registered successfully";
        } catch (DataIntegrityViolationException e) {
             return "Email/Username already exists";
        } catch (Exception e) {
            return "Error: " +e.getMessage();
        }
    }


    public String updateUserDetails(Long userId, ConsumerDTO consumerDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Consumer currentUser = repository.findByUserName(currentUsername);

        // Authorization check
        if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(userId)) {
            return "Unauthorized: You can only update your own account";
        }
        Consumer user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update username (if provided)
        if (consumerDTO.getUserName() != null) {
            if (repository.existsByUserName(consumerDTO.getUserName())) {
                return "Username already exists";
            }
            user.setUserName(consumerDTO.getUserName());
        }

        // Update email (if provided)
        if (consumerDTO.getEmail() != null) {
            if (repository.existsByEmail(consumerDTO.getEmail())) {
                return "Email already exists";
            }
            user.setEmail(consumerDTO.getEmail());
        }

        // Update password (if provided)
        if (consumerDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(consumerDTO.getPassword()));
        }

        repository.save(user);
        return "User details updated successfully";
    }
    public void saveAdmin(Consumer user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRole());
        repository.save(user);
    }



    public List<Consumer> getAll() {
        return repository.findAll();
    }

    public Optional<Consumer> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
        repository.deleteById(id);
    }

    public Consumer findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public String deleteAll(){
               repository.deleteAll();
               return "All Users are deleted succesfully";
    }

    public ConsumerDTO getCurrentUser(String username) {
        Consumer user = repository.findByUserName(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return mapToConsumerResponseDTO(user);
    }

    private ConsumerDTO mapToConsumerResponseDTO(Consumer user) {
        ConsumerDTO dto = new ConsumerDTO();
//        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }
}
