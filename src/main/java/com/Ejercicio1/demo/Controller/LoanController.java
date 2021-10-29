package com.Ejercicio1.demo.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Entity.Book;
import com.Ejercicio1.demo.Entity.Client;
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Entity.Loan;
import com.Ejercicio1.demo.Service.BookService;
import com.Ejercicio1.demo.Service.ClientService;
import com.Ejercicio1.demo.Service.LoanService;

@Controller
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private BookService bookService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Loan> loanList = loanService.read();
		model.addAttribute("loans", loanList);
		return "loan.html";
	}

	@GetMapping("/create")
	public String creating(ModelMap model) {
		List<Book> bookList = bookService.read();
		List<Client> clientList = clientService.read();
		model.addAttribute("books", bookList);
		model.addAttribute("clients", clientList);
		return "createLoan.html";
	}

	@PostMapping("/create")
	public String create(@RequestParam String book, @RequestParam Date loanDate, @RequestParam Date returnDate,
			@RequestParam String client) {
		try {
			Book bookEntity = bookService.getBookByName(book);
			Client clientEntity = clientService.getClientByName(client);
			loanService.create(bookEntity, loanDate, returnDate, clientEntity);
			return "redirect:/loan/list";
		} catch (Exception e) {
			e.getMessage();
			return "redirect:/loan/list";
		}
	}

}
