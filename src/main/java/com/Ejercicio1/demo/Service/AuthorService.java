package com.Ejercicio1.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.Ejercicio1.demo.Entity.Author;
import com.Ejercicio1.demo.Repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	// CREATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Author create(String title) throws Exception {
		validation(title);
		Author author = new Author();
		author.setName(title);
		author.setRegistered(true);
		authorRepository.save(author);
		return author;
	}

	// READ
	@Transactional(readOnly = true)
	public List<Author> read() {
		List<Author> authorList = authorRepository.findAll(); // findAll() == SELECT a from Author a
		return authorList; // return (List<Author>) data.findAll();
	}

	// UPDATE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void modify(String id, String name) throws Exception {
		validation(name);
		Author author = authorRepository.getById(id);
		author.setName(name);
		authorRepository.save(author);
	}

	// DELETE
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void delete(String id) {
		authorRepository.deleteById(id);
	}

	// GET AUTHOR BY ID
	@Transactional(readOnly = true)
	public Author gettingAuthor(String id) {
		Author author = authorRepository.getById(id);
		return author;
	}

	// GET AUTHOR BY NAME
	@Transactional(readOnly = true)
	public Author getAuthorByName(String name) throws Exception {
		Author author = authorRepository.alreadyExists(name);
		return author;
	}

	// REGISTERED UP
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Author registeredUp(String id) {
		Author entity = authorRepository.findById(id).get();
		entity.setRegistered(true);
		return authorRepository.save(entity);
	}

	// REGISTERED DOWN
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public Author registeredDown(String id) {
		Author entity = authorRepository.findById(id).get();
		entity.setRegistered(false);
		return authorRepository.save(entity);
	}

	// VALIDATION
	public void validation(String name) throws Exception {
		if (name.isBlank() || name.isEmpty() || name == null) {
			throw new Exception("Insert author's name");
		}
		if (authorRepository.alreadyExists(name) != null) {
			throw new Exception("This author already exists");
		}
	}

}