package com.Ejercicio1.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Ejercicio1.demo.Entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

	@Query("SELECT c FROM Client c WHERE c.name like :name")
	public Client alreadyExists(@Param ("name") String name);
	
}
