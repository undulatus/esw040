package com.pointwest.workforce.planner.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class RoleAccessDeniedHandler implements AccessDeniedHandler {

	public RoleAccessDeniedHandler() {
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException)
                throws IOException, ServletException {

		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have access for this request");

	}

}