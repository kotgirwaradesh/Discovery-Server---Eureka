package one.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import one.moviecatalogservice.models.CatalogItem;
import one.moviecatalogservice.models.Movie;
import one.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getCatalogFallBackMethod")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId() , Movie.class);
		return new CatalogItem(movie.getName(), "demo", rating.getRating());
	}
	
	public CatalogItem getCatalogFallBackMethod(Rating rating){
		return new CatalogItem("No Movie found", "", rating.getRating());
	}
}
