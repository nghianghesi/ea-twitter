package edu.mum.cs544.eatwitter.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hash {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String hash;	
	
	@OneToMany	
	private List<AbstractTweet> topTweets = new ArrayList<AbstractTweet>();	

}
