package edu.mum.cs544.eatwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.Tweet;

public interface TweetRepository extends JpaRepository<AbstractTweet, Long>{
	@Query("SELECT TOP ?2 DISTINCT t FROM AbstractTweet t "
			+ "JOIN FETCH t.byUser "
			+ "JOIN User u ON u.id == ?1 "
			+ "JOIN u.friends f ON f.id == t.byUser.id "
			+ "ORDER BY t.date DESC")
	List<AbstractTweet> findRecentTweets(long currentUserId, int limit);
	
	@Query("SELECT TOP ?1 t FROM Tweet t "
			+ "JOINT FETCH t.byUser "
			+ "ORDER BY t.thumbStats DESC")
	List<Tweet> findHotTweets(int limit);	
}
