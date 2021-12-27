package com.ajay.multipledatasources.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.multipledatasources.model.Book;
import com.ajay.multipledatasources.repositories.BooksRepository;

@RestController
public class BooksController {

	@Autowired
	BooksRepository bookRepository;

	@RequestMapping("/books")
	List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

}
