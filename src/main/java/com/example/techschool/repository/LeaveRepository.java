package com.example.techschool.repository;

import com.example.techschool.dao.LeaveManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository  extends JpaRepository<LeaveManagement, String> {

    Optional<LeaveManagement> findByIdAndUserId(String id, String userId);

    List<LeaveManagement> findAllByUserId(String userId);
}
