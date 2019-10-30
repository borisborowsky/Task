package com.company.catalogue;
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

import com.company.authentication.Secured;
import com.company.exception.RetriveResourceException;
import com.google.gson.Gson;


@Path("books")
public class BookResource {
	@Context
	SecurityContext securityContext;
	
	@POST
	@Path("/add/book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public BookUnit addBook(String json) {
		
		BookUnit book = new Gson().fromJson(json, BookUnit.class);
		new BookService().addBook(Objects.requireNonNull(book));
		
		return book;	
	}
	
	@GET
	@Path("/remove/book/{bookId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public void removeBook(@PathParam("bookId") int bookId) {
		new BookService().removeBook(bookId);
	}
	
	@GET
	@Path("/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
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
