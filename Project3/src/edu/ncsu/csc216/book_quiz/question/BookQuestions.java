/**
 * 
 */
package edu.ncsu.csc216.book_quiz.question;

import java.util.List;

import edu.ncsu.csc216.question_library.*;

/**
 * @author RoPanu
 * @author Devin Brenton
 */
public class BookQuestions {

	/** Number of questions correctly answered */
	private int numCorrectAnswers;
	
	/** Number of questions attempted */
	private int numAttemptQuests;
	
	/** Returned if question is correctly answered */
	public static final String CORRECT = "Correct!";
	
	/** Returned if question is incorrectly answered */
	public static final String INCORRECT = "Incorrect :(";
	
	/** Space between messages in the GUI */
	public static final String SEPARATOR = " ";
	
	/** Current state of the book quiz */
	private QuestionState state;
	
	/** State when user is answering elementary questions */
	private QuestionState elemState;
	
	/** State when user is answering standard questions */
	private QuestionState stdState;
	
	/** State when user is answering advanced questions */
	private QuestionState advState;
	
	/**
	 * Constructor - takes three Lists to initialize its inner concrete state classes.
	 */
	public BookQuestions(List<StandardQuestion> standard, List<ElementaryQuestion> elementary, 
			List<AdvancedQuestion> advanced) {
		
	}
	
	/**
	 * True if there are any more questions remaining in the quiz.
	 * @return True if there are any more questions remaining in the quiz
	 */
	public boolean hasMoreQuestions() {
		
	}
	
	/**
	 * Returns the text portion of the currentQuestion
	 * @return text of the question
	 */
	public String getCurrentQuestionText() {
		
	}
	
	/**
	 * Returns the four questions choices of the currentQuestion
	 * @return array of four possible question choices
	 */
	public String[] getCurrentQuestionChoices() {
		
	}
	
	/**
	 * Processes the answer submitted by the user. BookQuestions relies 
	 * on its current state to handle the processing.
	 * @param answer answer to be processed
	 * @return the appropriate response depending on the answer (i.e. "Correct!")
	 * @throws EmptyQuestionListException if the answer cannot be processed.
	 */
	public String processAnswer(String answer) {
		
	}
	
	/**
	 * Returns the number of questions that have been correctly answered.
	 * @return number of correct answers
	 */
	public int getNumCorrectQuestions() {
		
	}
	
	/**
	 * Returns the number of questions that have been attempted so far
	 * @return number of attempted questions
	 */
	public int getNumAttemptedQuestions() {
		
	}
	
	/**
	 * Adds the standard question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addStandardQuestion(StandardQuestion quest) {
		
	}
	
	/**
	 * Adds the elementary question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addElementaryQuestion(ElementaryQuestion quest) {
		
	}
	
	/**
	 * Adds the advanced question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addAdvancedQuestion(AdvancedQuestion quest) {
		
	}
	
	/**
	 * Returns a collection of standard questions through delegation to the appropriate state object
	 * @return collection of standard questions
	 */
	public List<Question> getStandardQuestions() {
		
	}
	
	/**
	 * Returns a collection of elementary questions through delegation to the appropriate state object
	 * @return collection of elementary questions
	 */
	public List<Question> getElementaryQuestions() {
		
	}
	
	/**
	 * Returns a collection of advanced questions through delegation to the appropriate state object
	 * @return collection of advanced questions
	 */
	public List<Question> getAdvancedQuestions() {
		
	}
	
//-----------------------------Inner Classes For FSM--------------------------//
	
	private class ElementaryQuestionState extends QuestionState {

		private int attempts;
		
		private int numCorrectInRow;
		
		public ElementaryQuestionState(List<Question> list) {
			super(list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String processAnswer(String answer) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class StandardQuestionState extends QuestionState {

		private int numCorrectInRow;
		
		public StandardQuestionState(List<Question> list) {
			super(list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String processAnswer(String answer) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private class AdvancedQuestionState extends QuestionState {

		public AdvancedQuestionState(List<Question> list) {
			super(list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public String processAnswer(String answer) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
