package com.company.catalogue;

import java.util.List;

import com.company.exception.RetriveResourceException;
import com.company.users.Customer;
import com.company.users.Member;

interface Catalog {
	BookUnit search(int userId, int bookId) throws RetriveResourceException;
	List<BookUnit> search(String json) throws RetriveResourceException;
	
}
