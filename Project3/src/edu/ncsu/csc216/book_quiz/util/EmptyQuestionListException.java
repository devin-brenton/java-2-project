package edu.ncsu.csc216.book_quiz.util;

/**
 * Exception that will be thrown when a list of questions is out of questions and is
 * called on for more questions. This is a checked exception.
 * @author Devin Brenton
 *
 */
public class EmptyQuestionListException extends Exception {
	
	/** Message printed when exception is called */
	private static final String MESSAGE = "There are no more questions available.";
	
	/** Serial ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor - if there is no message passed in the default message is used.
	 */
	public EmptyQuestionListException() {
		super(MESSAGE);
	}
	
	/**
	 * Constructor - creates the exception with the input message
	 * @param message the message to include with the exception
	 */
	public EmptyQuestionListException(String message) {
		super(message);
	}

}
