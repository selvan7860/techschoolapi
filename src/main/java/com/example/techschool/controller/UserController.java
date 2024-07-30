package com.example.techschool.controller;


import com.example.techschool.dto.GenericResponse;
import com.example.techschool.dto.UserDTO;
import com.example.techschool.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public GenericResponse createUser(@RequestBody UserDTO userDTO){
        return  new GenericResponse(userService.addUser(userDTO));
    }
}
