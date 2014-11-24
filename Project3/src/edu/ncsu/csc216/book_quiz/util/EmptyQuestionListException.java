package edu.ncsu.csc216.book_quiz.util;

public class EmptyQuestionListException extends Exception{
	
	/** Message printed when exception is called */
	private static final String MESSAGE = "There are no more questions available.";
	
	/** Serial ID */
	private static final long serialVersionUID = 1L;

	public EmptyQuestionListException() {
		super(MESSAGE);
	}
	
	public EmptyQuestionListException(String message) {
		super(message);
	}

}
