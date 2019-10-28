package com.company.users;

import com.company.catalogue.Book;
import com.company.catalogue.BookUnit;
import com.company.catalogue.Fine;

public interface Customer {
	void returnBook(Book book);
	boolean renewBook(Book book);
	Fine checkForFine(Customer customer);
	
}
