package com.Ejercicio1.demo.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Book {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2") 
	private String id;
	private Integer isbn;
	private String title;
	private Integer year;
	private Integer books;
	private Integer borrowedBooks;
	private Integer leftBooks;
	private Boolean registered;
	@OneToOne(cascade = CascadeType.ALL) //no tiene mucho sentido aca porque modificamos desde autor
	private Author author;
	@OneToOne(cascade = CascadeType.ALL) 
	private Editorial editorial;
	
	// (fetch = FetchType.EAGER) trae la info pero es lento
}
