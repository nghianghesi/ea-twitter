package eatwitter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Thumb {
	@Id
	@GeneratedValue
	private long id;
	
	private int type;
	
	@ManyToOne 
	@JoinColumn(name="by_user")
	private User byUser;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setType(ThumbType type) {
		this.type = type.getValue();
	}
	
	public boolean isType(ThumbType type) {
		return this.type == type.getValue();
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
		this.type = type.getValue();
	};
}
