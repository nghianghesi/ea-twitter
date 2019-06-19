package edu.mum.cs544.eatwitter.dto;

import edu.mum.cs544.eatwitter.api.security.UserPrincipal;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    
    private UserPrincipal userPrincipal;

    public JwtAuthenticationResponse(String accessToken,UserPrincipal userPrincipal) {
        this.accessToken = accessToken;
        this.userPrincipal = userPrincipal;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	public UserPrincipal getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(UserPrincipal userPrincipal) {
		this.userPrincipal = userPrincipal;
	}  
    
}
