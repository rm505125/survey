package com.code.springboot.firstrestapi.survey.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SurveyResourceIT {

	// http://localhost:8080/surveys/Survey1/questions/question1

	String str = """
				{
			    "id": "Question1",
			    "description": "Most Popular Cloud Platform Today",
			    "options": [
			        "AWS",
			        "Azure",
			        "Google Cloud",
			        "Oracle Cloud"
			    ],
			    "correctAnswer": "AWS"
			}

						""";

	private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

	// for firing rest api
	@Autowired
	private TestRestTemplate restTemplate;

	// http://localhost:RANDOM_PORT/surveys/Survey1/questions/question1
	// dynamic port

	// {"id":"Question1","description":"Most Popular Cloud Platform
	// Today","options":["AWS","Azure","Google Cloud","Oracle
	// Cloud"],"correctAnswer":"AWS"}
	// [Content-Type:"application/json"

	@Test
	void retrieveQuestionById_basicScenario() throws JSONException {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(SPECIFIC_QUESTION_URL, String.class);

		String expectedResponse = """
				{"id":"Question1",
				"description":"Most Popular Cloud Platform Today",
				"correctAnswer":"AWS"
				}
				""";
		
		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		
//		assertEquals(expectedResponse.trim(), responseEntity.getBody());
//		System.out.println(responseEntity.getBody());
//		System.out.println(responseEntity.getHeaders());

	}
}
