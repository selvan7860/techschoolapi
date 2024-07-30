package com.example.techschool.repository;

import com.example.techschool.dao.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CheckInOutRepository extends JpaRepository<CheckInOut, String> {
    Optional<CheckInOut> findByUserIdAndDate(String userId, LocalDate today);
}
