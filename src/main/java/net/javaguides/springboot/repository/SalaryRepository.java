package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>{

    List<Salary> findByEmployeeId(long employeeId);

}
