package edu.ncsu.csc216.book_quiz.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.ncsu.csc216.book_quiz.quiz.*;
import edu.ncsu.csc216.book_quiz.util.EmptyQuestionListException;
import edu.ncsu.csc216.question_library.QuestionException;

public class BookQuizGUI extends JFrame implements ActionListener {

	// Backend model
	private QuizMaster quiz;
	
	// Parameters for component sizes and spacings 
	private static final int FRAME_WIDTH = 500; 
	private static final int FRAME_HEIGHT = 300;
	private static final int FIELD_LENGTH = 100;
	
	// Panel and window titles
	private static final String WINDOW_TITLE = "Book Quiz";
	
	// Button and text field strings
	private static final String WELCOME = "Welcome";
	private static final String ERROR = "Error";
	private static final String ADD_QUEST = "Add Questions";
	private static final String TAKE_QUIZ = "Take Book Quiz";
	private static final String QUIT = "Quit";
	private static final String ADD = "Add";
	private static final String WRITE = "Write All";
	private static final String DONE = "Done";
	private static final String SUBMIT = "Submit Answer";
	private static final String NEXT = "Next Question";
	private static final String OK = "OK";	
	private static final String[] QUEST_TYPES = {"Standard Question", "Elementary Question", "Advanced Question"};
	private static final String[] ANS_CHOICES = {"A", "B", "C", "D"};
	private static final String QUIZ_RESULTS = "Quiz Results";
	private static final String BLANK = " ";
	private static final String HINT = "Hint:";
	private static final String COMMENT = "Comment:";
	
	// Buttons
	private JButton btnAddQuest = new JButton(ADD_QUEST);
	private JButton btnTake = new JButton(TAKE_QUIZ);
	private JButton btnQuit1 = new JButton(QUIT);
	private JButton btnQuit2 = new JButton(QUIT);
	private JButton btnQuit3 = new JButton(QUIT);
	private JButton btnAdd = new JButton(ADD);
	private JButton btnWrite = new JButton(WRITE);
	private JButton btnDone1 = new JButton(DONE);
	private JButton btnDone2 = new JButton(DONE);
	private JButton btnSubmit = new JButton(SUBMIT);
	private JButton btnNext = new JButton(NEXT);
	
	// Combo Boxes
	private JComboBox<String> cmbQuestType = new JComboBox<String>(QUEST_TYPES);
	private JComboBox<String> cmbAnsChoices = new JComboBox<String>(ANS_CHOICES);
	
	// Labels and separator
	private JLabel lblQuestType = new JLabel("Question Type:");
	private JLabel lblQuestAdd = new JLabel("Question:");
	private JLabel lblChoiceAAdd = new JLabel("Choice A:");
	private JLabel lblChoiceBAdd = new JLabel("Choice B:");
	private JLabel lblChoiceCAdd = new JLabel("Choice C:");
	private JLabel lblChoiceDAdd = new JLabel("Choice D:");
	private JLabel lblAnswerAdd = new JLabel("Answer:");
	private JLabel lblQuest = new JLabel();
	private JLabel lblResult = new JLabel();
	private JLabel lblHint = new JLabel(HINT);
	private JLabel lblComment = new JLabel(COMMENT);
	
	// Text fields
	private JTextField txtQuestAdd = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceA = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceB = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceC = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceD = new JTextField(FIELD_LENGTH);
	private JTextField txtHint = new JTextField(FIELD_LENGTH);
	private JTextField txtComment = new JTextField(FIELD_LENGTH);
	
	// Radio Buttons
	private JRadioButton radA = new JRadioButton();
	private JRadioButton radB = new JRadioButton();
	private JRadioButton radC = new JRadioButton();
	private JRadioButton radD = new JRadioButton();
	private ButtonGroup bgChoices = new ButtonGroup();
	
