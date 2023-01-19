package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Salary;
import net.javaguides.springboot.repository.SalaryRepository;

@Service
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	private SalaryRepository salaryRepository;

	@Override
	public List<Salary> getAllSalaries() {
		return salaryRepository.findAll();
	}

	@Override
	public void saveSalary(Salary salary) {
		this.salaryRepository.save(salary);
	}

	@Override
	public Salary getSalaryById(long id) {
		Optional<Salary> optional = salaryRepository.findById(id);
		Salary salary = null;
		if (optional.isPresent()) {
			salary = optional.get();
		} else {
			throw new RuntimeException(" Salary not found for id :: " + id);
		}
		return salary;
	}

	@Override
	public void deleteSalaryById(long id) {
		this.salaryRepository.deleteById(id);
	}

	@Override
	public List<Salary> getSalaryByEmployeeId(long id) {
		return salaryRepository.findByEmployeeId(id);
	}

}
