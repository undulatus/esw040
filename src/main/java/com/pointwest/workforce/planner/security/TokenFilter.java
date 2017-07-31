package com.pointwest.workforce.planner.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	JWTDecoderService jwtDecoderService;
	
	@Value("${token.request.header}")
	private String TOKENHEADER;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = request.getHeader(TOKENHEADER);
        
        if(jwtDecoderService.isValidToken(token) == false){
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token Unauthorized");
            //throw new SecurityException("invalid token");
        } else {
            TokenUser tokenUser = jwtDecoderService.getTokenUser(token);
            
            //Authentication auth = new UsernamePasswordAuthenticationToken(tokenUser.getUsername(), tokenUser.getRole());
            List<Authority> authorities = new ArrayList<Authority>();
            Authority authority = new Authority();
            authority.setAuthority(tokenUser.getRole());
            
            authorities.add(authority);
            Authentication auth = new UsernamePasswordAuthenticationToken(tokenUser.getUsername(), tokenUser.getRole(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);      
            
            filterChain.doFilter(request, response);
        }              
        
    }

}