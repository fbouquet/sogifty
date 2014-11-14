package com.sogifty.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sogifty.entity.User;
import com.sogifty.recommendation.CdiscountConfiguration;
import com.sogifty.recommendation.RecommendationEngine;
import com.sogifty.util.persistance.HibernateUtil;

@Path("hello")
public class HelloPerson {

	@GET
	@Path("/{who}")
	public Response helloPerson(@PathParam("who") String who) {
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		
//		/* Select users using HQL */
//		Query queryUser = session.createQuery("From User ");
//
//		List<User> resultUsers = queryUser.list();
//		System.out.println("You have " + resultUsers.size() + " user(s).");
//		for (User user : resultUsers) {
//			System.out.println("Next user: " + user);
//		}
//		
//		/* Select users using Criteria API (much better, no need of an other language!) */
//		Criteria criteria = session.createCriteria(User.class);
//		resultUsers = criteria.list();
//		System.out.println("You have " + resultUsers.size() + " user(s).");
//		for (User user : resultUsers) {
//			System.out.println("Next user: " + user);
//		}
		
		RecommendationEngine recommendationEngine = new RecommendationEngine();
		recommendationEngine.runEngine(new CdiscountConfiguration());
		
		return Response.status(200).entity("(Hibernate OK) \n Hello " + who).build();
	}
}
