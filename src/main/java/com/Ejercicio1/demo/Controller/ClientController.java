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
import com.Ejercicio1.demo.Entity.Client;
import com.Ejercicio1.demo.Service.ClientService;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Client> clientList = clientService.read();
		model.addAttribute("clients", clientList);
		return "client.html";
	}

	@GetMapping("/create")
	public String create() {
		return "createClient.html";
	}

	@PostMapping("/create")
	public String create(@RequestParam Long DNI, String name, String surname, String mobileNumber, ModelMap model) { // tomo el dato del html que ingresa el usuario
		try {
			clientService.create(DNI, name, surname, mobileNumber);
			model.put("success", "Successfully added");
			return "createClient.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage());
			return "createClient.html";
		}

	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) { 
		clientService.delete(id);
		return "redirect:/client/list";
	}

	@GetMapping("/edit/{id}")
	public String modify(ModelMap model, @PathVariable("id") String id) {
		Client client = clientService.gettingClient(id);
		model.addAttribute("client", client);
		return "modifyClient.html";
	}
	
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, @RequestParam("DNI") Long DNI,@RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("mobileNumber") String mobileNumber, ModelMap model) {
		try {
			clientService.modify(id, DNI, name, surname, mobileNumber);
			model.put("success", "Successfully added");
			return "createAuthor.html";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("error", e.getMessage());
			//model.put("name", name); 
			return "createAuthor.html";
		}
	}
	
	@GetMapping("/registeredUp/{id}")
	public String registeredUp(@PathVariable String id) {
		try {
			clientService.registeredUp(id);
			return "redirect:/client/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}

	@GetMapping("/registeredDown/{id}")
	public String registeredDown(@PathVariable String id) {
		try {
			clientService.registeredDown(id);
			return "redirect:/client/list";
		} catch (Exception e) {
			return "redirect:/";
		}

	}
	
	
	
}
