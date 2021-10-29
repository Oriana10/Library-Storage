package com.Ejercicio1.demo.Service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.Ejercicio1.demo.Entity.Book;
import com.Ejercicio1.demo.Entity.Client;
import com.Ejercicio1.demo.Entity.Loan;
import com.Ejercicio1.demo.Repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	// CREATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Loan create(Book book, Date loanDate, Date returnDate, Client client) throws Exception {
		validation(book, loanDate, returnDate, client);
		Loan loan = new Loan();
		loan.setLoanDate(loanDate);
		loan.setReturnDate(returnDate);
		loan.setBook(book);
		loan.setClient(client);
		loan.setRegistered(true);
		loanRepository.save(loan);
		return loan;
	}

	// READ
	@Transactional(readOnly = true)
	public List<Loan> read() {
		List<Loan> loanList = loanRepository.findAll();
		return loanList;
	}

	// UPDATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modify(String id, Date  loanDate, Date  returnDate, Book book, Client client) throws Exception {
		validation(book, loanDate, returnDate, client);
		Loan loan = loanRepository.getById(id);
		loan.setLoanDate(loanDate);
		loan.setReturnDate(returnDate);
		loan.setBook(book);
		loan.setClient(client);
		loan.setRegistered(true);
		loanRepository.save(loan);
	}

	// REGISTERED DOWN
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Loan registeredDown(String id) {
		Loan loan = loanRepository.findById(id).get();
		loan.setRegistered(false);
		return loanRepository.save(loan);
	}

	// VALIDATION
	public void validation(Book book, Date  loanDate, Date  returnDate, Client client) throws Exception {

		if (loanDate == null ) {
			throw new Exception("Invalid loan date");
		}
		
		if (returnDate == null ) {
			throw new Exception("Invalid loan date");
		}

		if (book == null) {
			throw new Exception("Invalid book");
		}

		if (client == null) {
			throw new Exception("Invalid client");
		}

	}
	
}
