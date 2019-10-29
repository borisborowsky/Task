package com.company.authentication;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.company.catalogue.BookUnit;
import com.company.catalogue.CatalogService;
import com.company.catalogue.Fine;
import com.company.exception.NoFineException;
import com.company.exception.RetriveResourceException;
import com.company.users.Member;
import com.company.utils.HibernateUtils;
import com.company.utils.SecureUtils;

@Path("/authentication")
public class Authentication {

	/**
	 * This function issue a token for the user if it's 
	 * his first login or fetch the user token from the database.
	 * @param credentials The user credentials username and password
	 * @return token
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		try {
			// Authenticate the user using the credentials provided
			// and issue a token
		
			String token = authenticate(username, password);

			// Return the token on the response
			return Response.ok(token).build();

		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}	
	
	/**
	 * This function check the credentials of the user. If the user log's for the
	 * first time it issue a new token and persist it in database, otherwise
	 * the function retrieve the user's token from db. Also it call a sub Function
	 * checkForBookDueDate() which is described in its own comment.
	 * 
	 * @param username The principal(user) username.
	 * @param password The principal(user) password.
	 * @return session token
	 * @throws IllegalArgumentException
	 * @throws NoFineException 
	 */

	@SuppressWarnings("unchecked")
	private String authenticate(String username, String password) throws IllegalArgumentException {
		Transaction transaction = null;
		String token = "";
		String token1 = "";
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			@SuppressWarnings("deprecation")
			List<Member> members = session.createCriteria(Member.class)
					.add(Restrictions.eq("account.username", username))
					.add(Restrictions.eq("account.password", password)).list();

			Member member = members.get(0);

			if (member.getToken() == null) {
				// issue a token and persist it into db
				token = issueToken(username);
				token1 = token + member.getId();
				member.getAccount().setToken(token1);
			} else {
				token1 = member.getToken();
				member.getAccount().setToken(token1);
			}
			Fine fine = null;
			try {
				fine = checkForBookDueDate(member, session);
			} catch (NullPointerException e) {
//				throw new NoFineException("No fine's found for user", e);
				e.printStackTrace();
			}
			
			if (fine != null)
				session.save(fine);	

			session.merge(member);
			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return token1;
	}

	private String issueToken(String username) {
		return SecureUtils.generateString();
	}

	
	/**
	 * This function checks if the authenticated user is having a
	 * overdue books and if there are such books a new fine's are created.
	 * The sum of the fine is calculated by default (fine value) * days overdue.
	 * If no new fine is generated a NullPointerException is thrown.
	 * 
	 * @param member The authenticating user.
	 * @param session The database session.
	 * @return A fine if the user have overdue books.
	 * @throws NullPointerException
	 */
	private Fine checkForBookDueDate(Member member, Session session) throws NullPointerException {
		List<BookUnit> books = null;

		try {
			books = new CatalogService().getBorrowedBooks(member.getId());
		} catch (RetriveResourceException e) {
			e.printStackTrace();
		}
		Fine fine = null;
		for (BookUnit book : books) {
			Date borrowedDate = book.getBorrowDate();
			Date returnDate = book.getReturnDate();
			if (borrowedDate.after(returnDate) && !book.isThereFineOnTheBook()) {
				long accumulatedFine = calculateFine(borrowedDate, returnDate);
				fine = new Fine(new Date(), member, Fine.DEFAULT_FINE_SUM * accumulatedFine);
				book.setThereFineOnTheBook(true);
				session.merge(book);
			}
		}

		if (fine == null)
			throw new NullPointerException();

		fine.setMember(member);
		member.getFines().add(fine);

		return fine;
	}

	/**
	 * This function calculates the number of days the book is overdue.
	 * 
	 * @param borrowDate the date in which the book was borrowed
	 * @param returnDate the date in which the book must be returned
	 * @return returnDate time - borrowedDate time
	 */
	private long calculateFine(Date borrowedDate, Date returnDate) {
		long dateDifference = 0;
		dateDifference = borrowedDate.getTime() - returnDate.getTime();
		System.out.println("Difference " + dateDifference);
		System.out.println("Days: " + TimeUnit.MILLISECONDS.toDays(dateDifference));
		return TimeUnit.MILLISECONDS.toDays(dateDifference);
	}
}
