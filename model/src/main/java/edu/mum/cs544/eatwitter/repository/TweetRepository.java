package edu.mum.cs544.eatwitter.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.mum.cs544.eatwitter.model.AbstractTweet;
import edu.mum.cs544.eatwitter.model.Tweet;

public interface TweetRepository extends JpaRepository<AbstractTweet, Long>{
	@Query("SELECT DISTINCT t FROM AbstractTweet t "
			+ "JOIN FETCH t.byUser "
			+ "JOIN User u ON u.id = ?1 "
			+ "LEFT JOIN u.friends f "
			+ "WHERE (f.id = t.byUser.id OR u.id = t.byUser.id) "
			+ "ORDER BY t.date DESC ")
	Slice<AbstractTweet> findRecentTweets(long currentUserId, Pageable pageable);
	
	@Query("SELECT t FROM Tweet t "
			+ "JOIN FETCH t.byUser "
			+ "ORDER BY t.thumbStats DESC ")
	Slice<Tweet> findHotTweets(Pageable pageable);
	
	
}
