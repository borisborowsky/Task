package com.company.catalogue;

import com.company.exception.RetriveResourceException;

interface ManageBook  {
	void addBook(BookUnit book) throws RetriveResourceException;
    void removeBook(int id) throws RetriveResourceException;
}
