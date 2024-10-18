package com.mantisa.inventory.controller;

import com.mantisa.inventory.model.UserEntity;
import com.mantisa.inventory.model.dto.AuthCreateUserRequestDTO;
import com.mantisa.inventory.service.implementation.UserServiceImpl;
import com.mantisa.inventory.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody AuthCreateUserRequestDTO user) {
        try {
            UserEntity userCreated = userService.createUser(user);
            return ResponseObject.build(true, HttpStatus.CREATED, "User created successfully", userCreated);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAllUsers() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Users retrieved successfully", userService.getAllUsers());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseObject> deleteUser(@Param("id") Long id) {
        try {
            boolean deleted = userService.deleteUserById(id);
            if (deleted) {
                return ResponseObject.build(true, HttpStatus.OK, "User deleted successfully", null);
            } else {
                return ResponseObject.build(false, HttpStatus.NOT_FOUND, "User not found", null);
            }
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

}
