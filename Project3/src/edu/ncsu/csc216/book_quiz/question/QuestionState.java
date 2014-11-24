package edu.ncsu.csc216.book_quiz.question;

import java.util.List;

import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.*;

public abstract class QuestionState {

	private static final int FRONT = 0;
	
	/** 
	 * Contains all questions that are unasked. Any new 
	 * questions the user creates are added to the end of this collection.
	 */
	private List<Question> waitingQuestions;
	
	/** 
	 * Contains all questions that have been asked. The last question in 
	 * this list is the currentQuestion. 
	 */
	private List<Question> askedQuestions;
	
	/**
	 * Reference to the current question that would be asked of the user 
	 * if the quiz is in the given state.
	 */
	private Question currentQuestion;
	
	/**
	 * Constructor
	 * 
	 * @param list
	 */
	public QuestionState(List<Question> list) {
		
	}

	/**
	 * Abstract method to process the user's answer. This method corresponds to 
	 * transitions in the FSM. Each concrete state (nested classes inside BookQuestions) 
	 * defines this method according to the transitions that go from that state. What 
	 * should happen in each concrete state is defined in UC7, S1.
	 * @param answer the user's answer
	 * @return appropriate response to the answer choice (i.e. "Correct!")
	 * @throws EmptyQuestionListExceptions if currentQuestion is null.
	 */
	public abstract String processAnswer(String answer);

	/**
	 * True if currentQuestion is not null or waitingQuestions is not empty.
	 * @return True if currentQuestion is not null or waitingQuestions is not empty.
	 */
	public boolean hasMoreQuestions() {
		
	}
	
	/**
	 * Returns question text of the current question
	 * @return question text
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public String getCurrentQuestionText() {
		
	}

	/**
	 * Returns all question choices of the current question
	 * @return array of four question choices
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public String[] getCurrentQuestionChoices() {
		
	}

	/**
	 * Returns the answer of the current question (a, b, c, or d case insensitive)
	 * @return question answer
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public String getCurrentQuestionAnswer() {
		
	}

	/**
	 * Returns the current question object
	 * @return current question
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public Question getCurrentQuestion() {
		
	}
	
	/**
	 * Sets currentQuestion to the next item in the waitingQuestions list, or null 
	 * if there are no more questions in the list. The currentQuestion is added to 
	 * the end of the askedQuestions list.
	 */
	public void nextQuestion() {
		
	}
	
	/**
	 * Adds the given question to the end of the waitingQuestions list. If 
	 * currentQuestion is null, implying there are no more questions of that type 
	 * to ask, nextQuestion() is executed because there is now a question to ask.
	 * @param q new question
	 */
	public void addQuestion(Question q) {
		
	}
	
	/**
	 * Returns a list of questions. The list of questions combines the 
	 * askedQuestions with the waitingQuestions. The askedQuestions are added to 
	 * the joint list first.
	 * @return the joint list of questions
	 */
	public List<Question> getQuestions() {
		
	}
}