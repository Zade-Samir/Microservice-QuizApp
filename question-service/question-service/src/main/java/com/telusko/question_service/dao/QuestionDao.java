package com.telusko.question_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.telusko.question_service.model.Question;


@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
	
	List<Question> findByCategory(String category);
	
	//it can easily work for postgreSQL
	//SELECT * FROM question WHERE category = :category ORDER BY RANDOM() LIMIT :numQ
	//List<Question> findRandomQuestionsByCategory(String category, Integer numQ);

	//some time to get the things you have to break your programming
	//get the things by yourself
	@Query(value = "SELECT q.id FROM question q WHERE q.category = :category ORDER BY RAND()", 
																				nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(@Param("category") String category);

	
}
