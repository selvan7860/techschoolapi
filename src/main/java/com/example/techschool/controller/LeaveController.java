package com.example.techschool.controller;


import com.example.techschool.dto.GenericResponse;
import com.example.techschool.dto.LeaveDTO;
import com.example.techschool.services.LeaveService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/leave")
public class LeaveController {

    private  final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping
    public GenericResponse createLeave(@RequestBody LeaveDTO leaveDTO){
        return  new GenericResponse(leaveService.addLeave(leaveDTO));
    }

//    @GetMapping
//    public GenericResponse getAllLeave(@RequestBody LeaveDTO leaveDTO){
//        return new GenericResponse(leaveService.getAllLeave());
//    }

}
