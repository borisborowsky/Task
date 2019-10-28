package com.company.catalogue;

import java.util.List;

import com.company.exception.RetriveResourceException;

public interface Searchable {
	 List<BookUnit> getBorrowedBooks(int userId) throws RetriveResourceException;
	 BookUnit borrowBook(int userId, int bookId) throws RetriveResourceException;
	 List<BookUnit> search(BookUnit book) throws RetriveResourceException;
}
