package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.*;
import com.example.demo.repository.*;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> getAllUser(){
		try {
			List<User> users = new ArrayList<>();
			userRepository.findAll().forEach(users ::add);
			return new ResponseEntity<>(users,HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<User> getUser(){
		return null;
	}
	
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		//TODO: process PUT request
		return null;
	}
	
	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String id) {
		//TODO: process DELETE request
		
		return null;
	}


}
