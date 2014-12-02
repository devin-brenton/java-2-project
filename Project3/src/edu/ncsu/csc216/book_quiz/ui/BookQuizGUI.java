package edu.ncsu.csc216.book_quiz.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import edu.ncsu.csc216.question_library.QuestionException;

public class BookQuizGUI extends JFrame implements ActionListener {

	// Backend model
	private QuizMaster quiz;
	
	// Parameters for component sizes and spacings 
	private static final int FRAME_WIDTH = 500; 
	private static final int FRAME_HEIGHT = 600;
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
	private static final String[] QUEST_TYPES = {"Elementary Question", "Standard Question", "Advanced Question"};
	private static final String[] ANS_CHOICES = {"A", "B", "C", "D"};
	
	// Buttons
	private JButton btnAddQuest = new JButton(ADD_QUEST);
	private JButton btnTake = new JButton(TAKE_QUIZ);
	private JButton btnQuit = new JButton(QUIT);
	private JButton btnAdd = new JButton(ADD);
	private JButton btnWrite = new JButton(WRITE);
	private JButton btnDone = new JButton(DONE);
	private JButton btnSubmit = new JButton(SUBMIT);
	private JButton btnNext = new JButton(NEXT);
	
	// Combo Boxes
	private JComboBox cmbQuestType = new JComboBox(QUEST_TYPES);
	private JComboBox cmbAnsChoices = new JComboBox(ANS_CHOICES);
	
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
	
	// Text fields
	private JTextField txtQuestAdd = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceA = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceB = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceC = new JTextField(FIELD_LENGTH);
	private JTextField txtChoiceD = new JTextField(FIELD_LENGTH);
	
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
	private JPanel pnlWelcomeButtons = new JPanel();
	private JPanel pnlAddInfo = new JPanel(new GridLayout(7, 2));
	private JPanel pnlAddButtons = new JPanel();
	private JPanel pnlQuizChoices = new JPanel(new GridLayout(2,1));
	private JPanel pnlQuizButtons = new JPanel();
	private JPanel pnlRadioButtons = new JPanel();
	
	// Main window
	private Container mainWindow = getContentPane();
	
	public BookQuizGUI(String filename) throws QuestionException {
		try {
			if (filename == null) {
				String userPickFilename = null;
				JFileChooser fc = new JFileChooser(".");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					userPickFilename = fc.getSelectedFile().getName();
					System.out.println("You chose to open this file: " + fc.getSelectedFile().getName());
				}
				quiz = new BookQuiz(userPickFilename);
			} else {
				quiz = new BookQuiz(filename);
			}
			initializeUI();
			this.setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), ERROR, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void initializeUI() {
		//  Initialize the main frame parameters.
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(WINDOW_TITLE);
		
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
		pnlAddButtons.add(btnDone);
		pnlAddButtons.add(btnQuit);
		
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
		pnlQuizButtons.add(btnDone);
		pnlQuizButtons.add(btnQuit);
		
		// Set up main GUI faces: Welcome, Add Question, Take Quiz
		pnlWelcome.add(btnAddQuest);
		pnlWelcome.add(btnTake);
		pnlWelcome.add(btnQuit);
		
		pnlAddQuest.add(pnlAddInfo, BorderLayout.NORTH);
		pnlAddQuest.add(pnlAddButtons, BorderLayout.SOUTH);
		
		pnlTakeQuiz.add(lblQuest, BorderLayout.NORTH);
		pnlTakeQuiz.add(pnlQuizChoices, BorderLayout.CENTER);
		pnlTakeQuiz.add(pnlQuizButtons, BorderLayout.SOUTH);
		
		// Add everything to CardLayout base
		pnlCard.add(pnlWelcome, WELCOME);
		pnlCard.add(pnlAddQuest, ADD_QUEST);
		pnlCard.add(pnlTakeQuiz, TAKE_QUIZ);
		
		// Add Card base to window
		mainWindow.add(pnlCard);
	}
	
	private void enableDisable() {
		
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
