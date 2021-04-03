package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.payload.request.*;
import com.example.demo.payload.response.*;
import com.example.demo.repository.*;
import com.example.demo.security.*;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.payload.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	FalcutyRepository falcutyRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping(value = "/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication); // provide secure for email and password
		String jwt = jwtUtils.generateJwtToken(authentication); // generate a token type jwt

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, userDetails.getAddress(), userDetails.getPhonenumber(),userDetails.getFalcuty_id()));
	}
	/*
	 * * Requests : - LoginRequest : {username,password} - SignupRequest :
	 * {username,email,password} *Responses : - JwtResponse :
	 * {token,type,id,username,email,roles} - Messageresponse : {message}
	 */

	@PostMapping(value = "/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		// TODO: process POST request
		
		// Check conditions
		 if (userRepository.existsByUsername(signupRequest.getUsername())) { // check if signup name which are inserted by user is already exists in database
			return ResponseEntity.badRequest().body(new MessageResponse("Error : Username is already taken!"));// unauthorize to create account
		}
		if (userRepository.existsByEmail(signupRequest.getEmail())) { // check if signup email which are inserted by user is already exists in database
			return ResponseEntity.badRequest().body(new MessageResponse("Error : Email is already taken!"));// unauthorize to create account
		}

		// Create new user's account
		
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()), signupRequest.getAddress(),
				signupRequest.getPhonenumber()); // initial a new user 
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>(); // initial a new set of roles (set almost the same with list)
		//Set Falcuty
		Falcuty falcuty = new Falcuty();// initial a new falcuty
		
		// set case
		switch (signupRequest.getFalcuty_id()) { // user insert falcuty
		case "SE":  
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SE) // system find falcuty name in database
					.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found")); // or if falcuty name not exist in database --> return error
			break;
		case "AI":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_AI)// system find falcuty name in database
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));// or if falcuty name not exist in database --> return error
			break;
		case "IB":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_IB)// system find falcuty name in database
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));// or if falcuty name not exist in database --> return error
			break;
		case "SA":
			falcuty = falcutyRepository.findByName(EFalcuty.FALCUTY_SA)// system find falcuty name in database
			.orElseThrow(() -> new RuntimeException("Error : Falcuty is not found"));// or if falcuty name not exist in database --> return error
			break;
		}
		user.setFalcuty(falcuty); // if found falcuty name in database --> set these user have that falcuty
		
		// Set Roles
		if (strRoles == null || strRoles.isEmpty()) { // if user not choose role type
			Role userRole = roleRepository.findByName(ERole.ROLE_GUEST) // system find role name in database
					.orElseThrow(() -> new RuntimeException("Error : Role is not found" + signupRequest.getRole())); // if role name not exist in database --> throw Error
			roles.add(userRole); // else, role name exist --> default set role = GUEST
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN) // similar with above
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(adminRole);
					break;
				case "coordinator":
					Role coordinatorRole = roleRepository.findByName(ERole.ROLE_MKT_COORDINATOR) // similar with above
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(coordinatorRole);
					break;
				case "manager":
					Role managerRole = roleRepository.findByName(ERole.ROLE_MKT_MANAGER) // similar with above
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(managerRole);
					break;
				case "student":
					Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT) // similar with above
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(studentRole);
					break;
				default:
					Role guestRole = roleRepository.findByName(ERole.ROLE_GUEST) // default set role = GUEST if register without choosing role
							.orElseThrow(() -> new RuntimeException("Error : Role is not found"));
					roles.add(guestRole);
				}
			});
		}
		user.setRoles(roles); // save role of user
		userRepository.save(user); // save all user informations
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
	}

}
