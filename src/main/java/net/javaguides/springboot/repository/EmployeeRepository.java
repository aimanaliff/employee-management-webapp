package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employees e WHERE e.first_name ILIKE %:keyword% OR e.last_name ILIKE %:keyword% OR e.email ILIKE %:keyword% OR e.department ILIKE %:keyword%", nativeQuery = true)
    List<Employee> searchByKeyword(@Param("keyword") String keyword);

}
