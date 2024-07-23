package com.example.techschool.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class LeaveDTO {
    private String id;
    private String from_date;
    private String to_date;
    private String reason;
}
