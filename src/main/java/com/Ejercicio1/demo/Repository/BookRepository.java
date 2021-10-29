package com.Ejercicio1.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Ejercicio1.demo.Entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, String>{

	@Query("SELECT b FROM Book b WHERE b.title like :title")
	public Book alreadyExists(@Param ("title") String name);
	
	
}