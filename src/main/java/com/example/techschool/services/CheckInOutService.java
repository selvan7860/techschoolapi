package com.example.techschool.services;

import com.example.techschool.dao.CheckInOut;
import com.example.techschool.dao.User;
import com.example.techschool.dto.CheckInOutDTO;
import com.example.techschool.exception.CustomException;
import com.example.techschool.repository.CheckInOutRepository;
import com.example.techschool.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CheckInOutService {

    private final UserRepository userRepository;
    private final CheckInOutRepository checkInOutRepository;

    public CheckInOutService(UserRepository userRepository, CheckInOutRepository checkInOutRepository) {
        this.userRepository = userRepository;
        this.checkInOutRepository = checkInOutRepository;
    }

    public CheckInOutDTO addCheckIn(CheckInOutDTO checkInOutDTO) {
        LocalDate today = LocalDate.now();
        String userId = checkInOutDTO.getUserId();
        Optional<CheckInOut> existingCheckIn = checkInOutRepository.findByUserIdAndDate(userId, today);

        if(existingCheckIn.isPresent()){
            throw new CustomException("Already checked in for today", HttpStatus.BAD_REQUEST);
        }
        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setDate(today);
        checkInOut.setCheckIn(ZonedDateTime.now());
        checkInOut.setStatus(true);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            checkInOut.setUser(user.get());
        }else {
            throw new CustomException("User Not Present", HttpStatus.BAD_REQUEST);
        }
        CheckInOut savedCheckInOut = checkInOutRepository.save(checkInOut);
        return convertDTO(savedCheckInOut);
    }

    private CheckInOutDTO convertDTO(CheckInOut savedCheckInOut) {
        CheckInOutDTO checkInOutDTO = new CheckInOutDTO();
        checkInOutDTO.setId(savedCheckInOut.getId());
        checkInOutDTO.setDate(String.valueOf(savedCheckInOut.getDate()));
        checkInOutDTO.setCheckIn(savedCheckInOut.getCheckIn());
        checkInOutDTO.setCheckOut(savedCheckInOut.getCheckOut());
        checkInOutDTO.setStatus(savedCheckInOut.isStatus());
        checkInOutDTO.setUserId(savedCheckInOut.getUser().getId());
        return checkInOutDTO;
    }

    public CheckInOutDTO addCheckOut(CheckInOutDTO checkInOutDTO) {
        LocalDate today = LocalDate.now();
        String userId = checkInOutDTO.getUserId();
        Optional<CheckInOut> existingCheckIn = checkInOutRepository.findByUserIdAndDate(userId, today);
        if (existingCheckIn.isPresent()) {
            CheckInOut checkInOut = existingCheckIn.get();
            if (checkInOut.getCheckOut() != null) {
                throw new CustomException("Already checked out for today", HttpStatus.BAD_REQUEST);
            }
            if (checkInOut.getCheckIn() == null) {
                throw new CustomException("Check-in not performed for today", HttpStatus.BAD_REQUEST);
            }
            // Update existing record
            checkInOut.setCheckOut(ZonedDateTime.now());
            checkInOut.setStatus(false); // Status false for check-out
            CheckInOut savedCheckInOut = checkInOutRepository.save(checkInOut);
            return convertDTO(savedCheckInOut);
        } else {
            throw new CustomException("No check-in record found for today", HttpStatus.BAD_REQUEST);
        }
    }
}
