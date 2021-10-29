package com.Ejercicio1.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Client {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2") 
	private String id;
	private Long DNI;
	private String name;
	private String surname;
	private String mobileNumber;
	private Boolean registered;
}
