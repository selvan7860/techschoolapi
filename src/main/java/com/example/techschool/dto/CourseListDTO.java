package com.example.techschool.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CourseListDTO {
    private List<CourseDTO> courses;
}
