package com.company.users;

import java.util.List;

import com.company.catalogue.Fine;
import com.company.exception.RetriveResourceException;

interface Administrator {
	void addMember(Member member) throws RetriveResourceException;
	void removeMember(Member member);
	void blockMember(Member member);
	void unblockMember(Member member);
	List<Fine> fetchAllFines() throws RetriveResourceException;
}
