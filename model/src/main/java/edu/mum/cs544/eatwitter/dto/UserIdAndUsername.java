package edu.mum.cs544.eatwitter.dto;

public interface UserIdAndUsername {
	public long getId();
	public String getUsername();
	
	public static class SimpleUserIdAndUsername implements UserIdAndUsername{
		private long id;
		private String username;
		public SimpleUserIdAndUsername(long id, String username) {
			this.id = id;
			this.username = username;
		}
		@Override
		public long getId() {
			return id;
		}
		@Override
		public String getUsername() {
			return username;
		}
		
	}
}
