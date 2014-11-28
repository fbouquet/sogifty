package com.sogifty.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.recommendation.CdiscountConfiguration;
import com.sogifty.service.recommendation.GiftsFetcher;

@Path("hello")
public class HelloPerson {

	private static final Logger logger = Logger.getLogger(HelloPerson.class);
	
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
		
		GiftsFetcher fetcher = new GiftsFetcher(new CdiscountConfiguration());
		try {
			Tag tag = new Tag();
			tag.setLabel("ballon rugby");
			List<Gift> gifts = fetcher.fetchGifts(tag);
			for (Gift gift : gifts) {
				logger.debug(gift.toString());
			}
		} catch (SogiftyException e) {
			e.printStackTrace();
		}
		
		return Response.status(200).entity("(Hibernate OK) \n Helloooo " + who).build();
	}
}
