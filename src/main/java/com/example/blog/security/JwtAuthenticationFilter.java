package com.example.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.blog.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestToken = request.getHeader("Authorization");
		String username = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			username=jwtUtil.extractUsername(token);
		}
		else {
			System.out.println("Token is null or does not begin with Bearer");
		}
		
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			if(username!=null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if(this.jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			else if(requestToken!=null) {
				throw new ServletException("Invalid jwt token");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
