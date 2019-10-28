package com.company.users;

import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.company.catalogue.Book;
import com.company.catalogue.BookUnit;
import com.company.catalogue.Fine;
import com.company.exception.RetriveResourceException;
import com.company.utils.HibernateUtils;

public class UserService implements Administrator, Customer {

	@Override
	public void addMember(Member member) throws RetriveResourceException {
		Transaction transaction = null;

		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(Objects.requireNonNull(member));
			transaction.commit(); 
			session.close();
		} catch (HibernateException e) {
			if (transaction != null) 
				transaction.rollback();
			e.printStackTrace();
			throw new RetriveResourceException("Cannot save resource to database : "
					 + member.toString(), e);
	
		}
	}

	@Override
	public void removeMember(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blockMember(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unblockMember(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnBook(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean renewBook(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public Fine checkForFine(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
