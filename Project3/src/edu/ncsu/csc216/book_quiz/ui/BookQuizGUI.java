package edu.ncsu.csc216.book_quiz.ui;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import edu.ncsu.csc216.book_quiz.quiz.*;
import edu.ncsu.csc216.question_library.QuestionException;

public class BookQuizGUI extends JFrame {

	// Backend model
	private QuizMaster quiz;
	
	// Parameters for component sizes and spacings 
	private static final int FRAME_WIDTH = 500; 
	private static final int FRAME_HEIGHT = 600;
	private static final int FIELD_LENGTH = 100;
	
	// Panel and window titles
	private static final String WINDOW_TITLE = "Book Quiz";
	
	// Button and text field strings
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
	private JLabel lblQuest;
	private JLabel lblResult;
	
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
	
	// Organizational and alignment boxes and panels
	
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
	
	private void initializeUI() {
		//  Initialize the main frame parameters.
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setTitle(WINDOW_TITLE);
		
	}
	
	public static void main(String[] args) throws QuestionException {
		try {
			if (args.length > 0)
				new BookQuizGUI(args[0]);
			else
				new BookQuizGUI(null);
		} catch (QuestionException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect File Specified");
			String[] s = new String[0];
			main(s);
		}
	}
}
