package com.example.techschool.services;


import com.example.techschool.dao.Course;
import com.example.techschool.dto.CourseDTO;
import com.example.techschool.dto.CourseListDTO;
import com.example.techschool.exception.CustomException;
import com.example.techschool.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private  final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseDTO addCourse(CourseDTO courseDTO){
        Course course = new Course();
        course.setName(courseDTO.getName());
        Course courses = courseRepository.save(course);
        return convertDTO(courses);
    }

    private CourseDTO convertDTO(Course course){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        return courseDTO;
    }

    public CourseDTO updateCourse(String id, CourseDTO courseDTO){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            Course courses = course.get();
            courses.setName(courseDTO.getName());
            Course saveCourse = courseRepository.save(courses);
            return convertDTO(saveCourse);
        }
        else {
            throw new CustomException("Course Not Found", HttpStatus.BAD_REQUEST);
        }
    }

    public CourseListDTO getAllCourse(){
        List<Course> courseList = courseRepository.findAll();
        List<CourseDTO> courseDTOS = convertDTOs(courseList);
        CourseListDTO courseListDTO = new CourseListDTO();
        courseListDTO.setCourses(courseDTOS);
        return courseListDTO;
    }

    private List<CourseDTO> convertDTOs(List<Course> courseList) {
        return courseList.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public Course deleteCourse(String id){
        Optional<Course> course = courseRepository.findById(id);
        if(course.isPresent()){
            courseRepository.deleteById(id);
        }
        else {
            throw new CustomException("Course Not Found", HttpStatus.BAD_REQUEST);
        }
        throw new CustomException("Course Deleted Successfully", HttpStatus.BAD_REQUEST);
    }
}
