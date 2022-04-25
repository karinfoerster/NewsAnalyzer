package newsanalyzer.ctrl;

import newsapi.NewsApi;
import newsapi.beans.Article;
import newsapi.beans.NewsReponse;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "7cca69bfbb4f4242aee753eb7ce2f5a9";

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO load the news based on the parameters

		List<Article> articles;

		NewsReponse newsReponse;

		try {
			newsReponse = newsApi.getNews();
			articles = newsReponse.getArticles();
			articles.forEach(article -> System.out.println(article.toString()));
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			return;
		}
		//TODO implement methods for analysis

		Article shortestAuthorName = null;

		System.out.println("\n\n\n***Analysis***");

		//sorts the name of provider by sum of articles
		Map<String, Long> nameCountMap = articles.stream().collect(Collectors.groupingBy(a-> a.getSource().getName(),
				Collectors.counting()));

		try {
			//this is to find the article with the shortest author name
			shortestAuthorName = articles.stream().filter(article -> article.getAuthor() != null)
					.min(Comparator.comparing(article -> article.getAuthor().length())).get();
		}catch (NoSuchElementException e) {
			System.out.println("Couldn't find an author");
		}

		//this is to sort the titles by length
		List<Article> sortedByTitleLength = articles.stream().sorted(Comparator.comparing(article -> article.getTitle()
				.length())).collect(Collectors.toList());
		Collections.reverse(sortedByTitleLength);

		//this is to sort the titles by alphabet
		List<Article> sortedAlphabetically = articles.stream().sorted(Comparator.comparing(Article::getTitle))
				.collect(Collectors.toList());

		//a
		System.out.println("\nNumber of articles: "+newsReponse.getTotalResults());

		//b
		if (newsReponse.getTotalResults() > 0) {
			System.out.println("\nMost articles are from: "+nameCountMap.entrySet().stream()
					.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());
		}

		//c
		try {
			System.out.println("\nShortest author name: "+shortestAuthorName.getAuthor());
		} catch (NullPointerException e) {
			System.out.println("Could not find shortest name of an author");
		}

		//d sorted by length
		System.out.println("\nTitles sorted by length: \n");
		sortedByTitleLength.forEach(article -> System.out.println(article.getTitle()));

		//d sorted by alphabet
		System.out.println("\nTitles sorted by alphabet: \n");
		sortedAlphabetically.forEach(article -> System.out.println(article.getTitle()));

		System.out.println("End process");
	}

	public Object getData() {
		
		return null;
	}
}
