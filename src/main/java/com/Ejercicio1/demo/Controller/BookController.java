package com.Ejercicio1.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Entity.Book;
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Service.AuthorService;
import com.Ejercicio1.demo.Service.BookService;
import com.Ejercicio1.demo.Service.EditorialService;

@Controller
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private EditorialService editorialService;
	@Autowired
	private AuthorService authorService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Book> bookList = bookService.read();
		model.addAttribute("books", bookList);
		return "book.html";
	}

	@GetMapping("/create")
	public String creating(ModelMap model) {
		System.err.println("ENTRÉ EN EL GETT");
		List<Author> authorList = authorService.read();
		List<Editorial> editorialList = editorialService.read();
		model.addAttribute("authors", authorList);
		model.addAttribute("editorials", editorialList);
		return "createBook.html";
	}

	@PostMapping("/create")
	public String create(@RequestParam String title, @RequestParam Integer isbn, @RequestParam Integer year,
			@RequestParam Integer books, @RequestParam String author, @RequestParam String editorial, ModelMap model) {
		System.err.println("NO ENTRE AL TRY TODAVIA");
		try {
			System.err.println("QUEDÉ EN EL TRYYY");
			Author authorsEntity = authorService.getAuthorByName(author);
			Editorial editorialEntity = editorialService.getEditorialByName(editorial);
			bookService.create(title, isbn, year, books, authorsEntity, editorialEntity);
			model.put("success", "Successfully added");
			return "createBook.html";
		} catch (Exception e) {
			System.err.println("ERRRROR");
			e.getMessage();
			model.put("error", e.getMessage());
			/*model.put("title", title);
			model.put("isbn", isbn);
			model.put("year", year);
			model.put("books", books);
			model.put("author", author);
			model.put("editorial", editorial);*/
			return "redirect:/book/list";
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		bookService.delete(id);
		return "redirect:/book/list";
	}

	@GetMapping("/edit/{id}")
	public String modify(ModelMap model, @PathVariable("id") String id) {
		Book book = bookService.gettingBook(id);
		model.addAttribute("book", book);
		return "modifyBook.html";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, @RequestParam("title") String title,
			@RequestParam("isbn") Integer isbn, @RequestParam("year") Integer year,
			@RequestParam("books") Integer books, Integer left, @RequestParam("author") String author,
			@RequestParam("editorial") String editorial) {
		try {
			Author authorsEntity = authorService.getAuthorByName(author);
			Editorial editorialEntity = editorialService.getEditorialByName(editorial);
			bookService.modify(id, title, isbn, year, books, authorsEntity, editorialEntity);
			return "redirect:/book/list";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/book/list";
		}

	}

	@GetMapping("/registeredUp/{id}")
	public String registeredUp(@PathVariable String id) {
		try {
			bookService.registeredUp(id);
			return "redirect:/book/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/registeredDown/{id}")
	public String registeredDown(@PathVariable String id) {
		try {
			bookService.registeredDown(id);
			return "redirect:/book/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}
	
	@GetMapping("/borrow/{id}")
	public String list(@PathVariable String id, ModelMap model) {
		bookService.lendBooks(id);
		return "book.html";
	}

}
