package com.code.springboot.firstrestapi.survey.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.code.springboot.firstrestapi.survey.Question;
import com.code.springboot.firstrestapi.survey.Survey;
import com.code.springboot.firstrestapi.survey.service.SurveyService;

@RestController
public class SurveyResource {

	private SurveyService service;

	public SurveyResource(SurveyService service) {
		super();
		this.service = service;
	}

	// Retrieve all surveys
	// GET/surveys
	// url : /surveys
	// for getting this url we can do that with two mapping.
	// @RequestMapping("/surveys")
	// or @GetMapping("/surveys")
	@GetMapping("/surveys")
	public List<Survey> retreiveAllSurveys() {
		return service.retreiveAllSurveys();

	}

	// Retrieve specific Survey
	// GET/surveys/{surveyId}
	@GetMapping("/surveys/{surveyId}")
	public Survey getSurveyById(@PathVariable String surveyId) {
		Survey survey = service.getsurveyById(surveyId);
		if (survey == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return survey;
	}

	/*
	 * Survey Questions REST API 1. Retrieve Survey Questions
	 * GET/surveys/{surveyId}/questions 2. Retrieve specific Survey Questions
	 * GET/surveys/{surveyId}/questions/{questionsId}
	 * 
	 * 
	 */

	// Retrieve Survey Questions
	// GET/surveys/{surveyId}/questions

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveAllQuestions(@PathVariable String surveyId) {
		List<Question> questions = service.retrieveAllQuestions(surveyId);

		if (questions == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return questions;
	}
	
	@GetMapping("/surveys/{surveyId}/questions/{questionsId}")
	public Question retrieveQuestionById(@PathVariable String surveyId, @PathVariable String questionsId) {
		Question question = service.retrieveQuestionById(surveyId,questionsId);

		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return question;
	}

}
