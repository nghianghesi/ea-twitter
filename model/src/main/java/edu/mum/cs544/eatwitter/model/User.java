package edu.mum.cs544.eatwitter.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;
import edu.mum.cs544.eatwitter.service.TweetService;

@Entity
public class User {
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String username;
	private String password;
	private String name;
	private String email;
	private Instant createdAt;
	
	@OneToMany
	private List<User> friends = new ArrayList<User>();
	@OneToMany
	private Set<Role> roles = new HashSet<Role>();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getUsername() == null) ? 0 : this.getUsername().toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!User.class.isInstance(obj))
			return false;

		User other = (User) obj;		
		if (this.getUsername() == null) {
			if (other.getUsername() != null)
				return false;
		} else if (!this.getUsername().equalsIgnoreCase(other.getUsername()))
			return false;
		return true;
	}
	
	public long getId() {
		return this.id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFriends() {
		return Collections.unmodifiableList(friends);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
	
    public Set<Role> getRoles() {
        return roles;
    }

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}    
    
	public User() {}
	public User(UserPrincipal u) {
		this.id= u.getId();
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.password = u.getPassword();
	}
}
