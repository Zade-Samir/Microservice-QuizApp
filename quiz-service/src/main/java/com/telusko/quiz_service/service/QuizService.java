package com.telusko.quiz_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quiz_service.dao.QuizDao;
import com.telusko.quiz_service.feign.QuizInterface;
import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.model.Response;



@Service
public class QuizService {
	
	@Autowired
	QuizDao quizdao;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		//.getbody() -> only give list of integer in place of full list of body
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody(); 
		
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		
		quizdao.save(quiz); //saving the quiz
		
		return new ResponseEntity<>("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		
		Quiz quiz = quizdao.findById(id).get();
		List<Integer> questionIds = quiz.getQuestionIds(); //getting queId from database which is stored
		
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds); 

		
		return questions;
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		
		ResponseEntity<Integer>  score = quizInterface.getScore(responses);
		
		return score;
	}

}











