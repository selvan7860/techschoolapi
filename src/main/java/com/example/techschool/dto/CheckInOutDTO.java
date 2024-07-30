package com.example.techschool.dto;


import com.example.techschool.dao.LeaveStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class CheckInOutDTO {

    private String id;
    private String date;
    private ZonedDateTime checkIn;
    private ZonedDateTime checkOut;
    private boolean status;
    private String userId;
}
