package one.moviecatalogservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import one.moviecatalogservice.models.Rating;
import one.moviecatalogservice.models.UserRating;

@Service
public class RatingInfo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getUserRatingFallBackMethod")
	public UserRating getRating(String userId) {
		return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
	}
	
	public UserRating getUserRatingFallBackMethod(String userId){
		List<Rating> ratings = Arrays.asList(
				new Rating("",0)
				);
		return new UserRating(ratings);
		
	}

}