	// Organizational and alignment boxes and panels
	private CardLayout mainCardLayout = new CardLayout(1, 1);
	private JPanel pnlCard = new JPanel(mainCardLayout);
	private JPanel pnlWelcome = new JPanel();
	private JPanel pnlAddQuest = new JPanel(new BorderLayout());
	private JPanel pnlTakeQuiz = new JPanel(new BorderLayout());
	private JPanel pnlAddInfo = new JPanel(new GridLayout(7, 2));
	private JPanel pnlAddButtons = new JPanel();
	private JPanel pnlQuizChoices = new JPanel(new GridLayout(2, 1));
	private JPanel pnlQuizButtons = new JPanel();
	private JPanel pnlRadioButtons = new JPanel(new GridLayout(0, 1));
	private CardLayout clHintComment = new CardLayout(1, 1);
	private JPanel pnlHintComment = new JPanel(clHintComment);
	private JPanel pnlHint = new JPanel(new GridLayout(1, 0));
	private JPanel pnlComment = new JPanel(new GridLayout(1, 0));
	private JPanel pnlBlank = new JPanel();
	
	// Main window
	private Container mainWindow = getContentPane();

	// Question answer when quizzing
	private String questionAnswer;
	
	// Question type when adding questions
	private String questionType;

	// Answer choice when adding questions
	private String answerChoice;
	
