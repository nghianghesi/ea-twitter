package eatwitter.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hash {
	@Id
	@GeneratedValue
	private long id;
	
	private String hash;	
	
	@OneToMany	
	private List<AbstractTweet> topTweets = new ArrayList<AbstractTweet>();	

}
