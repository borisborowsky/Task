package com.company.users;

import java.util.List;

import com.company.catalogue.Book;
import com.company.catalogue.Fine;
import com.company.exception.RetriveResourceException;

interface Customer {
	void returnBook(Book book);
	boolean renewBook(Book book);
	List<Fine> checkForFine(int userId) throws RetriveResourceException;
}
