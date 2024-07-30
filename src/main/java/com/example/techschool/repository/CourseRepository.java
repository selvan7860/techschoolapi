package com.example.techschool.repository;

import com.example.techschool.dao.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
