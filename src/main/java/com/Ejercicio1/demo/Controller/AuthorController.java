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
import com.Ejercicio1.demo.Service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Author> authorList = authorService.read();
		model.addAttribute("authors", authorList);
		return "author.html";
	}

	@GetMapping("/create")
	public String create() {
		return "createAuthor.html";
	}

	@PostMapping("/create")
	public String create(@RequestParam String name, ModelMap model) { // tomo el dato del html que ingresa el usuario
		try {
			authorService.create(name);
			model.put("success", "Successfully added");
			return "createAuthor.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage()); // se pone put en el post y addatribute en el getmapping
			model.put("name", name); // sirve para que no limpie el error automaticamente
			return "createAuthor.html";
		}

	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) { // toma la url con pathv.
		authorService.delete(id);
		return "redirect:/author/list";
	}

	@GetMapping("/edit/{id}")
	public String modify(ModelMap model, @PathVariable("id") String id) {
		Author author = authorService.gettingAuthor(id);
		model.addAttribute("person", author);
		return "modifyAuthor.html";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, @RequestParam("name") String name, ModelMap model) {
		try {
			authorService.modify(id, name);
			model.put("success", "Successfully added");
			return "createAuthor.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage()); // se pone put en el post y addatribute en el getmapping
			model.put("name", name); // sirve para que no limpie el error automaticamente
			return "createAuthor.html";
		}
	}

	@GetMapping("/registeredUp/{id}")
	public String registeredUp(@PathVariable String id) {
		try {
			authorService.registeredUp(id);
			return "redirect:/author/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/registeredDown/{id}")
	public String registeredDown(@PathVariable String id) {
		try {
			authorService.registeredDown(id);
			return "redirect:/author/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

}
