package com.sogifty.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.sogifty.service.recommendation.CdiscountConfiguration;
import com.sogifty.service.recommendation.RecommendationEngine;

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
