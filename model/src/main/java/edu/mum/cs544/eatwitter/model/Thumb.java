package edu.mum.cs544.eatwitter.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Thumb {
	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	private ThumbType type;
	
	@ManyToOne 
	@JoinColumn(name="by_user")
	private User byUser;

	public ThumbType getType() {
		return type;
	}

	public void setType(ThumbType type) {
		this.type = type;
	}	
	
	public boolean isType(ThumbType type) {
		return this.type == type;
	}		

	public User getByUser() {
		return byUser;
	}

	public void setByUser(User byUser) {
		this.byUser = byUser;
	}
	
	public Thumb() {};
	public Thumb(User by, ThumbType type) {
		this.byUser = by;
		this.type = type;
	};
}