	public BookQuizGUI(String filename) throws QuestionException {
		try {
			if (filename == null) {
				String userPickFilename = null;
				JFileChooser fc = new JFileChooser(".");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					userPickFilename = fc.getSelectedFile().getName();
				}
				quiz = new BookQuiz(userPickFilename);
			} else {
				quiz = new BookQuiz(filename);
			}
			initializeUI();
			this.setVisible(true);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Display add question card
		if(e.getSource().equals(btnAddQuest)) {
			mainCardLayout.show(pnlCard, ADD_QUEST);
		}
		// Display take quiz card
		if(e.getSource().equals(btnTake)) {
			try {
				getQuestionInfo();
				mainCardLayout.show(pnlCard, TAKE_QUIZ);
				resetQuestionButtons();
			} catch (EmptyQuestionListException exc) {
				JOptionPane.showMessageDialog(new JFrame(), exc.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
			}
		}
		// Quit the program
		if(e.getSource().equals(btnQuit1) || e.getSource().equals(btnQuit2) || e.getSource().equals(btnQuit3)) {
			System.exit(0);
		}
		// Try to add the question to the available questions
		if(e.getSource().equals(btnAdd)) {
			addQuestion();
		}
		// Try to write all the questions to a file
		if(e.getSource().equals(btnWrite)) {
			try {
				String userPickFilename = null;
				JFileChooser fc = new JFileChooser(".");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					userPickFilename = fc.getSelectedFile().getName();
				}
				quiz.writeQuestions(userPickFilename);
			} catch (QuestionException exc) {
				JOptionPane.showMessageDialog(new JFrame(), "Unable to write to file.", ERROR, JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException n) {
				// Don't do anything if the user didn't pick a file
			}
		}
		// Return to the welcome page from quiz
		if(e.getSource().equals(btnDone1)) {
			JOptionPane.showMessageDialog(new JFrame(), getQuizResults(), QUIZ_RESULTS, JOptionPane.INFORMATION_MESSAGE);
			mainCardLayout.show(pnlCard, WELCOME);
		}
		// Return to the welcome page from add
		if(e.getSource().equals(btnDone2)) {
			mainCardLayout.show(pnlCard, WELCOME);
		}
		// Submit the answer for processing
		if(e.getSource().equals(btnSubmit)) {
			try {
				lblResult.setText(quiz.processAnswer(questionAnswer));
			} catch (EmptyQuestionListException exc) {
				JOptionPane.showMessageDialog(new JFrame(), getQuizResults(), QUIZ_RESULTS, JOptionPane.INFORMATION_MESSAGE);
			}
			btnSubmit.setEnabled(false);
			btnNext.setEnabled(true);
		}
		// Show next question
		if(e.getSource().equals(btnNext)) {
			try {
				getQuestionInfo();
			} catch (EmptyQuestionListException exc) {
				btnDone1.doClick();
			}
			resetQuestionButtons();
		}
		
		// Combo boxes
		// Get the question type
		if(e.getSource().equals(cmbQuestType)) {
			questionType = (String) cmbQuestType.getSelectedItem();
			if(questionType.equals("Elementary Question")) {
				clHintComment.show(pnlHintComment, HINT);
			}
			if(questionType.equals("Advanced Question")) {
				clHintComment.show(pnlHintComment, COMMENT);
			}
		}
//		// Get the answer
//		if(e.getSource().equals(cmbAnsChoices)) {
//			questionAnswer = (String) cmbAnsChoices.getSelectedItem();
//		}
		
		// Radio Buttons
		// Button A
		if(e.getSource().equals(radA)) {
			btnSubmit.setEnabled(true);
			questionAnswer = "a";
		}
		// Button B
		if(e.getSource().equals(radB)) {
			btnSubmit.setEnabled(true);
			questionAnswer = "b";
		}
		// Button C
		if(e.getSource().equals(radC)) {
			btnSubmit.setEnabled(true);
			questionAnswer = "c";
		}
		// Button D
		if(e.getSource().equals(radD)) {
			btnSubmit.setEnabled(true);
			questionAnswer = "d";
		}
	}
	
	private void addQuestion() {
		String question = txtQuestAdd.getText();
		String[] choices = {txtChoiceA.getText(), txtChoiceB.getText(), 
				txtChoiceC.getText(), txtChoiceD.getText()};
		String answer = (String) cmbAnsChoices.getSelectedItem();
		try {
			if(questionType.equals("Standard Question")) {
				quiz.addStandardQuestion(question, choices, answer);
			} else if(questionType.equals("Elementary Question")) {
				String hint = txtHint.getText();
				quiz.addElementaryQuestion(question, choices, answer, hint);
			} else {
				String comment = txtComment.getText();
				quiz.addAdvancedQuestion(question, choices, answer, comment);
			} 
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		cmbQuestType.setSelectedIndex(0);
		txtQuestAdd.setText("");
		txtChoiceA.setText("");
		txtChoiceB.setText("");
		txtChoiceC.setText("");
		txtChoiceD.setText("");
		cmbAnsChoices.setSelectedIndex(0);
		txtHint.setText("");
		txtComment.setText("");
		clHintComment.show(pnlHintComment, BLANK);		
	}
	
	private void resetQuestionButtons() {
		btnSubmit.setEnabled(false);
		btnNext.setEnabled(false);
		lblResult.setText(" ");
		bgChoices.clearSelection();
	}
	
	private String getQuizResults() {
		return "You answered " + quiz.getNumCorrectQuestions() + " questions correctly out of " + 
				quiz.getNumAttemptedQuestions() + " attempts.";
	}
	
	private void getQuestionInfo() throws EmptyQuestionListException {
		String[] choices;
		lblQuest.setText(quiz.getCurrentQuestionText());
		choices = quiz.getCurrentQuestionChoices();
		radA.setText(choices[0]);
		radB.setText(choices[1]);
		radC.setText(choices[2]);
		radD.setText(choices[3]);
	}
	
	private void initializeUI() {
		//  Initialize the main frame parameters.
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(WINDOW_TITLE);
		
		setUpPanels();
		
		// Add everything to window
		mainWindow.add(pnlCard);
		
		addListeners();
	}
	
	private void addListeners() {
		// Add listeners to buttons
		btnAddQuest.addActionListener(this);
		btnTake.addActionListener(this);
		btnQuit1.addActionListener(this);
		btnQuit2.addActionListener(this);
		btnQuit3.addActionListener(this);
		btnAdd.addActionListener(this);
		btnWrite.addActionListener(this);
		btnDone1.addActionListener(this);
		btnDone2.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnNext.addActionListener(this);
		
		// Add listeners to combo boxes
		cmbQuestType.addActionListener(this);
		cmbAnsChoices.addActionListener(this);
		
		// Add listeners to radio buttons
		radA.addActionListener(this);
		radB.addActionListener(this);
		radC.addActionListener(this);
		radD.addActionListener(this);
	}
	
	private void setUpPanels() {
		// Set up grid panel for adding questions
		pnlAddInfo.add(lblQuestType);
		pnlAddInfo.add(cmbQuestType);
		pnlAddInfo.add(lblQuestAdd);
		pnlAddInfo.add(txtQuestAdd);
		pnlAddInfo.add(lblChoiceAAdd);
		pnlAddInfo.add(txtChoiceA);
		pnlAddInfo.add(lblChoiceBAdd);
		pnlAddInfo.add(txtChoiceB);
		pnlAddInfo.add(lblChoiceCAdd);
		pnlAddInfo.add(txtChoiceC);
		pnlAddInfo.add(lblChoiceDAdd);
		pnlAddInfo.add(txtChoiceD);
		pnlAddInfo.add(lblAnswerAdd);
		pnlAddInfo.add(cmbAnsChoices);
		
		// Set up panel of buttons for adding questions
		pnlAddButtons.add(btnAdd);
		pnlAddButtons.add(btnWrite);
		pnlAddButtons.add(btnDone2);
		pnlAddButtons.add(btnQuit3);
		
		// Set up Hint/Comment portion for adding questions
		pnlHint.add(lblHint);
		pnlHint.add(txtHint);
		pnlComment.add(lblComment);
		pnlComment.add(txtComment);
		pnlHintComment.add(pnlBlank, BLANK);
		pnlHintComment.add(pnlHint, HINT);
		pnlHintComment.add(pnlComment, COMMENT);
		
		// Add Radio buttons to button group
		bgChoices.add(radA);
		bgChoices.add(radB);
		bgChoices.add(radC);
		bgChoices.add(radD);
		
		// Add Radio buttons to panel
		pnlRadioButtons.add(radA);
		pnlRadioButtons.add(radB);
		pnlRadioButtons.add(radC);
		pnlRadioButtons.add(radD);
		
		// Set up grid with quiz choices and answer result
		pnlQuizChoices.add(pnlRadioButtons);
		pnlQuizChoices.add(lblResult);
		
		// Set up panel of buttons for taking quiz
		pnlQuizButtons.add(btnSubmit);
		pnlQuizButtons.add(btnNext);
		pnlQuizButtons.add(btnDone1);
		pnlQuizButtons.add(btnQuit2);
		
		// Set up main GUI faces: Welcome, Add Question, Take Quiz
		pnlWelcome.add(btnAddQuest);
		pnlWelcome.add(btnTake);
		pnlWelcome.add(btnQuit1);
		
		pnlAddQuest.add(pnlAddInfo, BorderLayout.NORTH);
		pnlAddQuest.add(pnlHintComment, BorderLayout.CENTER);
		pnlAddQuest.add(pnlAddButtons, BorderLayout.SOUTH);
		
		pnlTakeQuiz.add(lblQuest, BorderLayout.NORTH);
		pnlTakeQuiz.add(pnlQuizChoices, BorderLayout.CENTER);
		pnlTakeQuiz.add(pnlQuizButtons, BorderLayout.SOUTH);
		
		// Add everything to CardLayout base
		pnlCard.add(pnlWelcome, WELCOME);
		pnlCard.add(pnlAddQuest, ADD_QUEST);
		pnlCard.add(pnlTakeQuiz, TAKE_QUIZ);		
	}
	
	public static void main(String[] args) {
		try {
			if (args.length > 0)
				new BookQuizGUI(args[0]);
			else
				new BookQuizGUI(null);
		} catch (QuestionException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid File");
		}
	}
}
