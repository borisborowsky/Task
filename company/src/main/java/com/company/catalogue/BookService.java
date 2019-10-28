package com.company.catalogue;

import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.company.utils.HibernateUtils;

public class BookService implements ManageBook {

	@Override
	public void addBook(BookUnit book) {
		Transaction transaction = null;

		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(Objects.requireNonNull(book));
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}
	
	@Override
	public void removeBook(int id) {
		Transaction transaction = null;
		BookUnit book = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			try {
				book = session.get(BookUnit.class, id);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

			if (book != null)
				session.delete(book);

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

}
