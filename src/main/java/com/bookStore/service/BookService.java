package com.bookStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bookStore.entity.Book;
import com.bookStore.repository.BookRepository;

@Service
public class BookService {
  
	@Autowired
	private BookRepository bRepo;
	
	public void save(Book b) {//1
		bRepo.save(b);
	}
	public List<Book> getAllBook(){//2
		return  bRepo.findAll();
	}
	public Book getBookById(int id) {//3
		return bRepo.findById(id).get();
	}
	public void deleteById(int id) {//4
	    bRepo.deleteById(id);
	}
	public void updateBook(Book b) {//5
		bRepo.save(b);
	}

}