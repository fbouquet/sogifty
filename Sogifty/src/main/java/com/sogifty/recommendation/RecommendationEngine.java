package com.sogifty.recommendation;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecommendationEngine {
	
	private final String TEST_TAG = "tolkien";
	
	private final String USER_AGENT = "Mozilla";
	private static final Logger logger = Logger.getLogger(RecommendationEngine.class);

	public RecommendationEngine() {
		
	}
	
	public void runEngine(Configuration conf) {
		logger.info("Running the recommendation engine.");
		Document doc;
		try {
			doc = Jsoup.connect(conf.getSearchUrl(TEST_TAG)).userAgent(USER_AGENT).get();
			
			Element productList = doc.getElementById(conf.getIdProductList());
			Elements products = productList.getElementsByClass(conf.getClassProduct());
			
			for (Element product : products) {
				String debugLine = new String("\n");
				
				Elements title = product.getElementsByClass(conf.getClassTitle());
				debugLine += "Title : " + title.get(0).text() + "\n";
				
				Elements description = product.getElementsByClass(conf.getClassDescription());
				debugLine += "Description : " + description.get(0).ownText() + "\n";
				
				Elements price = product.getElementsByClass(conf.getClassPrice());
				debugLine += "Price : " + price.get(0).text() + "\n";
				
				Elements picture = product.getElementsByClass(conf.getClassPicture());
				debugLine += "Picture : " + picture.get(0).attr(conf.getAttrPicture())+ "\n";
				
				logger.debug(debugLine);
			}
		} catch (IOException e) {
			logger.fatal("Exception when running the recommendation engine: ", e);
		}
	}
}
