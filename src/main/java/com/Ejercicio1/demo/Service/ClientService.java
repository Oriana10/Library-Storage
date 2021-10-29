package com.Ejercicio1.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Entity.Book;
import com.Ejercicio1.demo.Entity.Client;
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	// CREATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Client create(Long DNI, String name, String surname, String mobileNumber) throws Exception {
		validation(DNI, name, surname, mobileNumber);
		Client client = new Client();
		client.setDNI(DNI);
		client.setName(name);
		client.setSurname(surname);
		client.setMobileNumber(mobileNumber);
		client.setRegistered(true);
		clientRepository.save(client);
		return client;
	}

	// READ
	@Transactional(readOnly = true)
	public List<Client> read() {
		List<Client> clientList = clientRepository.findAll();
		return clientList;
	}

	// UPDATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modify(String id, Long DNI, String name, String surname, String mobileNumber) throws Exception {
		validation(DNI, name, surname, mobileNumber);
		Client client = clientRepository.getById(id);
		client.setDNI(DNI);
		client.setName(name);
		client.setSurname(surname);
		client.setMobileNumber(mobileNumber);
		client.setRegistered(true);
		clientRepository.save(client);
	}

	// DELETE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void delete(String id) {
		clientRepository.deleteById(id);
	}

	// REGISTERED DOWN
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Client registeredDown(String id) {
		Client client = clientRepository.findById(id).get();
		client.setRegistered(false);
		return clientRepository.save(client);
	}

	// REGISTERED UP
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Client registeredUp(String id) {
		Client client = clientRepository.findById(id).get();
		client.setRegistered(true);
		return clientRepository.save(client);
	}

	// GET CLIENT BY ID
	@Transactional(readOnly = true)
	public Client gettingClient(String id) {
		Client client = clientRepository.getById(id);
		return client;
	}

	// GET CLIENT BY NAME
	@Transactional(readOnly = true)
	public Client getClientByName(String name) throws Exception {
		Client client = clientRepository.alreadyExists(name);
		return client;
	}

	// VALIDATION
	public void validation(Long DNI, String name, String surname, String mobileNumber) throws Exception {

		if (DNI == null || DNI.toString().length() != 8) {
			throw new Exception("Invalid DNI");
		}

		if (name == null || name.isEmpty() || name.contains("  ")) {
			throw new Exception("Invalid name");
		}

		if (surname == null || surname.isEmpty() || surname.contains("  ")) {
			throw new Exception("Invalid name");
		}

// Integer.parseInt(mobileNumber.substring(0, 3)) != 15 || Integer.parseInt(mobileNumber.substring(0, 4)) != 341 ||
		if (mobileNumber == null || mobileNumber.length() != 10 || mobileNumber.contains("  ")) {
			throw new Exception("Invalid mobile phone number");
		}

	}
}
