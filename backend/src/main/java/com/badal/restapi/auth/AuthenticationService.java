package com.badal.restapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.badal.restapi.config.JwtService;
import com.badal.restapi.dto.AuthenticationRequest;
import com.badal.restapi.dto.AuthenticationResponse;
import com.badal.restapi.dto.RegisterRequest;
import com.badal.restapi.user.Role;
import com.badal.restapi.user.User;
import com.badal.restapi.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {

		var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
				.build();

		// save user to db

		userRepository.save(user);

		// generate token
		var jwtToken = this.jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// authenticate and if not authenitcated throw exception
		try {

			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (Exception e) {
			System.out.println(e);
		}
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		// save user to db
		// userRepository.save(user);

		// generate token
		var jwtToken = this.jwtService.generateToken(user);

		return AuthenticationResponse.builder().token(jwtToken).build();
	}

}
