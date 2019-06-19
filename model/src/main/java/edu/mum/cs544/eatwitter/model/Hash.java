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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( this.getHash() == null ? 0 : this.getHash().toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!Hash.class.isAssignableFrom(obj.getClass()))
			return false;
		Hash other = (Hash) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!this.getHash().equalsIgnoreCase(other.getHash()))
			return false;
		return true;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getId() {
		return id;
	}	
}
