package com.ajay.multipledatasources.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

	@Id
	@Column(name = "book_id")
	private int boodId;
	@Column(name = "book_name")
	private String bookName;
	@Column(name = "book_desc")
	private String bookDescription;

}
