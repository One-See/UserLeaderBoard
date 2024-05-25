package com.crio.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.crio.board.dto.CreateUserDTO;
import com.crio.board.dto.UserScoreDTO;
import com.crio.board.exceptions.BadgeLimitExceededException;
import com.crio.board.exceptions.InvalidUserException;
import com.crio.board.exceptions.NonUniqueUserException;
import com.crio.board.model.User;
import com.crio.board.repository.UserRepository;
import com.crio.board.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BoardController {

    private final UserService userService;

    public BoardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Object> fetchAllUsers(@RequestParam(required = false) String userId) {
        if (userId != null) {
            try {
                User user = this.userService.findUser(userId);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (InvalidUserException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(this.userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<String> postUser(@Valid @RequestBody CreateUserDTO userData) {
        try {
            this.userService.addUser(userData);
        } catch (NonUniqueUserException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User is created", HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUserScore(@Valid @RequestBody UserScoreDTO userScoreDTO) {
        try {
            this.userService.updateUserScore(userScoreDTO);
        } catch (InvalidUserException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadgeLimitExceededException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User score is updated", HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUser(@RequestParam(required = true) String userId) {
        if (userId != null) {
            try {
                this.userService.deleteUser(userId);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } catch (InvalidUserException exception) {
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("Please provide the userId", HttpStatus.BAD_REQUEST);
    }

}
