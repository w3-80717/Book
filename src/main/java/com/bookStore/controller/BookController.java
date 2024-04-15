package com.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import ch.qos.logback.core.model.Model;

@Controller
public class BookController {
	
	@Autowired
	private BookService service;
	@Autowired
	private MyBookListService myBookService;
	
   @GetMapping("/")
   public String home() {
	   return "home";
   }
   
   @GetMapping("/book_register")//no need to call service dont need create method just call this show bookRegister.html 
   public String bookRegister() {
	   return "bookRegister";
   }
//   @GetMapping("/available_books")
//   public String getAllBook() {
//	   return "bookList";
//   }
   @PostMapping("/save")//1.BookService
   public String addBook(@ModelAttribute Book b) {
	service.save(b); 
	return "redirect:/available_books";
	   }
   
   @GetMapping("/available_books")
   public ModelAndView getAllBook() { //2.BookService
	   List<Book> list = service.getAllBook();
	   return new ModelAndView("bookList","book",list);
   }
   
   @GetMapping("/my_books")
	   public ModelAndView getMyBooks() {//2. MyBookListService
	       List<MyBookList> mb = myBookService.getAllBook();
	       ModelAndView mv = new ModelAndView("myBook","book",mb);
		   return mv;
	   }
   //important 2 tables 1 book ko mybook me dalna hai book ke id se book fetch kare 58 
   @GetMapping("/myList/{id}")
   public String getMyList(@PathVariable("id") int id) {
	   Book b = service.getBookById(id);//3.BookService
	   MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
	   myBookService.saveMyBooks(mb);//1.MyBookListService save 
	   return "redirect:/my_books"; 
   }
   @RequestMapping("/myListdelete/{id}")
   public String deleteMyList(@PathVariable int id) {
	  myBookService.deleteById(id); //3.MyBookListService	
	   return "redirect:/my_books";
   }
   @RequestMapping("/bookdelete/{id}")
   public String deleteBook(@PathVariable int id) {
	  service.deleteById(id); //4.Bookservice
	   return "redirect:/available_books";
   }
   @GetMapping("/bookedit/{id}")
   public ModelAndView editBook(@PathVariable int id, Model model) {
	   Book b = service.getBookById(id);//3.BookService
	   ModelAndView mv = new ModelAndView("bookEdit","book",b);
	   return mv;
   }
   @PostMapping("/bookedit/{id}")
   public String updateBook(@PathVariable int id, @ModelAttribute Book b) {
	  b.setId(id);
	  System.out.println(b);
	   service.updateBook(b);//5.BookService
	   
	   return "redirect:/available_books";
   }
}
