package com.Ejercicio1.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.Validate;

import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Entity.Book;
import com.Ejercicio1.demo.Entity.Client;
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private EditorialService editorialService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookRepository bookRepository;

	// CREATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void create(String title, Integer isbn, Integer year, Integer books, Author author, Editorial editorial)
			throws Exception {
		validate(title, isbn, year, books);
		Book book = new Book();
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setYear(year);
		book.setBooks(books);
		book.setBorrowedBooks(0);
		book.setLeftBooks(books);
		book.setRegistered(true);
		book.setAuthor(author);
		book.setEditorial(editorial);
		bookRepository.save(book);
	}

	// READ
	@Transactional(readOnly = true)
	public List<Book> read() {
		List<Book> bookList = bookRepository.findAll();
		return bookList;
	}

	// UPDATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modify(String id, String title, Integer isbn, Integer year, Integer books, Author author,
			Editorial editorial) throws Exception {
		validate(title, isbn, year, books);
		Book book = bookRepository.getById(id);
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setYear(year);
		book.setBooks(books);
		book.setBorrowedBooks(0);
		book.setLeftBooks(books);
		book.setAuthor(author);
		book.setEditorial(editorial);
		bookRepository.save(book);
	}

	// DELETE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void delete(String id) {
		bookRepository.deleteById(id);
	}

	// GET BOOK BY ID
	@Transactional(readOnly = true)
	public Book gettingBook(String id) {
		Book book = bookRepository.getById(id);
		return book;
	}

	// GET BOOK BY NAME
	@Transactional(readOnly = true)
	public Book getBookByName(String name) throws Exception {
		Book book = bookRepository.alreadyExists(name);
		return book;
	}

	// REGISTERED UP
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Book registeredUp(String id) {
		Book entity = bookRepository.findById(id).get();
		entity.setRegistered(true);
		return bookRepository.save(entity);
	}

	// REGISTERED DOWN
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Book registeredDown(String id) {
		Book entity = bookRepository.findById(id).get();
		entity.setRegistered(false);
		return bookRepository.save(entity);
	}

	// BORROW BOOKS
	public void lendBooks(String id) {
		Book book = bookRepository.getById(id);
		book.setLeftBooks(book.getLeftBooks() - 1); 
		book.setBorrowedBooks(book.getBorrowedBooks() + 1); 
	}

	// VALIDATION
	public void validate(String title, Integer isbn, Integer year, Integer books) throws Exception {

		if (title == null || title.isEmpty() || title.contains("  ")) {
			throw new Exception();
		}

		if (isbn == null) {
			throw new Exception();
		}

		if (isbn.toString().length() < 10 || isbn.toString().length() > 13) {
			throw new Exception("Invalid ISBN entry");
		}

		if (year == null) {
			throw new Exception();
		}

		if (year > 2021 || year < 1700) {
			throw new Exception("Invalid year entry");
		}

		if (books == null || books != 0) {
			throw new Exception();
		}

	}

}
