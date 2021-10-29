package com.Ejercicio1.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Ejercicio1.demo.Entity.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{

	@Query("SELECT e FROM Editorial e WHERE e.name like :name")
	public Editorial alreadyExists(@Param ("name") String name);
	
}
