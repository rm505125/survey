package com.code.springboot.firstrestapi.survey.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.code.springboot.firstrestapi.survey.Question;
import com.code.springboot.firstrestapi.survey.Survey;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);
	}

	public List<Survey> retreiveAllSurveys() {
		return surveys;
	}

	public Survey getsurveyById(String surveyId) {

		Predicate<? super Survey> predicate = survey -> survey.getId().equals(surveyId);

		Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();

		if (optionalSurvey.isEmpty()) {
			return null;
		}
		return optionalSurvey.get();
	}

	public List<Question> retrieveAllQuestions(String surveyId) {
		// TODO Auto-generated method stub

		Survey survey = getsurveyById(surveyId);
		if (survey == null) {
			return null;
		}
		return survey.getQuestions();

	}

	public Question retrieveQuestionById(String surveyId, String questionsId) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestion = retrieveAllQuestions(surveyId);
		if (surveyQuestion == null) {
			return null;
		}
		Predicate<? super Question> predicate = question -> question.getId().equals(questionsId);
		Optional<Question> optionalQuestion = surveyQuestion.stream().filter(predicate).findFirst();
		if (optionalQuestion.isEmpty()) {
			return null;
		}
		return optionalQuestion.get();
	}

	public String addNewSurveyQuestion(String surveyId, Question question) {
		// TODO Auto-generated method stub
		List<Question> questions = retrieveAllQuestions(surveyId);

		// generationg random id
		question.setId(generateRandomId());
		questions.add(question);
		return question.getId();
	}

	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}

	public String deleteQuestionById(String surveyId, String questionsId) {
		// TODO Auto-generated method stub
		List<Question> questions = retrieveAllQuestions(surveyId);

		if(questions == null) {
			return null;
		}
		Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionsId);
		boolean removed = questions.removeIf(predicate);
		if(!removed) {
			return null;
		}
		return questionsId;
	}

	public void updateQuestionById(String surveyId, String questionsId, Question question) {
		List<Question> questions = retrieveAllQuestions(surveyId);
		questions.removeIf(q -> q.getId().equalsIgnoreCase(questionsId));  
		
		
		questions.add(question);
		
		
	}

}
