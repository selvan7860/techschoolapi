package com.example.techschool.services;


import com.example.techschool.dao.User;
import com.example.techschool.dto.UserDTO;
import com.example.techschool.exception.CustomException;
import com.example.techschool.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO addUser(UserDTO userDTO){
        validationUser(userDTO);
        User saveUser = new User();
        saveUser.setName(userDTO.getName());
        saveUser.setRole(userDTO.getRole());
        saveUser.setEmail(userDTO.getEmail());

        User user = userRepository.save(saveUser);
        return convertDTO(user);
    }

    private void validationUser(UserDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new CustomException("Email Already Exists", HttpStatus.BAD_REQUEST);
        }
        if(userDTO.getEmail().isEmpty() || userDTO.getEmail().trim().isEmpty()){
            throw new CustomException("Fill Your Email In Email Field", HttpStatus.BAD_REQUEST);
        }
        if(userDTO.getName().isEmpty() || userDTO.getName().trim().isEmpty()){
            throw new CustomException("Fill Your Name In Name Field", HttpStatus.BAD_REQUEST);
        }
        if(!isValidEmail(userDTO.getEmail())){
            throw new CustomException("Enter Valid Email", HttpStatus.BAD_REQUEST);
        }
        if(userDTO.getRole().isEmpty() || userDTO.getRole().trim().isEmpty()){
            throw  new CustomException("Fill Your Role In Role Field", HttpStatus.BAD_REQUEST);
        }
    }

    // Check if an email has a valid format using a basic regular expression
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private UserDTO convertDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
