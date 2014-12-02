package edu.ncsu.csc216.book_quiz.quiz;

import java.util.List;

import edu.ncsu.csc216.book_quiz.question.BookQuestions;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.*;

/**
 * Serves as an interface for taking quizzes on book trivia.
 * @author Devin Brenton
 *
 */
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
		reader = new QuestionReader(fileName);
		questions = new BookQuestions(reader.getStandardQuestions(), reader.getElementaryQuestions(), 
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
	 * How many questions has the user attempted to answer?
	 * @return the number of attempts
	 */
	@Override
	public int getNumAttemptedQuestions() {
		return questions.getNumAttemptedQuestions();
	}
	
	/**
	 * Helper method
	 * @param s string to validate
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	private void validateString(String s) {
		if(s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Helper method
	 * @param array array to validate
	 * @throws IllegalArgumentException if any parameters contain null or empty strings
	 */
	private void validateStringArray(String[] array) {
		if(array == null) {
			throw new IllegalArgumentException();
		} else {
			for(int i = 0; i < array.length; i++) {
				if(array[i] == null || array[i].equals("")) {
					throw new IllegalArgumentException();
				}
			}
		}
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
		validateString(questionText);
		validateStringArray(questionChoices);
		validateString(correctAnswer);

		StandardQuestion q = new StandardQuestion();
		q.setAnswer(correctAnswer);
		q.setQuestion(questionText);
		q.setChoiceA(questionChoices[0]);
		q.setChoiceB(questionChoices[1]);
		q.setChoiceC(questionChoices[2]);
		q.setChoiceD(questionChoices[3]);
		
		questions.addStandardQuestion(q);
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
		validateString(questionText);
		validateStringArray(questionChoices);
		validateString(correctAnswer);
		validateString(hint);

		ElementaryQuestion q = new ElementaryQuestion();
		q.setAnswer(correctAnswer);
		q.setQuestion(questionText);
		q.setChoiceA(questionChoices[0]);
		q.setChoiceB(questionChoices[1]);
		q.setChoiceC(questionChoices[2]);
		q.setChoiceD(questionChoices[3]);
		q.setHint(hint);
		
		questions.addElementaryQuestion(q);
		
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
		validateString(questionText);
		validateStringArray(questionChoices);
		validateString(correctAnswer);
		validateString(comment);

		AdvancedQuestion q = new AdvancedQuestion();
		q.setAnswer(correctAnswer);
		q.setQuestion(questionText);
		q.setChoiceA(questionChoices[0]);
		q.setChoiceB(questionChoices[1]);
		q.setChoiceC(questionChoices[2]);
		q.setChoiceD(questionChoices[3]);
		q.setComment(comment);
		
		questions.addAdvancedQuestion(q);
	}

	/**
	 * Writes the Questions to the given file
	 * @param questionFile file name to write questions to
	 * @throws QuestionException if the questions cannot be written to the 
	 * given file
	 */
	@Override
	public void writeQuestions(String questionFile) throws QuestionException {
		writer = new QuestionWriter(questionFile);

		List<Question> standard = questions.getStandardQuestions();
		List<Question> elementary = questions.getElementaryQuestions();
		List<Question> advanced = questions.getAdvancedQuestions();
		
		for(int i = 0; i < standard.size(); i++) {
			writer.addStandardQuestion((StandardQuestion) standard.get(i));
		}
		
		for(int i = 0; i < elementary.size(); i++) {
			writer.addElementaryQuestion((ElementaryQuestion) elementary.get(i));
		}

		for(int i = 0; i < advanced.size(); i++) {
			writer.addAdvancedQuestion((AdvancedQuestion) advanced.get(i));
		}
		
		writer.marshal();
	}

}
