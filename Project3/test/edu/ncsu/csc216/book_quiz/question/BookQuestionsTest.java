package edu.ncsu.csc216.book_quiz.question;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.question_library.*;
import edu.ncsu.csc216.book_quiz.*;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;

public class BookQuestionsTest {
	
	/** questions1.xml - A valid questions file with 3 standard questions, 4 elementary questions, and 2 advanced questions. */
	private static final String QUESTIONS = "testFiles/questions1.xml";
	
	/** QuestionReader to process the XML file and return Question lists */
	private QuestionReader reader;
	
	/** List with 3 standard questions (after construction) */
	private List<StandardQuestion> standard;
	
	/** List with 4 elementary questions (after construction) */
	private List<ElementaryQuestion> elementary;
	
	/** List with 2 advanced questions (after construction) */
	private List<AdvancedQuestion> advanced;
	
	/** BookQuestion object for testing */
	private BookQuestions questions;

	/**
	 * Constructs the reader and gets the lists of questions. Creates questions from those lists.
	 */
	@Before
	public void setUp() throws Exception {
		reader = new QuestionReader(QUESTIONS);
		standard = reader.getStandardQuestions();
		elementary = reader.getElementaryQuestions();
		advanced = reader.getAdvancedQuestions();
		questions = new BookQuestions(standard, elementary, advanced);
	}

	/**
	 * Verifies the construction of BookQuestions
	 */
	@Test
	public void testBookQuestions() {
		assertEquals(standard, questions.getStandardQuestions());
		assertEquals(elementary, questions.getElementaryQuestions());
		assertEquals(advanced, questions.getAdvancedQuestions());
		assertTrue(questions.hasMoreQuestions());
		assertEquals(questions.getNumAttemptedQuestions(), 0);
		assertEquals(questions.getNumCorrectQuestions(), 0);
		
		standard = null;
		elementary = null;
		advanced = null;
		BookQuestions nullQuestions = new BookQuestions(standard, elementary, advanced);
		assertEquals(standard, questions.getStandardQuestions());
		assertEquals(elementary, questions.getElementaryQuestions());
		assertEquals(advanced, questions.getAdvancedQuestions());
		assertFalse(questions.hasMoreQuestions());
		assertEquals(questions.getNumAttemptedQuestions(), 0);
		assertEquals(questions.getNumCorrectQuestions(), 0);
	}

	@Test
	public void testGetCurrentQuestionText() {
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 2"));
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
	}

	/**
	 * Makes sure that the question choices are returned in the correct order
	 */
	@Test
	public void testGetCurrentQuestionChoices() {
		String[] choices = new String[4];
		choices[0] = "Choice a";
		choices[1] = "Choice b";
		choices[2] = "Choice c";
		choices[3] = "Choice d";
		
		String[] answers = questions.getCurrentQuestionChoices();
		
		assertTrue(answers[0].equals(choices[0]));
		assertTrue(answers[1].equals(choices[1]));
		assertTrue(answers[2].equals(choices[2]));
		assertTrue(answers[3].equals(choices[3]));
	}

	/**
	 * Tests the FSM pattern for deciding which level difficulty question to ask next as defined by UC7, S1
	 */
	@Test
	public void testProcessAnswer() {
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give correct answer for advanced question 1. Next question will be advanced
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1")); //The second advanced question is still named Advanced Question 1

		//Give incorrect answer for advanced question 2. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give incorrect answer for standard question 3. Next question will be elementary
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give incorrect answer for elementary question 1. Next question will be the same question
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give correct answer for elementary question 1. Next question will be elementary
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give correct answer for elementary question 2. 
		//Next question should be advanced but there are none left so exception will be thrown
		questions.processAnswer("c");
		try {
			questions.getCurrentQuestionText();
			fail();
		} catch (EmptyQuestionListException e) {
			assertTrue(e.getMessage().equals("There are no more questions available."));
		}
	}

	@Test
	public void testGetNumCorrectQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumAttemptedQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddStandardQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddElementaryQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAdvancedQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStandardQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElementaryQuestions() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdvancedQuestions() {
		fail("Not yet implemented");
	}

}
