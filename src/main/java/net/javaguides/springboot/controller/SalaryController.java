package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import net.javaguides.springboot.model.Salary;
import net.javaguides.springboot.service.SalaryService;

@Controller
public class SalaryController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SalaryService salaryService;

	// display list of salaries
	@GetMapping("/indexSalary")
	public String viewSalary(Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("listEmployees", employees);
		model.addAttribute("listSalaries", salaryService.getAllSalaries());
		return "index_salary";		
	}
	
	// display list of salaries for the selected employee
	@GetMapping("/viewSalary/{id}")
	public String viewSalary(@PathVariable (value="id") long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		model.addAttribute("listSalaries", salaryService.getSalaryByEmployeeId(id));
		return "salary";		
	}
	
	@GetMapping("/showNewSalaryForm")
	public String showNewSalaryForm(Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("listEmployees", employees);

		// create model attribute to bind form data
		Salary salary = new Salary();
		model.addAttribute("salary", salary);
		return "index_new_salary";
	}

	@GetMapping("/showNewSalaryForm/{id}")
	public String showNewSalaryForm(@PathVariable (value="id") long id, Model model) {
		// create model attribute to bind form data
		Salary salary = new Salary();
		model.addAttribute("id", id);
		model.addAttribute("salary", salary);
		return "new_salary";
	}

	@PostMapping("/saveSalary")
	public String saveSalary(@ModelAttribute("salary") Salary salary,  @RequestParam(name = "employeeId", required = false) String employeeId) {
		Employee employee = employeeService.getEmployeeById(Long.valueOf(employeeId));
		// save salary to database
		salary.setEmployee(employee);
		salaryService.saveSalary(salary);
		return "redirect:/indexSalary";
	}
	
	@PostMapping("/saveSalary/{id}")
	public String saveSalary(@PathVariable (value="id") long id, @ModelAttribute("salary") Salary salary) {
		Employee employee = employeeService.getEmployeeById(id);
		// save salary to database
		salary.setEmployee(employee);
		salaryService.saveSalary(salary);
		return "redirect:/viewSalary/" + id;
	}

	@GetMapping("/showIndexSalaryFormForUpdate/{id}")
	public String showIndexSalaryFormForUpdate(@PathVariable (value="id") long id, Model model) {
		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("listEmployees", employees);
		
		Salary salary = salaryService.getSalaryById(id);
		model.addAttribute("salary", salary);
		return "index_update_salary";
	}
	
	@GetMapping("/showSalaryFormForUpdate/{id}")
	public String showSalaryFormForUpdate(@PathVariable (value="id") long id, Model model) {
		Salary salary = salaryService.getSalaryById(id);
		model.addAttribute("salary", salary);
		return "update_salary";
	}

	@GetMapping("/deleteIndexSalary/{id}")
	public String deleteIndexSalary(@PathVariable (value="id") long id) {
		// call delete salary method 
		this.salaryService.deleteSalaryById(id);
		return "redirect:/indexSalary";
	}
	
	@GetMapping("/deleteSalary/{id}")
	public String deleteSalary(@PathVariable (value="id") long id) {
		// call delete salary method 
		Salary salary = salaryService.getSalaryById(id);
		this.salaryService.deleteSalaryById(id);
		return "redirect:/viewSalary/" + salary.getEmployee().getId();
	}
}