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
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Service.EditorialService;

@Controller
@RequestMapping("/editorial")
public class EditorialController {
	
	@Autowired 
	private EditorialService editorialService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Editorial> editorialList = editorialService.read();
		model.addAttribute("editorials", editorialList);
		return "editorial.html"; 
	}
	
	@GetMapping("/create")
	public String create() {
		return "createEditorial.html";
	}
	
	@PostMapping("/create")
	public String create(@RequestParam String name, ModelMap model) {
		try {
			editorialService.create(name);
			model.put("success", "Successfully added");
			return "createEditorial.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage()); // se pone put en el post y addatribute en el getmapping
			model.put("name", name); // sirve para que no limpie el error automaticamente
			return "createEditorial.html";
		}

	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) { // toma la url con pathv.
		editorialService.delete(id);
		return "redirect:/editorial/list";
	}
	
	@GetMapping("/edit/{id}")
	public String modify(ModelMap model, @PathVariable("id") String id) {
		Editorial editorial = editorialService.gettingEditorial(id);
		model.addAttribute("person", editorial);
		return "modifyEditorial.html";
	}
	
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, @RequestParam("name") String name, ModelMap model) {
		try {
			editorialService.modify(id, name);
			model.put("success", "Successfully added");
			return "createEditorial.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage()); // se pone put en el post y addatribute en el getmapping
			model.put("name", name); // sirve para que no limpie el error automaticamente
			return "createEditorial.html";
		}
	}
	
	@GetMapping("/registeredUp/{id}")
	public String registeredUp(@PathVariable String id) {
		try {
			editorialService.registeredUp(id);
			return "redirect:/editorial/list";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/registeredDown/{id}")
	public String registeredDown(@PathVariable String id) {
		try {
			editorialService.registeredDown(id);
			return "redirect:/editorial/list";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
}
