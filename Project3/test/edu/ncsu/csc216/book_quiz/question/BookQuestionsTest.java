package edu.ncsu.csc216.book_quiz.question;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.question_library.*;
import edu.ncsu.csc216.book_quiz.*;

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
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentQuestionChoices() {
		fail("Not yet implemented");
	}

	@Test
	public void testProcessAnswer() {
		fail("Not yet implemented");
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
