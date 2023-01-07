package net.javaguides.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;

	@Column(name = "department")
	private String department;

	@Column(name = "basic_salary")
	private double basicSalary;

	@Column(name = "epf")
	private double epf;

	@Column(name = "socso")
	private double socso;

	@Column(name = "eis")
	private double eis;

	@Column(name = "bonus")
	private double bonus;

	@Column(name = "net_salary")
	private double netSalary;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public double getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
		calculateNetSalary();
	}
	public double getEpf() {
		return epf;
	}
	public double getSocso() {
		return socso;
	}
	public double getEis() {
		return eis;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	public double getNetSalary() {
		return netSalary;
	}

	public void calculateNetSalary() {
		basicSalary = getBasicSalary();
		
		// bonus addition, 11% EPF deduction, 0.5% SOCSO deduction, 0.2% EIS deduction
		epf = 0.11 * basicSalary;
		socso = 0.005 * basicSalary;
		eis = 0.002 * basicSalary;

		netSalary = basicSalary + getBonus() - epf - socso - eis;
	}
}
