package com.company.users;

import com.company.exception.RetriveResourceException;

public interface Administrator {
	void addMember(Member member) throws RetriveResourceException;
	void removeMember(Member member);
	void blockMember(Member member);
	void unblockMember(Member member);
}
