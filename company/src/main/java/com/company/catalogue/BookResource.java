package com.company.catalogue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.company.catalogue.BookUnit;
import com.company.exception.RetriveResourceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Path("books")
public class BookResource {
	
	@POST
	@Path("/add/book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BookUnit addBook(String json) {

		System.out.println(json + " JSON");
		
		BookUnit book = new Gson().fromJson(json, BookUnit.class);
		System.out.println(book + " BOOK");
		new BookService().addBook(Objects.requireNonNull(book));
		return book;
	}
	
	@GET
	@Path("/remove/book/{bookId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeBook(@PathParam("bookId") int bookId) {

		new BookService().removeBook(bookId);
	
	}
	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookUnit> fetchAllBooks() {
		List<BookUnit> books = null; 
	
		try {
			books = new BookService().fetchAllBooks();
		} catch (RetriveResourceException e) {
			e.printStackTrace();
		}
	
		return books;
	}
	
}
