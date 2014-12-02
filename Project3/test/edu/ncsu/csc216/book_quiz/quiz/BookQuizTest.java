/**
 * 
 */
package edu.ncsu.csc216.book_quiz.quiz;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.book_quiz.question.BookQuestions;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.AdvancedQuestion;
import edu.ncsu.csc216.question_library.ElementaryQuestion;
import edu.ncsu.csc216.question_library.Question;
import edu.ncsu.csc216.question_library.QuestionException;
import edu.ncsu.csc216.question_library.QuestionReader;
import edu.ncsu.csc216.question_library.QuestionWriter;
import edu.ncsu.csc216.question_library.StandardQuestion;

/**
 * Test class for the BookQuiz which provides the model for running a book quiz
 * @author Devin Brenton
 *
 */
public class BookQuizTest {
	/** questions1.xml - A valid questions file with 3 standard questions, 4 elementary questions, and 2 advanced questions. */
	private static final String FILE_NAME = "questions1.xml";
	
	
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
	private String questionText = "Which is not a character in Harry Potter?";
	private String[] questionChoices = {"Harry", "Ron", "Bubba Gump", "Dumbledore"}; 
	private String correctAnswer1 = "c";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		quiz = new BookQuiz(FILE_NAME);
		reader = new QuestionReader(FILE_NAME);
		standard = reader.getStandardQuestions();
		elementary = reader.getElementaryQuestions();
		advanced = reader.getAdvancedQuestions();
	}

	/**
	 * Tests that the BookQuiz was properly constructed. 
	 * The construction is more thoroughly tested by testing other methods.
	 */
	@Test
	public void testBookQuiz() {
		assertTrue(quiz.hasMoreQuestions());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#hasMoreQuestions()}.
	 */
	@Test
	public void testHasMoreQuestions() throws EmptyQuestionListException {
		assertTrue(quiz.hasMoreQuestions());
		
		//Simulate playing the game until no more questions should be left
		//Correct answer to Standard Question 1. 
		quiz.processAnswer("d");
		assertTrue(quiz.hasMoreQuestions());
		
		//Correct answer to Standard Question 2. 
		quiz.processAnswer("c");
		assertTrue(quiz.hasMoreQuestions());
		
		//Correct answer to Advanced Question 1. 
		quiz.processAnswer("d");
		assertTrue(quiz.hasMoreQuestions());
		
		//Correct answer to Advanced Question 2. 
		quiz.processAnswer("c");
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
		String[] choices = {"Choice a", "Choice b", "Choice c", "Choice d"}; 
		//Tests that the four answers are available in all cases
		String[] choices1 = quiz.getCurrentQuestionChoices();
		assertTrue(Arrays.equals(choices1, choices));
		
		quiz.processAnswer("a");
		String[] choices2 = quiz.getCurrentQuestionChoices();
		assertTrue(Arrays.equals(choices2, choices));
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#processAnswer(java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testProcessAnswer() throws EmptyQuestionListException {		
		String qResponse = "";
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		qResponse = quiz.processAnswer("d");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue("Should be Standard Question 2 but was " + quiz.getCurrentQuestionText(), quiz.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		qResponse = quiz.processAnswer("c");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give correct answer for advanced question 1. Next question will be advanced
		qResponse = quiz.processAnswer("d");
		assertTrue("Expected: Correct! Great work! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT + " Great work!"));
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 2"));

		//Give incorrect answer for advanced question 2. Next question will be standard
		qResponse = quiz.processAnswer("d");
		assertTrue("Expected: Incorrect :( Actual: " + qResponse, qResponse.equals(BookQuestions.INCORRECT));
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give incorrect answer for standard question 3. Next question will be elementary
		qResponse = quiz.processAnswer("d");
		assertTrue("Expected: Incorrect :( Actual: " + qResponse, qResponse.equals(BookQuestions.INCORRECT));
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give incorrect answer for elementary question 1. Next question will be the same question
		qResponse = quiz.processAnswer("a");
		assertTrue("Expected: Incorrect :( Here is a hint. The correct answer is d. Actual: " + qResponse, 
				qResponse.equals(BookQuestions.INCORRECT + " Here is a hint. The correct answer is d."));
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give correct answer for elementary question 1. Next question will be elementary
		qResponse = quiz.processAnswer("d");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give correct answer for elementary question 2. Next question will be elementary
		qResponse = quiz.processAnswer("c");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 3"));
		
		//Give correct answer for elementary question 3. 
		//Next question should be standard but there are none left so exception will be thrown
		qResponse = quiz.processAnswer("a");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		try {
			quiz.getCurrentQuestionText();
			fail();
		} catch (EmptyQuestionListException e) {
			assertTrue(e.getMessage().equals("There are no more questions available."));
		}
		
		try {
			quiz.processAnswer("a");
			fail();
		} catch (EmptyQuestionListException e) {
			assertTrue(e.getMessage().equals("There are no more questions available."));
		}
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
	 * @throws QuestionException 
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testAddStandardQuestion() throws QuestionException, EmptyQuestionListException {		
		quiz.addStandardQuestion(questionText, questionChoices, correctAnswer1);
		
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		quiz.processAnswer("c");
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give incorrect answer for advanced question 1. Next question will be standard
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give correct answer for standard question 3. Next question will be the newly added standard question
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals(questionText));
		
		//Restart questions
		quiz = new BookQuiz(FILE_NAME);
		
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		quiz.processAnswer("c");
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give incorrect answer for advanced question 1. Next question will be standard
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give correct answer for standard question 3. There will not be another question
		quiz.processAnswer("a");
		
		//Add the new question
		quiz.addStandardQuestion(questionText, questionChoices, correctAnswer1);
		assertTrue(quiz.getCurrentQuestionText().equals(questionText));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#addElementaryQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testAddElementaryQuestion() throws EmptyQuestionListException {		
		quiz.addElementaryQuestion(questionText, questionChoices, correctAnswer1, "Hint");
		
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give incorrect answer for standard question 1. Next question will be elementary
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give correct answer for Elementary Question 1. Next question will be elementary
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give incorrect answer for Elementary Question 2. Next question will be the same question
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give correct answer for Elementary Question 2. Next question will be elementary
		quiz.processAnswer("c");
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 3"));
		
		//Give correct answer for Elementary Question 3. Next question will be elementary
		quiz.processAnswer("a");
		assertTrue("Expected: Elementary Question 4 Actual: " + quiz.getCurrentQuestionText(), quiz.getCurrentQuestionText().equals("Elementary Question 4"));
		
		//Give incorrect answer for Elementary Question 4. Next question will be the same question
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals("Elementary Question 4"));
		
		//Give incorrect answer for Elementary Question 4. Next question will be the new question
		quiz.processAnswer("a");
		assertTrue(quiz.getCurrentQuestionText().equals(questionText));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#addAdvancedQuestion(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)}.
	 * @throws EmptyQuestionListException 
	 */
	@Test
	public void testAddAdvancedQuestion() throws EmptyQuestionListException {
		quiz.addAdvancedQuestion(questionText, questionChoices, correctAnswer1, "Comment");
		
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		quiz.processAnswer("c");
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give correct answer for advanced question 1. Next question will be advanced
		quiz.processAnswer("d");
		assertTrue(quiz.getCurrentQuestionText().equals("Advanced Question 2"));
		
		//Give correct answer for advanced question 2. Next question will be the newly added advanced question
		quiz.processAnswer("c");
		assertTrue(quiz.getCurrentQuestionText().equals(questionText));
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.book_quiz.quiz.BookQuiz#writeQuestions(java.lang.String)}.
	 * @throws QuestionException 
	 */
	@Test
	public void testWriteQuestions() throws QuestionException {
		quiz.writeQuestions("questions_new.xml");
		QuestionReader copyOfQuiz = new QuestionReader("questions1.xml");
		assertTrue(standard.equals(copyOfQuiz.getStandardQuestions()));
		assertTrue(elementary.equals(copyOfQuiz.getElementaryQuestions()));
		assertTrue(advanced.equals(copyOfQuiz.getAdvancedQuestions()));
	}

}
