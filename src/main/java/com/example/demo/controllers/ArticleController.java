package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Article;
import com.example.demo.models.EFalcuty;
import com.example.demo.models.Falcuty;
import com.example.demo.models.User;
import com.example.demo.payload.request.ArticleRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.FalcutyRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/article")
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired 
	FalcutyRepository falcutyRepository;
	
	@Autowired 
	UserRepository userRepository;

	@GetMapping(value = "/all")
	public ResponseEntity<List<Article>> getAllArticle(@RequestParam(required = false) String title) {
		try {
			List<Article> articles = new ArrayList<Article>();
			if (title == null)
				articleRepository.findAll().forEach(articles::add);
			else
				articleRepository.findByTitleContaining(title).forEach(articles::add);
			if (articles.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			// TODO: handle exception
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
		Optional<Article> articleData = articleRepository.findById(id);
		if (articleData.isPresent())
			return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
		return new ResponseEntity<>(articleRequest,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteArticle(@RequestParam String title) {
		String message = "";
		try {
			boolean isvalidarticle = articleRepository.existsByTitle(title);
			if (isvalidarticle) {
				articleRepository.deleteByTitle(title);
				message = "deleted successfully";
				return new ResponseEntity<> (message, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<> (message, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			message = e.getMessage();
			return new ResponseEntity<> (message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
