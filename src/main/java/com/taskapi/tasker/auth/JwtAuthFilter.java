package com.taskapi.tasker.auth;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.taskapi.tasker.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor

public class JwtAuthFilter extends OncePerRequestFilter
{

	private final JwtService jwtService;

	private final UserService userService;

	@Override

	protected void doFilterInternal(

			@NonNull HttpServletRequest request,

			@NonNull HttpServletResponse response,

			@NonNull FilterChain chain

	) throws ServletException, IOException
	{

		final String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer "))
		{

			chain.doFilter(request, response);

			return;

		}

		final String token = header.substring(7);

		try
		{

			final String username = jwtService.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{

				var userDetails = userService.loadUserByUsername(username);

				if (jwtService.isValid(token, userDetails))
				{

					var authToken = new UsernamePasswordAuthenticationToken(

							userDetails, null, userDetails.getAuthorities()

					);

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);

				}

			}

		} catch (Exception ignored)
		{

			// Invalid token — continue as unauthenticated

		}

		chain.doFilter(request, response);

	}

}
