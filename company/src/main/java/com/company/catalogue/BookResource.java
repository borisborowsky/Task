package com.company.catalogue;
import java.security.Principal;
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

import com.company.users.UserService;
import com.google.gson.Gson;


@Path("books")
public class BookResource {
	@Context
	SecurityContext securityContext;
	
	@POST
	@Path("/add/book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	private BookUnit addBook(String json) {

		System.out.println(json + " JSON");
		
		BookUnit book = new Gson().fromJson(json, BookUnit.class);
		System.out.println(book + " BOOK");
		new BookService().addBook(Objects.requireNonNull(book));
		return book;
	}
	
	@POST
	@Path("/remove/book")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	private BookUnit removeBook(String json) {

		System.out.println(json + " JSON");
		
		BookUnit book = new Gson().fromJson(json, BookUnit.class);
		System.out.println(book + " BOOK");
		new BookService().addBook(Objects.requireNonNull(book));
		return book;
	}
	
}
