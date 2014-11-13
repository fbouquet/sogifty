package com.sogifty.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sogifty.entity.User;
import com.sogifty.util.persistance.HibernateUtil;
import com.sogifty.webservice.model.UserServiceModel;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	@POST
	public Response create(User user) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		if(user.getId() != null) {
			return Response.status(Status.NOT_ACCEPTABLE).entity("Cannot create a user with an existing id").build();
		}
		
		Transaction t = session.beginTransaction();
		Integer createdUserId = (Integer) session.save(user);
		t.commit();
		session.close();
		
		return Response.ok(new UserServiceModel(createdUserId)).build();
	}
}
