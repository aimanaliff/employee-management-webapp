package net.javaguides.springboot.model;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "salaries")
public class Salary {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    @Column(name = "date")
    private String date;

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

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)

	private Employee employee;

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
		calculateNetSalary();
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
		calculateNetSalary();
	}
	public void setOtHoursWorking(int otHoursWorking) {
		this.otHoursWorking = otHoursWorking;
		calculateNetSalary();
	}
	public void setOtHoursRest(int otHoursRest) {
		this.otHoursRest = otHoursRest;
		calculateNetSalary();
	}
	public void setOtHoursPublic(int otHoursPublic) {
		this.otHoursPublic = otHoursPublic;
		calculateNetSalary();
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
