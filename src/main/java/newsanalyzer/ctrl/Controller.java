package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

public class Controller {

	public static final String APIKEY = "7cca69bfbb4f4242aee753eb7ce2f5a9";

	public void process(String query, Category category) {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO load the news based on the parameters
		NewsApi newsApi;
		if (category != null) {
			newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setQ(query)
					.setEndPoint(Endpoint.TOP_HEADLINES)
					.setSourceCountry(Country.at)
					.setSourceCategory(category)
					.createNewsApi();
		} else {
			newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setQ(query)
					.setEndPoint(Endpoint.TOP_HEADLINES)
					.setSourceCountry(Country.at)
					.createNewsApi();
		}



		//TODO implement methods for analysis

		System.out.println("End process");
	}
	

	public Object getData() {
		
		return null;
	}
}
