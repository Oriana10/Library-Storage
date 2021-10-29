package com.Ejercicio1.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Entity.Editorial;
import com.Ejercicio1.demo.Repository.EditorialRepository;

@Service
public class EditorialService {

	@Autowired
	private EditorialRepository editorialRepository;

	// CREATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial create(String title) throws Exception {
		validation(title);
		Editorial editorial = new Editorial();
		editorial.setName(title);
		editorial.setRegistered(true);
		editorialRepository.save(editorial);
		return editorial;
	}

	// READ
	@Transactional(readOnly = true)
	public List<Editorial> read() {
		List<Editorial> editorialList = editorialRepository.findAll(); 
		return editorialList; 
	}

	// UPDATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modify(String id, String name) throws Exception {
		validation(name);
		Editorial editorial = editorialRepository.getById(id);
		editorial.setName(name);
		editorialRepository.save(editorial);
	}

	// DELETE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void delete(String id) {
		editorialRepository.deleteById(id);
	}

	// GET EDITORIAL BY ID
	@Transactional(readOnly = true)
	public Editorial gettingEditorial(String id) {
		Editorial editorial = editorialRepository.getById(id);
		return editorial;
	}

	// GET EDITORIAL BY NAME
	@Transactional(readOnly = true)
	public Editorial getEditorialByName(String name) throws Exception {
		validation(name);
		Editorial editorial = editorialRepository.alreadyExists(name);
		return editorial;
	}

	// REGISTERED UP
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial registeredUp(String id) {
		Editorial entity = editorialRepository.findById(id).get();
		entity.setRegistered(true);
		return editorialRepository.save(entity);
	}

	// REGISTERED DOWN
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Editorial registeredDown(String id) {
		Editorial entity = editorialRepository.findById(id).get();
		entity.setRegistered(false);
		return editorialRepository.save(entity);
	}

	// VALIDATION
	public void validation(String name) throws Exception {
		if (name.isBlank() || name.isEmpty() || name == null) {
			throw new Exception("Insert editorial's name");
		}
		if (editorialRepository.alreadyExists(name) != null) {
			throw new Exception("This editorial already exists");
		}

	}
}