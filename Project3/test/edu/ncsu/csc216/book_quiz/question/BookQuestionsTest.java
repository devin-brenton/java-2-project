package edu.ncsu.csc216.book_quiz.question;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.question_library.*;
import edu.ncsu.csc216.book_quiz.*;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;

/**
 * Tests the functionality and construction of BookQuestions, a class for storing and determining the questions in a quiz
 * @author Devin Brenton
 *
 */
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
		assertEquals(questions.getNumAttemptedQuestions(), 0);
		
//		standard = null;
//		elementary = null;
//		advanced = null;
//		BookQuestions nullQuestions = new BookQuestions(standard, elementary, advanced);
//		assertEquals(standard, nullQuestions.getStandardQuestions());
//		assertEquals(elementary, nullQuestions.getElementaryQuestions());
//		assertEquals(advanced, nullQuestions.getAdvancedQuestions());
//		assertFalse(questions.hasMoreQuestions());
//		assertEquals(questions.getNumAttemptedQuestions(), 0);
//		assertEquals(questions.getNumAttemptedQuestions(), 0);
	}

	@Test
	/**
	 * Verifies the Question Texts can be retrieved
	 */
	public void testGetCurrentQuestionText() throws EmptyQuestionListException {
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
	public void testGetCurrentQuestionChoices() throws EmptyQuestionListException {
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
	public void testProcessAnswer() throws EmptyQuestionListException {
		String qResponse = "";
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		qResponse = questions.processAnswer("d");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue("Should be Standard Question 2 but was " + questions.getCurrentQuestionText(), questions.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		qResponse = questions.processAnswer("c");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give correct answer for advanced question 1. Next question will be advanced
		qResponse = questions.processAnswer("d");
		assertTrue("Expected: Correct! Great work! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT + " Great work!"));
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 2"));

		//Give incorrect answer for advanced question 2. Next question will be standard
		qResponse = questions.processAnswer("d");
		assertTrue("Expected: Incorrect :( Actual: " + qResponse, qResponse.equals(BookQuestions.INCORRECT));
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give incorrect answer for standard question 3. Next question will be elementary
		qResponse = questions.processAnswer("d");
		assertTrue("Expected: Incorrect :( Actual: " + qResponse, qResponse.equals(BookQuestions.INCORRECT));
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give incorrect answer for elementary question 1. Next question will be the same question
		qResponse = questions.processAnswer("a");
		assertTrue("Expected: Incorrect :( Here is a hint. The correct answer is d. Actual: " + qResponse, 
				qResponse.equals(BookQuestions.INCORRECT + " Here is a hint. The correct answer is d."));
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give correct answer for elementary question 1. Next question will be elementary
		qResponse = questions.processAnswer("d");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give correct answer for elementary question 2. Next question will be elementary
		qResponse = questions.processAnswer("c");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 3"));
		
		//Give correct answer for elementary question 3. 
		//Next question should be standard but there are none left so exception will be thrown
		qResponse = questions.processAnswer("a");
		assertTrue("Expected: Correct! Actual: " + qResponse, qResponse.equals(BookQuestions.CORRECT));
		try {
			questions.getCurrentQuestionText();
			fail();
		} catch (EmptyQuestionListException e) {
			assertTrue(e.getMessage().equals("There are no more questions available."));
		}
		
		try {
			questions.processAnswer("a");
			fail();
		} catch (EmptyQuestionListException e) {
			assertTrue(e.getMessage().equals("There are no more questions available."));
		}
	}

	/**
	 * Test that BookQuestions correctly counts the number of correctly answered questions
	 */
	@Test
	public void testGetNumCorrectQuestions() throws EmptyQuestionListException {
		assertEquals(questions.getNumCorrectQuestions(), 0);
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertEquals(questions.getNumCorrectQuestions(), 1);
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertEquals(questions.getNumCorrectQuestions(), 2);
		
		//Give correct answer for advanced question 1. Next question will be advanced
		questions.processAnswer("d");
		assertEquals(questions.getNumCorrectQuestions(), 3);

		//Give incorrect answer for advanced question 2. Next question will be standard
		questions.processAnswer("d");
		assertEquals(questions.getNumCorrectQuestions(), 3);
		
		//Give incorrect answer for standard question 3. Next question will be elementary
		questions.processAnswer("d");
		assertEquals(questions.getNumCorrectQuestions(), 3);
		
		//Give incorrect answer for elementary question 1. Next question will be the same question
		questions.processAnswer("a");
		assertEquals(questions.getNumCorrectQuestions(), 3);
		
		//Give correct answer for elementary question 1. Next question will be elementary
		questions.processAnswer("d");
		assertEquals(questions.getNumCorrectQuestions(), 4);
		
		//Give correct answer for elementary question 2. Next question will be elementary
		questions.processAnswer("c");
		assertEquals(questions.getNumCorrectQuestions(), 5);
		
		//Give correct answer for elementary question 3. 
		//Next question should be standard but there are none left
		questions.processAnswer("a");
		assertEquals(questions.getNumCorrectQuestions(), 6);
		
		try {
			questions.processAnswer("a");
			fail();
		} catch (EmptyQuestionListException e) {
			assertEquals(questions.getNumCorrectQuestions(), 6);
		}
	}

	/**
	 * Test that BookQuestions correctly counts the number of questions attempted
	 */
	@Test
	public void testGetNumAttemptedQuestions() throws EmptyQuestionListException {
		assertEquals(questions.getNumAttemptedQuestions(), 0);
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertEquals(questions.getNumAttemptedQuestions(), 1);
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertEquals(questions.getNumAttemptedQuestions(), 2);
		
		//Give correct answer for advanced question 1. Next question will be advanced
		questions.processAnswer("d");
		assertEquals(questions.getNumAttemptedQuestions(), 3);

		//Give incorrect answer for advanced question 2. Next question will be standard
		questions.processAnswer("d");
		assertEquals(questions.getNumAttemptedQuestions(), 4);
		
		//Give incorrect answer for standard question 3. Next question will be elementary
		questions.processAnswer("d");
		assertEquals(questions.getNumAttemptedQuestions(), 5);
		
		//Give incorrect answer for elementary question 1. Next question will be the same question
		questions.processAnswer("a");
		assertEquals(questions.getNumAttemptedQuestions(), 6);
		
		//Give correct answer for elementary question 1. Next question will be elementary
		questions.processAnswer("d");
		assertEquals(questions.getNumAttemptedQuestions(), 6);
		
		//Give correct answer for elementary question 2. Next question will be elementary
		questions.processAnswer("c");
		assertEquals(questions.getNumAttemptedQuestions(), 7);
				
		//Give correct answer for elementary question 3. 
		//Next question should be standard but there are none left
		questions.processAnswer("a");
		assertEquals(questions.getNumAttemptedQuestions(), 8);
		
		try {
			questions.processAnswer("a");
			fail();
		} catch (EmptyQuestionListException e) {
			assertEquals(questions.getNumAttemptedQuestions(), 8);
		}
	}

	/**
	 * Test the functionality of adding standard questions to BookQuestions
	 */
	@Test
	public void testAddStandardQuestion() throws EmptyQuestionListException {
		StandardQuestion q = new StandardQuestion();
		q.setQuestion("Standard Question");
		q.setChoiceA("a");
		q.setChoiceB("b");
		q.setChoiceC("c");
		q.setChoiceD("d");
		q.setAnswer("a");
		
		questions.addStandardQuestion(q);
		
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give incorrect answer for advanced question 1. Next question will be standard
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give correct answer for standard question 3. Next question will be the newly added standard question
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question"));
		
		//Restart questions
		questions = new BookQuestions(standard, elementary, advanced);
		
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give incorrect answer for advanced question 1. Next question will be standard
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 3"));
		
		//Give correct answer for standard question 3. There will not be another question
		questions.processAnswer("a");
		
		//Add the new question
		questions.addStandardQuestion(q);
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question"));
	}

	/**
	 * Test the functionality of adding elementary questions to BookQuestions
	 */
	@Test
	public void testAddElementaryQuestion() throws EmptyQuestionListException {
		ElementaryQuestion q = new ElementaryQuestion();
		q.setQuestion("Elementary Question");
		q.setChoiceA("a");
		q.setChoiceB("b");
		q.setChoiceC("c");
		q.setChoiceD("d");
		q.setAnswer("a");
		
		questions.addElementaryQuestion(q);
		
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give incorrect answer for standard question 1. Next question will be elementary
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 1"));
		
		//Give correct answer for Elementary Question 1. Next question will be elementary
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give incorrect answer for Elementary Question 2. Next question will be the same question
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 2"));
		
		//Give correct answer for Elementary Question 2. Next question will be elementary
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 3"));
		
		//Give correct answer for Elementary Question 3. Next question will be elementary
		questions.processAnswer("a");
		assertTrue("Expected: Elementary Question 4 Actual: " + questions.getCurrentQuestionText(), questions.getCurrentQuestionText().equals("Elementary Question 4"));
		
		//Give incorrect answer for Elementary Question 4. Next question will be the same question
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question 4"));
		
		//Give incorrect answer for Elementary Question 4. Next question will be the new question
		questions.processAnswer("a");
		assertTrue(questions.getCurrentQuestionText().equals("Elementary Question"));
		
		//Restart questions
		questions = new BookQuestions(standard, elementary, advanced);		
		questions.addElementaryQuestion(q);
		
		List<Question> list = questions.getElementaryQuestions();
		assertEquals(list.get(4), q);
		
		ElementaryQuestion q2 = new ElementaryQuestion();
		q.setQuestion("Elementary Question");
		q.setChoiceA("a");
		q.setChoiceB("b");
		q.setChoiceC("c");
		q.setChoiceD("d");
		q.setAnswer("a");
		
		questions.addElementaryQuestion(q2);
		list = questions.getElementaryQuestions();
		assertEquals(list.get(4), q);
		assertEquals(list.get(5), q2);
	}

	/**
	 * Test the functionality of adding advanced questions to BookQuestions
	 */
	@Test
	public void testAddAdvancedQuestion() throws EmptyQuestionListException {
		AdvancedQuestion q = new AdvancedQuestion();
		q.setQuestion("Advanced Question");
		q.setChoiceA("a");
		q.setChoiceB("b");
		q.setChoiceC("c");
		q.setChoiceD("d");
		q.setAnswer("a");
		
		questions.addAdvancedQuestion(q);
		
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 1"));
		
		//Give correct answer for standard question 1. Next question will be standard
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Standard Question 2"));
		
		//Give correct answer for standard question 2. Next question will be advanced
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 1"));
		
		//Give correct answer for advanced question 1. Next question will be advanced
		questions.processAnswer("d");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question 2"));
		
		//Give correct answer for advanced question 2. Next question will be the newly added advanced question
		questions.processAnswer("c");
		assertTrue(questions.getCurrentQuestionText().equals("Advanced Question"));
		
		//Restart questions
		questions = new BookQuestions(standard, elementary, advanced);		
		questions.addAdvancedQuestion(q);
		
		List<Question> list = questions.getAdvancedQuestions();
		assertEquals(list.get(2), q);
		
		AdvancedQuestion q2 = new AdvancedQuestion();
		q.setQuestion("Advanced Question");
		q.setChoiceA("a");
		q.setChoiceB("b");
		q.setChoiceC("c");
		q.setChoiceD("d");
		q.setAnswer("a");
		
		questions.addAdvancedQuestion(q2);
		list = questions.getAdvancedQuestions();
		assertEquals(list.get(2), q);
		assertEquals(list.get(3), q2);
	}

}
