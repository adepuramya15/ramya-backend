package com.accesshr.emsbackend.Repo;

import com.accesshr.emsbackend.Entity.EmployeeManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeManagerRepository extends JpaRepository<EmployeeManager, Integer> {
    // Custom method to find Employee by their corporate email
    EmployeeManager findByCorporateEmail(String corporateEmail);
}
