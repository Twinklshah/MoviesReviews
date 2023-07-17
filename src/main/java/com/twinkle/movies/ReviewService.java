package com.twinkle.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Reviews createReview(String reviewBody, String imdbId){
        Reviews reviews=reviewRepository.insert(new Reviews(reviewBody));

        mongoTemplate.update(Movies.class).matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(reviews)).first();

        return reviews;
    }
}
