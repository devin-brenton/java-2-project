/**
 * 
 */
package edu.ncsu.csc216.book_quiz.question;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.question_library.*;
import edu.ncsu.csc216.book_quiz.util.*;

/**
 * Implements a Finite State Machine for question sequencing, additions, and transitions.
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
	public static final String INCORRECT = "Incorrect!";
	
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
	 * @param standard list of standard quiz questions
	 * @param elementary list of elementary quiz questions
	 * @param advanced list of advanced quiz questions
	 */
	public BookQuestions(List<StandardQuestion> standard, List<ElementaryQuestion> elementary, 
			List<AdvancedQuestion> advanced) {
		elemState = new ElementaryQuestionState(elementary);
		stdState = new StandardQuestionState(standard);
		advState = new AdvancedQuestionState(advanced);
		
		state = stdState;
		
		numCorrectAnswers = 0;
		numAttemptQuests = 0;
	}
	
	/**
	 * True if there are any more questions remaining in the quiz.
	 * @return True if there are any more questions remaining in the quiz
	 */
	public boolean hasMoreQuestions() {
		return state.hasMoreQuestions();
	}
	
	/**
	 * Returns the text portion of the currentQuestion
	 * @return text of the question
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public String getCurrentQuestionText() throws EmptyQuestionListException {
		return state.getCurrentQuestionText();
	}
	
	/**
	 * Returns the four questions choices of the currentQuestion
	 * @return array of four possible question choices
	 * @throws EmptyQuestionListException if currentQuestion is null
	 */
	public String[] getCurrentQuestionChoices() throws EmptyQuestionListException {
		return state.getCurrentQuestionChoices();
	}
	
	/**
	 * Processes the answer submitted by the user. BookQuestions relies 
	 * on its current state to handle the processing.
	 * @param answer answer to be processed
	 * @return the appropriate response depending on the answer (i.e. "Correct!")
	 * @throws EmptyQuestionListException if the answer cannot be processed.
	 */
	public String processAnswer(String answer) throws EmptyQuestionListException {
		return state.processAnswer(answer);
	}
	
	/**
	 * Returns the number of questions that have been correctly answered.
	 * @return number of correct answers
	 */
	public int getNumCorrectQuestions() {
		return numCorrectAnswers;
	}
	
	/**
	 * Returns the number of questions that have been attempted so far
	 * @return number of attempted questions
	 */
	public int getNumAttemptedQuestions() {
		return numAttemptQuests;
	}
	
	/**
	 * Adds the standard question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addStandardQuestion(StandardQuestion quest) {
		stdState.addQuestion(quest);
	}
	
	/**
	 * Adds the elementary question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addElementaryQuestion(ElementaryQuestion quest) {
		elemState.addQuestion(quest);
	}
	
	/**
	 * Adds the advanced question to the appropriate state object through delegation.
	 * @param quest question to be added
	 */
	public void addAdvancedQuestion(AdvancedQuestion quest) {
		advState.addQuestion(quest);
	}
	
	/**
	 * Returns a collection of standard questions through delegation to the appropriate state object
	 * @return collection of standard questions
	 */
	public List<Question> getStandardQuestions() {
		return stdState.getQuestions();
	}
	
	/**
	 * Returns a collection of elementary questions through delegation to the appropriate state object
	 * @return collection of elementary questions
	 */
	public List<Question> getElementaryQuestions() {
		return elemState.getQuestions();
	}
	
	/**
	 * Returns a collection of advanced questions through delegation to the appropriate state object
	 * @return collection of advanced questions
	 */
	public List<Question> getAdvancedQuestions() {
		return advState.getQuestions();
	}
	
