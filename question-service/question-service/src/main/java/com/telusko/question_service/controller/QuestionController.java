package com.telusko.question_service.controller;

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

import com.telusko.question_service.model.Question;
import com.telusko.question_service.model.QuestionWrapper;
import com.telusko.question_service.model.Response;
import com.telusko.question_service.service.QuestionService;



@RestController //only controlling the questions
@RequestMapping("question") //whatever request coming for question, this controller will handle it.
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@GetMapping("/")
	public String greet() {
		return "hello";
	}
	
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}
	
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
		return questionService.getQuestionsByCategory(category);
	}
	
	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}
	
	//generate the list of question
	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz
								(@RequestParam String categoryName, @RequestParam Integer numQuestions) { 
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}
	
	//get question based on the id
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds) {
		return questionService.getQuestionsFromId(questionIds);
	}
	
	//getting the score of the questions
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
		return questionService.getScore(responses);
	}
}






