package edu.mum.cs544.eatwitter.model;

public enum ThumbType {
	Up(1), Down(-1), Neutral(0);
	
	private int value = 0;
	private ThumbType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
