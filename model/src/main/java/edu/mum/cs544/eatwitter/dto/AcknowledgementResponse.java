package edu.mum.cs544.eatwitter.dto;

import java.util.UUID;

public class AcknowledgementResponse {
	private UUID id;
	
	public AcknowledgementResponse(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}	
}
