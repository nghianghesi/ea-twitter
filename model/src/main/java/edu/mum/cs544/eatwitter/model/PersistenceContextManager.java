package edu.mum.cs544.eatwitter.model;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class PersistenceContextManager {
	  @Produces
	  @RequestScoped
	  @PersistenceContext
	  public EntityManager entityManager;
	public void merge(Object entity) {
		this.entityManager.merge(entity);
	}
	
	public void persist(Object entity) {
		this.entityManager.persist(entity);
	}	
}
