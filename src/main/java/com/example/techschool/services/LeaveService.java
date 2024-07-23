package com.example.techschool.services;

import com.example.techschool.dao.LeaveManagement;
import com.example.techschool.dto.LeaveDTO;
import com.example.techschool.exception.CustomException;
import com.example.techschool.repository.LeaveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public LeaveDTO addLeave(LeaveDTO leaveDTO){
        validateDateCalculation(leaveDTO);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDate = LocalDate.parse(leaveDTO.getFrom_date(), dateTimeFormatter);
        LocalDate toDate = LocalDate.parse(leaveDTO.getTo_date(), dateTimeFormatter);
        LeaveManagement saveLeaveManagement = new LeaveManagement();
        saveLeaveManagement.setFrom_date(fromDate);
        saveLeaveManagement.setTo_date(toDate);
        saveLeaveManagement.setReason(leaveDTO.getReason());
        LeaveManagement leaveManagement = leaveRepository.save(saveLeaveManagement);
        return convertDTO(leaveManagement);
    }




    private void validateDateCalculation(LeaveDTO leaveDTO) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDate = LocalDate.parse(leaveDTO.getFrom_date(), dateTimeFormatter);
        LocalDate toDate = LocalDate.parse(leaveDTO.getTo_date(), dateTimeFormatter);

        if (fromDate.isAfter(toDate) || fromDate.isEqual(toDate)) {
            throw new CustomException("From Date must be before To Date", HttpStatus.BAD_REQUEST);
        }
    }

    private LeaveDTO convertDTO(LeaveManagement leaveManagement){
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setId(leaveManagement.getId());
        leaveDTO.setFrom_date(String.valueOf(leaveManagement.getFrom_date()));
        leaveDTO.setTo_date(String.valueOf(leaveManagement.getTo_date()));
        leaveDTO.setReason(leaveManagement.getReason());
        return leaveDTO;
    }

}
