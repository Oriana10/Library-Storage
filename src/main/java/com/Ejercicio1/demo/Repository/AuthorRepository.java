package com.Ejercicio1.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Ejercicio1.demo.Entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String>{

	@Query("SELECT a FROM Author a WHERE a.name like :name")
	public Author alreadyExists(@Param ("name") String name);
	
}
