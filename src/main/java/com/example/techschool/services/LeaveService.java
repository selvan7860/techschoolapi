package com.example.techschool.services;

import com.example.techschool.dao.LeaveManagement;
import com.example.techschool.dao.LeaveStatus;
import com.example.techschool.dao.User;
import com.example.techschool.dto.LeaveDTO;
import com.example.techschool.dto.LeaveManagementDTO;
import com.example.techschool.exception.CustomException;
import com.example.techschool.repository.LeaveRepository;
import com.example.techschool.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;

    public LeaveService(LeaveRepository leaveRepository, UserRepository userRepository) {
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;
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
        saveLeaveManagement.setStatus(LeaveStatus.valueOf("PENDING"));
        Optional<User> user = userRepository.findById(leaveDTO.getUserId());
        if(user.isPresent()){
            saveLeaveManagement.setUser(user.get());
        }
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
        if( leaveDTO.getFrom_date().isEmpty()){
            throw new CustomException("Please Enter From Date", HttpStatus.BAD_REQUEST);
        }
        if(leaveDTO.getTo_date().isEmpty()){
            throw new CustomException("Please Enter To Date", HttpStatus.BAD_REQUEST);
        }
        if(leaveDTO.getReason().trim().isEmpty()){
            throw new CustomException("Please Enter Reason For Leave", HttpStatus.BAD_REQUEST);
        }
    }

    private LeaveDTO convertDTO(LeaveManagement leaveManagement){
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setId(leaveManagement.getId());
        leaveDTO.setFrom_date(String.valueOf(leaveManagement.getFrom_date()));
        leaveDTO.setTo_date(String.valueOf(leaveManagement.getTo_date()));
        leaveDTO.setReason(leaveManagement.getReason());
        leaveDTO.setStatus(leaveManagement.getStatus());
        leaveDTO.setUserId(leaveManagement.getUser().getId());
        return leaveDTO;
    }

    public LeaveDTO updateLeave(String id, LeaveDTO leaveDTO){
        Optional<LeaveManagement> leaveManagement = leaveRepository.findByIdAndUserId(id, leaveDTO.getUserId());
        System.out.println(leaveManagement);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fromDate = LocalDate.parse(leaveDTO.getFrom_date(), dateTimeFormatter);
        LocalDate toDate = LocalDate.parse(leaveDTO.getTo_date(), dateTimeFormatter);

        if (!leaveManagement.isPresent()){
            throw new CustomException("Leave Not Found", HttpStatus.BAD_REQUEST);
        }

        LeaveManagement leave = leaveManagement.get();
        leave.setFrom_date(fromDate);
        leave.setTo_date(toDate);
        leave.setReason(leaveDTO.getReason());
        leave.setStatus(leaveDTO.getStatus());
        LeaveManagement update = leaveRepository.save(leave);

        return convertDTO(update);
    }

    public LeaveManagement deleteLeave(String id) {
        Optional<LeaveManagement> leaveManagement = leaveRepository.findById(id);
        if(!leaveManagement.isPresent()){
            throw new CustomException("Leave Not Found", HttpStatus.BAD_REQUEST);
        }
        leaveRepository.deleteById(id);

        throw new CustomException("Leave Deleted Successfully", HttpStatus.BAD_REQUEST);
    }

    public LeaveManagementDTO getAllLeave(String id) {
        List<LeaveManagement> leaveManagements = leaveRepository.findAllByUserId(id);
        List<LeaveDTO> leaveDTOs = convertToDTOs(leaveManagements);
        LeaveManagementDTO leaveManagement = new LeaveManagementDTO();
        leaveManagement.setLeaves(leaveDTOs);
        return leaveManagement;
    }

    private List<LeaveDTO> convertToDTOs(List<LeaveManagement> leaveManagements) {
        return leaveManagements.stream().map(this::convertToLeaveDTO).collect(Collectors.toList());
    }

    private LeaveDTO convertToLeaveDTO(LeaveManagement leave) {
        LeaveDTO leaveDTO = new LeaveDTO();
        leaveDTO.setId(leave.getId());
        leaveDTO.setUserId(leave.getUser().getId());
        leaveDTO.setFrom_date(String.valueOf(leave.getFrom_date()));
        leaveDTO.setTo_date(String.valueOf(leave.getTo_date()));
        leaveDTO.setReason(leave.getReason());
        leaveDTO.setStatus(leave.getStatus());
        return leaveDTO;
    }
}
