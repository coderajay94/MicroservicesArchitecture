package com.ajay.multipledatasources.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ajay.multipledatasources.model.Book;

@Repository
public interface BooksRepository extends CrudRepository<Book, Integer> {

}
