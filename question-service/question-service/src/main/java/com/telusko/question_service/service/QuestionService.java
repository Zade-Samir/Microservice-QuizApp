package com.telusko.question_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.question_service.dao.QuestionDao;
import com.telusko.question_service.model.Question;
import com.telusko.question_service.model.QuestionWrapper;
import com.telusko.question_service.model.Response;


@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		
		try {
			return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		
	}

	public ResponseEntity<String> addQuestion(Question question) {
		
		questionDao.save(question);
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
		
		//get the random questions from  questiondao
		List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName);
				
		//manually limit to numQ
		if (questions.size() > numQuestions) {
			questions = questions.subList(0, numQuestions);
		}
		
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}
	
public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>(); //getting the question from questionIds
		
		//getting the question from questionIds using questionDao
		for (Integer id : questionIds) {
			questions.add(questionDao.findById(id).get());
		}
		
		//storing the question from questions list into wrapper
		for (Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			
			wrappers.add(wrapper);
		}
		
		return new ResponseEntity<>(wrappers, HttpStatus.OK); 
	}

public ResponseEntity<Integer> getScore(List<Response> responses) {
	
	int right = 0;
	for (Response response : responses) {
		
		Question question = questionDao.findById(response.getId()).get(); //.get() for optional
		
		if (response.getResponse().equals(question.getRightAnswer())) {
			right ++;
		}
	}
	return new ResponseEntity<>(right, HttpStatus.OK);
}
	
	
}







