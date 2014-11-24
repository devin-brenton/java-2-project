package edu.ncsu.csc216.book_quiz.quiz;

import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.QuestionException;

public class BookQuiz implements QuizMaster {

	/**
	 * Are there any more questions remaining in this quiz?
	 * @return true if there are, false if there are not
	 */
	@Override
	public boolean hasMoreQuestions() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Get the text for the current question.
	 * @return the current question text
	 * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String getCurrentQuestionText() throws EmptyQuestionListException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get the possible answers for the current question
	 * @return the possible answers for the current question -- each answer
	 *         is a separate array element
	 * * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String[] getCurrentQuestionChoices()
			throws EmptyQuestionListException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Process the user's answer to the current question.
	 * @param answer  the user's answer to the question
	 * @return the graded response to the question
	 * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String processAnswer(String answer)
			throws EmptyQuestionListException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * How many questions has the user answered correctly?
	 * @return the number of correct answers
	 */
	@Override
	public int getNumCorrectQuestions() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * How many questions has the user attempted to answer.
	 * @return the number of attempts
	 */
	@Override
	public int getNumAttemptedQuestions() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Adds a StandardQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	@Override
	public void addStandardQuestion(String questionText,
			String[] questionChoices, String correctAnswer) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Adds an ElementaryQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param hint a hint for the question
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	@Override
	public void addElementaryQuestion(String questionText,
			String[] questionChoices, String correctAnswer, String hint) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Adds an AdvancedQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @param comment a message for answering the question right
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	@Override
	public void addAdvancedQuestion(String questionText,
			String[] questionChoices, String correctAnswer, String comment) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Writes the Questions to the given file
	 * @param questionFile file name to write questions to
	 * @throws QuestionException if the questions cannot be written to the 
	 * given file
	 */
	@Override
	public void writeQuestions(String questionFile) throws QuestionException {
		// TODO Auto-generated method stub
		
	}
	
	

}
