package com.code.springboot.firstrestapi.survey.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		Question question = service.retrieveQuestionById(surveyId, questionsId);

		if (question == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return question;
	}

	/*
	 * Add Survey Question POST/surveys/{surveyId}/questions
	 */

	// POST/surveys/{surveyId}/questions
	@RequestMapping(value = "surveys/{surveyId}/questions", method = RequestMethod.POST)
	public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {

		String questionId = service.addNewSurveyQuestion(surveyId, question);

		// returning location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{questionId}").buildAndExpand(questionId).toUri();
		return ResponseEntity.created(location).build();
	}

	@RequestMapping(value = "surveys/{surveyId}/questions/{questionsId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQuestionById(@PathVariable String surveyId, @PathVariable String questionsId) {
		service.deleteQuestionById(surveyId, questionsId);
		//return ResponseEntity.noContent().build(); //204
		return ResponseEntity.accepted().build(); //202
	}
	
}
