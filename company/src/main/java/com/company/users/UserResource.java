package com.company.users;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.company.authentication.Secured;
import com.company.catalogue.Fine;
import com.company.exception.RetriveResourceException;
import com.google.gson.Gson;


@Path("members")
public class UserResource {
	@Context
	SecurityContext securityContext;
	
	@POST
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public Member createMember(String json) {
		Member member = new Gson().fromJson(json, Member.class);
		try {
			new UserService().addMember(Objects.requireNonNull(member));
		} catch (RetriveResourceException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	@GET
	@Path("member/fines/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured
	public List<Fine> checkForFine(@PathParam("userId") int userId) {
		List<Fine> fines = null;
		try {
			fines = new UserService().checkForFine(userId);
		} catch (RetriveResourceException e) {
			e.printStackTrace();
		}
		return fines;
	}
}
