package com.company.users;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.company.catalogue.Book;

@Entity
@Table(name="employee_tbl")
public class Employee extends Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final int id;
	
	private final Date hireDate;
	private final double salary;
	
	Employee() {
		id = 0;
		hireDate = new Date();
		salary = 0;
	}
	
	Employee(int id, String name, String phone, String country, 
			String city, String postCode, Date hireDate,
			double salary, Gender gender) {
		super(name, phone, country, city, postCode, gender);
		this.id = id;
		this.hireDate = new Date();
		this.salary = salary;
	}
}
