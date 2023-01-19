package net.javaguides.springboot.service;

import java.util.List;

import net.javaguides.springboot.model.Salary;

public interface SalaryService {
	List<Salary> getAllSalaries();
	void saveSalary(Salary salary);
	Salary getSalaryById(long id);
	void deleteSalaryById(long id);
	List<Salary> getSalaryByEmployeeId(long id);
}
