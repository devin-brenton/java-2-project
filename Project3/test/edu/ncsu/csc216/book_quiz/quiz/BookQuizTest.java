/**
 * 
 */
package edu.ncsu.csc216.book_quiz.quiz;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.book_quiz.question.BookQuestions;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.AdvancedQuestion;
import edu.ncsu.csc216.question_library.ElementaryQuestion;
import edu.ncsu.csc216.question_library.QuestionReader;
import edu.ncsu.csc216.question_library.QuestionWriter;
import edu.ncsu.csc216.question_library.StandardQuestion;

/**
 * @author Devin
 *
 */
public class BookQuizTest {
	/** questions1.xml - A valid questions file with 3 standard questions, 4 elementary questions, and 2 advanced questions. */
	private static final String FILE_NAME = "testFiles/questions1.xml";
	
	
	/** BookQuiz object for testing */
	private BookQuiz quiz;
	
	/** Reads the XML file and returns arrays of questions */
	private QuestionReader reader;
	
	/** Writes XML files from the arrays of questions */
	private QuestionWriter writer;
	
	/** List with 3 standard questions (after construction) */
	private List<StandardQuestion> standard;
	
	/** List with 4 elementary questions (after construction) */
	private List<ElementaryQuestion> elementary;
	
	/** List with 2 advanced questions (after construction) */
	private List<AdvancedQuestion> advanced;
	
	/** Example Questions to test writing */
	private String questionText;
	private String[] questionChoices = {"Harry", "Ron", "Bubba Gump", "Dumbledore"}; 
	private String correctAnswer1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		questionText = "Which is not a character in Harry Potter?";
		correctAnswer1 = "c";
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#BookQuiz(java.lang.String)}.
	 */
	@Test
	public void testBookQuiz() {
		assertTrue(quiz.hasMoreQuestions());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#hasMoreQuestions()}.
	 */
	@Test
	public void testHasMoreQuestions() {
		assertTrue(quiz.hasMoreQuestions());
		
		//Simulate playing the game until no more questions should be left
		assertFalse(quiz.hasMoreQuestions());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#getCurrentQuestionText()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetCurrentQuestionText() throws EmptyQuestionListException {
		//Simulate correct walk through a quiz, 2 correct answers increases difficulty
		assertEquals(quiz.getCurrentQuestionText(), "Standard Question 1");
		quiz.processAnswer("d");
		assertEquals(quiz.getCurrentQuestionText(), "Standard Question 2");
		quiz.processAnswer("c");
		//At this stage, difficulty is Advanced
		assertEquals(quiz.getCurrentQuestionText(), "Advanced Question 1");
		quiz.processAnswer("a"); //WRONG, go to Standard
		assertEquals(quiz.getCurrentQuestionText(), "Standard Question 3");
		quiz.processAnswer("c"); //WRONG, go to Elementary
		assertEquals(quiz.getCurrentQuestionText(), "Elementary Question 1");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#getCurrentQuestionChoices()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetCurrentQuestionChoices() throws EmptyQuestionListException {
		String[] choices1 = {"a", "b", "c", "d"}; 
		//Tests that the four answers are available in all cases
		assertTrue(quiz.getCurrentQuestionChoices() == choices1);
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionChoices() == choices1);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#processAnswer(java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testProcessAnswer() throws EmptyQuestionListException {
		//Assuming that processAnswer RETURNS the correct answer
		assertEquals(quiz.processAnswer("d"), "d");
		assertEquals(quiz.processAnswer("c"), "c");
		//At this stage, difficulty is Advanced
		assertEquals(quiz.processAnswer("d"), "d");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#getNumCorrectQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetNumCorrectQuestions() throws EmptyQuestionListException {
		//Simulate quiz
		quiz.processAnswer("d");
		assertEquals(quiz.getNumCorrectQuestions(), 1);
		quiz.processAnswer("c");
		assertEquals(quiz.getNumCorrectQuestions(), 2);
		quiz.processAnswer("a"); //WRONG
		assertEquals(quiz.getNumCorrectQuestions(), 2);
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#getNumAttemptedQuestions()}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testGetNumAttemptedQuestions() throws EmptyQuestionListException {
		quiz.processAnswer("d");
		assertEquals(quiz.getNumAttemptedQuestions(), 1);
		quiz.processAnswer("c");
		assertEquals(quiz.getNumAttemptedQuestions(), 2);
		quiz.processAnswer("a"); //WRONG
		assertEquals(quiz.getNumAttemptedQuestions(), 3);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#addStandardQuestion(java.lang.String, java.lang.String[], java.lang.String)}.
	 */
	@Test
	public void testAddStandardQuestion() {
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#addElementaryQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddElementaryQuestion() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#addAdvancedQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddAdvancedQuestion() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#writeQuestions(java.lang.String)}.
	 */
	@Test
	public void testWriteQuestions() {
		fail("Not yet implemented");
	}

}
