package com.company.authentication;

import java.util.List;

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

import com.company.users.Member;
import com.company.utils.HibernateUtils;
import com.company.utils.SecureUtils;

@Path("/authentication")
public class Authentication {

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

			if (member.getToken().equals("")) {
				// issue a token and persist it into db
				token = issueToken(username);
				token1 = token + member.getId();
				member.getAccount().setToken(token1);
			} else {
				token1 = member.getToken() + member.getId();
				member.getAccount().setToken(token1);
			}
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
}
