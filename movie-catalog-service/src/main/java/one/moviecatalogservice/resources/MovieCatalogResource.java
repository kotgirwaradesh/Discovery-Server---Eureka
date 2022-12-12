package one.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import one.moviecatalogservice.models.CatalogItem;
import one.moviecatalogservice.models.Movie;
import one.moviecatalogservice.models.Rating;
import one.moviecatalogservice.models.UserRating;
import one.moviecatalogservice.service.MovieInfo;
import one.moviecatalogservice.service.RatingInfo;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MovieInfo movieinfo;
	
	@Autowired
	RatingInfo ratinginfo;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		/*List<CatalogItem> demo = new ArrayList<CatalogItem>();
		demo.add(new CatalogItem( "RRR", "demo",5));
		return demo;*/
		UserRating ratings = ratinginfo.getRating(userId);
		
		return ratings.getUserRating().stream().map(rating -> {
			return movieinfo.getCatalogItem(rating);
		})
		.collect(Collectors.toList());
		
		
	}
	
	

	
	
}
