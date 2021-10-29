package com.Ejercicio1.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Ejercicio1.demo.Entity.Loan;

public interface LoanRepository  extends JpaRepository<Loan, String> {

}
