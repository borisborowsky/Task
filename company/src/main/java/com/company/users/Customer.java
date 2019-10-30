package com.company.users;

import java.util.List;

import com.company.catalogue.Book;
import com.company.catalogue.BookUnit;
import com.company.catalogue.Fine;
import com.company.exception.RetriveResourceException;

interface Customer {
	BookUnit unBorrowBook(int userId, int bookId) throws RetriveResourceException;
	boolean renewBook(Book book);
	List<Fine> checkForFine(int userId) throws RetriveResourceException;
}
