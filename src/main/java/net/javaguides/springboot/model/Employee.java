package net.javaguides.springboot.model;

import java.text.DecimalFormat;

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

	@Column(name = "ot_hours_working")
	private int otHoursWorking;

	@Column(name = "ot_hours_rest")
	private int otHoursRest;

	@Column(name = "ot_hours_public")
	private int otHoursPublic;

	@Column(name = "ot_pay_total")
	private double otPayTotal;

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
		calculateNetSalary();
	}
	public int getOtHoursWorking() {
		return otHoursWorking;
	}
	public void setOtHoursWorking(int otHoursWorking) {
		this.otHoursWorking = otHoursWorking;
		calculateNetSalary();
	}
	public int getOtHoursRest() {
		return otHoursRest;
	}
	public void setOtHoursRest(int otHoursRest) {
		this.otHoursRest = otHoursRest;
		calculateNetSalary();
	}
	public int getOtHoursPublic() {
		return otHoursPublic;
	}
	public void setOtHoursPublic(int otHoursPublic) {
		this.otHoursPublic = otHoursPublic;
		calculateNetSalary();
	}
	public double getOtPayTotal() {
		return otPayTotal;
	}
	public double getNetSalary() {
		return netSalary;
	}

	// bonus, 11% EPF deduction, 0.5% SOCSO deduction, 0.2% EIS deduction, OT pay according to hours
	public void calculateNetSalary() {
		DecimalFormat df = new DecimalFormat("#.##");    

		basicSalary = getBasicSalary();
		bonus = getBonus();
		otHoursWorking = getOtHoursWorking();
		otHoursRest = getOtHoursRest();
		otHoursPublic = getOtHoursPublic();
		
		epf = 0.11 * basicSalary;
		socso = 0.005 * basicSalary;
		eis = 0.002 * basicSalary;

		// we assume that all employees are entitled for OT pay
		double dailyPay = Double.valueOf(df.format(basicSalary/26));
		double hourlyPay = Double.valueOf(df.format(dailyPay/8));

		double otPayWorking = hourlyPay * otHoursWorking * 1.5;
		double otPayRest = 0;
		double otPayPublic = 0;

		if(otHoursRest > 0 && otHoursRest <= 4) {
			otPayRest = dailyPay * 0.5;
		} else if(otHoursRest > 4 && otHoursRest <= 8) {
			otPayRest = dailyPay;
		} else if(otHoursRest > 8) {
			double otHoursRestBal = otHoursRest - 8;
			otPayRest = dailyPay + (hourlyPay * otHoursRestBal * 2);
		}

		if(otHoursPublic > 0 && otHoursPublic <= 8) {
			otPayPublic = dailyPay * 2;
		} else if(otHoursPublic > 8) {
			double otHoursPublicBal = otHoursPublic - 8;
			otPayPublic = dailyPay + (hourlyPay * otHoursPublicBal * 3);
		}

		otPayTotal = Double.valueOf(df.format(otPayWorking + otPayRest + otPayPublic));
		netSalary = Double.valueOf(df.format(basicSalary + bonus - epf - socso - eis + otPayTotal));
	}
}