//----------------------------------------------------------------------------//
//-----------------------------Inner Classes For FSM--------------------------//
//----------------------------------------------------------------------------//
	/**
	 * Concrete state when user is answering elementary questions
	 * @author Devin Brenton
	 *
	 */
	private class ElementaryQuestionState extends QuestionState {

		/** Number of attempts at a question */
		private int attempts;
		
		/** Number of elementary questions answered correctly in a row */
		private int numCorrectInRow;

		/**
		 * Constructor - stores the parameter list as a list of waiting standard questions (questions yet to be asked).
		 * @param list list of elementary questions
		 */
		public ElementaryQuestionState(List<ElementaryQuestion> list) {
			super(new ArrayList<Question>(list));
			attempts = 0;
			numCorrectInRow = 0;
		}

		/**
		 * This method corresponds to transitions in the FSM. If an ElementaryQuestion is incorrectly answered 
		 * a hint is displayed and the question is asked again. If two ElementaryQuestion are answered correctly
		 * in a row, the state is set to StandardQuestionState.
		 * @param answer answer to be processed
		 * @return Response to the user's answer
		 * @throws EmptyQuestionListExceptions if currentQuestion is null.
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if(super.getCurrentQuestion() == null) {
				throw new EmptyQuestionListException();
			} else if(super.getCurrentQuestionAnswer().equals(answer)) {
				if(attempts == 0) {
					numCorrectInRow++;
					numAttemptQuests++;
				} else {
					attempts = 0;
				}
				numCorrectAnswers++;
				if(numCorrectInRow == 2) {
					numCorrectInRow = 0;
					state = stdState;
				}
				super.nextQuestion();
				return CORRECT;
			} else {
				numCorrectInRow = 0;
				if(attempts == 0) {
					numAttemptQuests++;
					attempts++;
					return INCORRECT + SEPARATOR + ((ElementaryQuestion) super.getCurrentQuestion()).getHint();
				} else {
					attempts = 0;
					super.nextQuestion();
					return INCORRECT;
				}
			}
		}
		
	}

	/**
	 * Concrete state when user is answering standard questions
	 * @author Devin Brenton
	 *
	 */
	private class StandardQuestionState extends QuestionState {

		/** Number of standard questions answered correctly in a row */
		private int numCorrectInRow;
		
		/**
		 * Constructor - stores the parameter list as a list of waiting standard questions (questions yet to be asked).
		 * @param list list of standard questions
		 */
		public StandardQuestionState(List<StandardQuestion> list) {
			super(new ArrayList<Question>(list));
			numCorrectInRow = 0;
		}

		/**
		 * This method corresponds to transitions in the FSM. If a StandardQuestion is incorrectly answered 
		 * the state is set to ElementaryQuestionState. If two StandardQuestions are answered correctly
		 * in a row, the state is set to AdvancedQuestionState.
		 * @param answer answer to be processed
		 * @return Response to the user's answer
		 * @throws EmptyQuestionListExceptions if currentQuestion is null.
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if(super.getCurrentQuestion() == null) {
				throw new EmptyQuestionListException();
			} else if(super.getCurrentQuestionAnswer().equals(answer)) {
				numCorrectInRow++;
				numCorrectAnswers++;
				numAttemptQuests++;
				if(numCorrectInRow == 2) {
					numCorrectInRow = 0;
					state = advState;
				}
				super.nextQuestion();
				return CORRECT;
			} else {
				numCorrectInRow = 0;
				numAttemptQuests++;
				state = elemState;
				super.nextQuestion();
				return INCORRECT;
			}
		}
		
	}

	/**
	 * Concrete state when user is answering advanced questions
	 * @author Devin Brenton
	 *
	 */
	private class AdvancedQuestionState extends QuestionState {

		/**
		 * Constructor - stores the parameter list as a list of waiting advanced questions (questions yet to be asked).
		 * @param list list of advanced questions
		 */
		public AdvancedQuestionState(List<AdvancedQuestion> list) {
			super(new ArrayList<Question>(list));
		}

		/**
		 * This method corresponds to transitions in the FSM. If a AdvancedQuestion is incorrectly answered 
		 * the state is set to StandardQuestionState. If an AdvancedQuestion is answered correctly a 
		 * congratulatory message is displayed.
		 * @param answer answer to be processed
		 * @return Response to the user's answer
		 * @throws EmptyQuestionListExceptions if currentQuestion is null.
		 */
		@Override
		public String processAnswer(String answer) throws EmptyQuestionListException {
			if(super.getCurrentQuestion() == null) {
				throw new EmptyQuestionListException();
			} else if(super.getCurrentQuestionAnswer().equals(answer)) {
				numCorrectAnswers++;
				numAttemptQuests++;
				String comment = ((AdvancedQuestion) super.getCurrentQuestion()).getComment();
				super.nextQuestion();
				return CORRECT + SEPARATOR + comment;
			} else {
				numAttemptQuests++;
				state = stdState;
				super.nextQuestion();
				return INCORRECT;
			}
		}
		
	}
	
}
