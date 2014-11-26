package edu.ncsu.csc216.book_quiz.quiz;

import edu.ncsu.csc216.book_quiz.question.BookQuestions;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.*;

public class BookQuiz implements QuizMaster {
	
	/** Contains the question for quizzing */
	private BookQuestions questions;
	
	/** Reads the XML file and returns arrays of questions */
	private QuestionReader reader;
	
	/** Writes XML files from the arrays of questions */
	private QuestionWriter writer;
	
	/**
	 * Constructor - The String parameter for the constructor is required for 
	 * reader, which is of type QuestionReader. After the constructor creates 
	 * reader, it can create questions, which is of type BookQuestions. The 
	 * three List parameters for the BookQuestions constructor are obtained 
	 * from reader.
	 * @param fileName name of XML file containing the questions
	 */
	public BookQuiz(String fileName) throws QuestionException {
		this.reader = new QuestionReader(fileName);
		this.writer = new QuestionWriter(fileName);
		this.questions = new BookQuestions(reader.getStandardQuestions(), reader.getElementaryQuestions(), 
						reader.getAdvancedQuestions());
	}
	
	/**
	 * Are there any more questions remaining in this quiz?
	 * @return true if there are, false if there are not
	 */
	@Override
	public boolean hasMoreQuestions() {
		return questions.hasMoreQuestions();
	}

	/**
	 * Get the text for the current question.
	 * @return the current question text
	 * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String getCurrentQuestionText() throws EmptyQuestionListException {
		return questions.getCurrentQuestionText();
	}

	/**
	 * Get the possible answers for the current question
	 * @return the possible answers for the current question -- each answer
	 *         is a separate array element
	 * * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String[] getCurrentQuestionChoices() throws EmptyQuestionListException {
		return questions.getCurrentQuestionChoices();
	}

	/**
	 * Process the user's answer to the current question.
	 * @param answer the user's answer to the question
	 * @return the graded response to the question
	 * @throws EmptyQuestionListException if there is no current question
	 */
	@Override
	public String processAnswer(String answer) throws EmptyQuestionListException {
		return questions.processAnswer(answer);
	}

	/**
	 * How many questions has the user answered correctly?
	 * @return the number of correct answers
	 */
	@Override
	public int getNumCorrectQuestions() {
		return questions.getNumCorrectAnswers();
	}

	/**
	 * How many questions has the user attempted to answer.
	 * @return the number of attempts
	 */
	@Override
	public int getNumAttemptedQuestions() {
		return questions.getNumAttemptedQuestions();
	}
	
	/**
	 * Helper method
	 * @param s string to validate
	 */
	private void validateString(String s) {
		
	}
	
	/**
	 * Helper method
	 * @param array array to validate
	 */
	private void validateStringArray(String[] array) {
		
	}

	/**
	 * Adds a StandardQuestion to the QuestionLibrary.
	 * @param questionText text of question
	 * @param questionChoices array of possible answers
	 * @param correctAnswer correct answer
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	@Override
	public void addStandardQuestion(String questionText, String[] questionChoices, String correctAnswer) {
		//Error Checking
		if (questionText.isEmpty() || questionText == null || questionChoices == null){
			throw new IllegalArgumentException();
		}
		if (correctAnswer.isEmpty() || correctAnswer == null){
			throw new IllegalArgumentException();
		}

		StandardQuestion q = new StandardQuestion();
		q.setAnswer(correctAnswer);
		q.setQuestion(questionText);
		q.setChoiceA(questionChoices[0]);
		q.setChoiceB(questionChoices[1]);
		q.setChoiceC(questionChoices[2]);
		q.setChoiceD(questionChoices[3]);
		
		writer.addStandardQuestion(q);
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
	public void addElementaryQuestion(String questionText, String[] questionChoices, String correctAnswer, String hint) {
		//Error Checking
		if (questionText.isEmpty() || questionText == null || questionChoices == null){
			throw new IllegalArgumentException();
		}
		if (correctAnswer.isEmpty() || correctAnswer == null || hint.isEmpty() || hint == null){
			throw new IllegalArgumentException();
		}

		ElementaryQuestion q = new ElementaryQuestion();
		q.setAnswer(correctAnswer);
		q.setQuestion(questionText);
		q.setChoiceA(questionChoices[0]);
		q.setChoiceB(questionChoices[1]);
		q.setChoiceC(questionChoices[2]);
		q.setChoiceD(questionChoices[3]);
		q.setHint(hint);
		
		writer.addElementaryQuestion(q);
		
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
				//Error Checking
				if (questionText.isEmpty() || questionText == null || questionChoices == null){
					throw new IllegalArgumentException();
				}
				if (correctAnswer.isEmpty() || correctAnswer == null || comment.isEmpty() || comment == null){
					throw new IllegalArgumentException();
				}

				AdvancedQuestion q = new AdvancedQuestion();
				q.setAnswer(correctAnswer);
				q.setQuestion(questionText);
				q.setChoiceA(questionChoices[0]);
				q.setChoiceB(questionChoices[1]);
				q.setChoiceC(questionChoices[2]);
				q.setChoiceD(questionChoices[3]);
				q.setComment(comment);
				
				writer.addAdvancedQuestion(q);
	}

	/**
	 * Writes the Questions to the given file
	 * @param questionFile file name to write questions to
	 * @throws QuestionException if the questions cannot be written to the 
	 * given file
	 */
	@Override
	public void writeQuestions(String questionFile) throws QuestionException {
		
	}

}
