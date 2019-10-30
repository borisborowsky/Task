package com.company.users;

import java.util.List;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.company.catalogue.Book;
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
			throw new RetriveResourceException(
					"Cannot save resource to database : " + member.toString(), e);
		}
	}

	@Override
	public List<Fine> checkForFine(int userId) throws RetriveResourceException {
		Transaction transaction = null;
		Member member = null;
		List<Fine> fines = null;

		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			member = session.get(Member.class, userId);

			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();

			QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Fine.class).get();

			org.apache.lucene.search.Query lucenceQuery = qb.keyword().onFields("member", "fines")
					.matching(member.getId()).createQuery();

			@SuppressWarnings("unchecked")
			Query<Fine> query = fullTextSession.createFullTextQuery(lucenceQuery, Fine.class);
			fines = query.getResultList();

			transaction.commit();
			session.close();
		} catch (HibernateException | InterruptedException e) {
			Thread.currentThread().interrupt();
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw new RetriveResourceException("Cannot retrive resource from database : " + member + "\n" + fines, e);
		}
		return fines;

	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Fine> fetchAllFines() throws RetriveResourceException {
		Transaction transaction = null;
		List<Fine> fines = null;

		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			fines = session.createCriteria(Fine.class).list();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return fines;
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

}
