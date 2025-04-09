package com.telusko.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.Response;


@FeignClient("QUESTION-SERVICE") //to create this as client of feign
public interface QuizInterface {
	
	//generate the list of question
		@GetMapping("question/generate")
		public ResponseEntity<List<Integer>> getQuestionsForQuiz
									(@RequestParam String categoryName, @RequestParam Integer numQuestions);
		
		//get question based on the id
		@PostMapping("question/getQuestions")
		public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);
		
		//getting the score of the questions
		@PostMapping("question/getScore")
		public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}
