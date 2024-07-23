package com.example.techschool.repository;

import com.example.techschool.dao.LeaveManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository  extends JpaRepository<LeaveManagement, String> {

}
