package com.taskapi.tasker.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskapi.tasker.auth.dto.AuthResponse;
import com.taskapi.tasker.auth.dto.LoginRequest;
import com.taskapi.tasker.auth.dto.RegisterRequest;
import com.taskapi.tasker.entity.User;
import com.taskapi.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor

public class AuthService
{

	private final UserRepository userRepo;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final AuthenticationManager authManager;

	public AuthResponse register(RegisterRequest req)
	{

		if (userRepo.existsByUsername(req.username()))
		{

			throw new IllegalArgumentException("Username already taken: " + req.username());

		}

		if (userRepo.existsByEmail(req.email()))
		{

			throw new IllegalArgumentException("Email already registered: " + req.email());

		}

		var user = User.builder()

				.username(req.username())

				.email(req.email())

				.password(passwordEncoder.encode(req.password()))

				.build();

		userRepo.save(user);

		return new AuthResponse(jwtService.generate(user), user.getUsername(), user.getEmail());

	}

	public AuthResponse login(LoginRequest req)
	{

		// Throws AuthenticationException if credentials are wrong

		authManager.authenticate(

				new UsernamePasswordAuthenticationToken(req.username(), req.password())

		);

		var user = userRepo.findByUsername(req.username())

				.orElseThrow(() -> new RuntimeException("User not found after successful auth"));

		return new AuthResponse(jwtService.generate(user), user.getUsername(), user.getEmail());

	}

}