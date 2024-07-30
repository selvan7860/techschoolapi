package com.example.techschool.controller;

import com.example.techschool.dto.CheckInOutDTO;
import com.example.techschool.dto.GenericResponse;
import com.example.techschool.services.CheckInOutService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/checkInOut")
public class CheckInOutController {

    private final CheckInOutService checkInOutService;

    public CheckInOutController(CheckInOutService checkInOutService) {
        this.checkInOutService = checkInOutService;
    }

    @PostMapping("/check-in")
    public GenericResponse CheckIn(@RequestBody CheckInOutDTO checkInOutDTO){
        return new GenericResponse(checkInOutService.addCheckIn(checkInOutDTO));
    }

    @PutMapping("/check-out")
    public GenericResponse checkOut(@RequestBody CheckInOutDTO checkInOutDTO){
        return new GenericResponse(checkInOutService.addCheckOut(checkInOutDTO));
    }
}
