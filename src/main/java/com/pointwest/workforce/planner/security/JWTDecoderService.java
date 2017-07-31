package com.pointwest.workforce.planner.security;

import java.util.Map;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;

public interface JWTDecoderService {
	
	public Map<String, Claim> decode(String token) throws JWTDecodeException;

	Boolean isValidToken(String token);

	public Boolean isTokenExpired(String token) throws SecurityException;

	TokenUser getTokenUser(String token) throws SecurityException;
}
