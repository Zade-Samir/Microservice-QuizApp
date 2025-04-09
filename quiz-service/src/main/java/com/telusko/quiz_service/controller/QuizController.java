package com.telusko.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.QuizDto;
import com.telusko.quiz_service.model.Response;
import com.telusko.quiz_service.service.QuizService;


@RestController
@RequestMapping("quiz")
public class QuizController {
	
	
	@Autowired
	QuizService quizservice;
	
	//create the quiz table with quiz id in it
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
		return quizservice.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle()); 
	}
	
	//get the quiz question by providing the quiz id
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
		return quizservice.getQuizQuestions(id);
	}
	
	//give the total score for the responses
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,
											@RequestBody List<Response> responses) {
		return quizservice.calculateResult(id, responses);
	}
}













