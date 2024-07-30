package com.example.techschool.controller;

import com.example.techschool.dto.CourseDTO;
import com.example.techschool.dto.GenericResponse;
import com.example.techschool.services.CourseService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/course")
public class CourseController {

    private final CourseService courseService;


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public GenericResponse addCourse(@RequestBody CourseDTO courseDTO){
        return new GenericResponse(courseService.addCourse(courseDTO));
    }

    @PutMapping("/{id}")
    public GenericResponse updateCourse(@PathVariable String id,@RequestBody CourseDTO courseDTO){
        return new GenericResponse(courseService.updateCourse(id,courseDTO));
    }

    @GetMapping
    public GenericResponse getAllCourse(){
        return new GenericResponse(courseService.getAllCourse());
    }

    @DeleteMapping("/{id}")
    public GenericResponse deleteCourse(@PathVariable String id){
        return new GenericResponse(courseService.deleteCourse(id));
    }

}
