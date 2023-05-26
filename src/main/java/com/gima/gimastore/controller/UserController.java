package com.gima.gimastore.controller;

import com.gima.gimastore.model.UserDTO;
import com.gima.gimastore.repository.UserRepository;
import com.gima.gimastore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")

@CrossOrigin(origins = "*")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
    	this. userService=userService;
    }
    @PostMapping
    public ResponseEntity<?> insertUser(@RequestBody UserDTO dto) {
        try {
            userService.addUser(dto);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("error in save", HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO dto) {
        try {
            return new ResponseEntity<>(userService.updateUser(dto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("error in update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO dto) {
        try {
            return new ResponseEntity<>(userService.updateUser(dto), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("error in delete", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("error in fetching data", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsersById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("error in fetching data", HttpStatus.BAD_REQUEST);
        }
    }
}
