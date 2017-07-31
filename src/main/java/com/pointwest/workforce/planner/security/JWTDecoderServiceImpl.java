package com.pointwest.workforce.planner.security;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTDecoderServiceImpl implements JWTDecoderService {
	private static final Logger log = LoggerFactory.getLogger(JWTDecoderServiceImpl.class);
	
	@Override
	public Map<String, Claim> decode(String token) throws JWTDecodeException{
		log.debug("MCI >> decode");
		Map<String, Claim> claims = null;
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    claims = jwt.getClaims();
		    
		} catch (JWTDecodeException jwte){
			log.error("An error of type " + jwte.getClass() + " occurred while trying to run decode(). "
					+ "Invalid on decoding token.");
			throw jwte;
		}
		log.debug("MCO << decode");
		return claims;
	}
	
	@Override
	public TokenUser getTokenUser(String token) {
		try {
			Map<String, Claim> claims = this.decode(token);
			String username = claims.get("usr").asString();
			String role = claims.get("rle").asString();
		    return new TokenUser(username, role);
		} catch(Exception e) {
			log.debug("Security exception @getTokenUser");
			throw new SecurityException();
		}
	}
	    
	@Override
	public Boolean isTokenExpired(String token) {
		try {
	        Map<String, Claim> claims = this.decode(token);
//		    log.debug("this is the token expiration : " + claims.get("exp").asDate());
		    final Date expiration = claims.get("exp").asDate();
	        return expiration.before(new Date());
		} catch(Exception e) {
			log.debug("Security exception @isTokenExpired");
			throw new SecurityException();
		}
    }
	
	@Override
	
	public Boolean isValidToken(String token) {
		try {
			if(getTokenUser(token) != null && !isTokenExpired(token)) {
				return true;
			}
			else {
				return false;
			}
		} catch(SecurityException se) {
			log.debug("Security exception received token not valid");
			return false;
		} catch(Exception e) {
			log.debug("token not valid exception received: " + e.getMessage());
			return false;
		}
	}
}
