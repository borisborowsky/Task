package com.company.catalogue;

import java.util.List;

import com.company.exception.RetriveResourceException;

interface Catalog  {
	void addBook(BookUnit book) throws RetriveResourceException;
    void removeBook(int id) throws RetriveResourceException;
    List<BookUnit> fetchAllBooks() throws RetriveResourceException;
}
