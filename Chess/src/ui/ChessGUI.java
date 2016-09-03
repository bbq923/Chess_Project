package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.ChessGame;
import ui.eventlistener.ButtonClickListener;

public class ChessGUI {
	
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private Image[][] chessPieceImages = new Image[2][6];
	private JPanel chessBoard;
	private final JLabel message = new JLabel(
			"Ready to play!");
	private static final String COLS = "ABCDEFGH";
	public static final int ROOK = 0,  KNIGHT = 1,
			BISHOP = 2, KING = 3, QUEEN = 4, PAWN = 5;
	public static final int[] STARTING_ROW = {
		ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
	};
	public static final int BLACK = 0, WHITE = 1;
	public static ChessGame myGame; // 새로운 게임을 시작할 때(new 버튼을 누를 때)마다 myGame = new ChessGame(); 을 해준다.
	
	public ChessGUI() {
		initializeGui();
		
	}
	
	
	
	
	
	public final void initializeGui() {
		// create the images for the chess pieces
		createImages();
		
		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
		Action newGameAction = new AbstractAction("New") {

			@Override
			public void actionPerformed(ActionEvent e) {
				setupNewGame();
			}
			
		};
		tools.add(newGameAction);
		tools.add(new JButton("Save")); // TODO - add functionality!
		tools.add(new JButton("Restore")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(new JButton("Resign")); // TODO - add functionality!
		tools.addSeparator();
		tools.add(message);
		
		gui.add(new JLabel("?"), BorderLayout.LINE_START);
		
		chessBoard = new JPanel(new GridLayout(0, 9)) {
			
			/**
			 *  Override the preferred size to return the largest it can, in
			 *  a square shape. Must (must, must) be added to a GridBagLayout
			 *  as the only component (it uses the parent as a guide to size)
			 *  with no GridBagConstaint (so it is centered).
			 */
			@Override
			public final Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				Dimension prefSize = null;
				Component c = getParent();
				if (c == null) {
					prefSize = new Dimension(
							(int)d.getWidth(), (int)d.getHeight());
				} else if (c != null &&
						c.getWidth() > d.getWidth() &&
						c.getHeight() > d.getHeight()) {
					prefSize = c.getSize();
				} else {
					prefSize = d;
				}
				int w = (int)prefSize.getWidth();
				int h = (int)prefSize.getHeight();
				// the smaller of the two sizes
				int s = ( w > h ? h : w);
				return new Dimension(s, s);
			}
		};
		
		chessBoard.setBorder(new CompoundBorder(
				new EmptyBorder(8, 8, 8, 8),
				new LineBorder(Color.BLACK)
				));
		// Set the BG to be ochre
		Color ochre = new Color(204, 119, 34);
		chessBoard.setBackground(ochre);
		JPanel boardConstrain = new JPanel(new GridBagLayout());
		boardConstrain.setBackground(ochre);
		boardConstrain.add(chessBoard);
		gui.add(boardConstrain);
		
		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(buttonMargin);
				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				ImageIcon icon = new ImageIcon(
						new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				if ((ii % 2 == 1 && jj % 2 == 1)
						//) {
						|| (ii % 2 == 0 && jj % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.BLACK);
				}
				chessBoardSquares[ii][jj] = b;
			}
		}
		
		
		
		/*
		 * fill the chess board
		 */
		chessBoard.add(new JLabel(""));
		// fill the top row
		for (int ii = 0; ii < 8; ii++) {
			chessBoard.add(
					new JLabel(COLS.substring(ii, ii + 1),
					SwingConstants.CENTER));
		}
		// fill the black non-pawn piece row
		for (int ii = 0; ii < 8; ii++) {
			for (int jj = 0; jj < 8; jj++) {
				switch (jj) {
					case 0:
						chessBoard.add(new JLabel("" + (9 - (ii + 1)),
								SwingConstants.CENTER));
					default:
						chessBoard.add(chessBoardSquares[ii][jj]);
				}
			}
		}
	}
	
	public final JComponent getGui() {
		return gui;
	}
	
	private final void createImages() {
		try {
			URL url = new URL("http://i.stack.imgur.com/222NX.png");
			BufferedImage bi = ImageIO.read(url);
			for (int ii = 0; ii < 5; ii++) {
				chessPieceImages[BLACK][ii] = bi.getSubimage(
						ii * 64, 0, 64, 64);
				chessPieceImages[WHITE][ii] = bi.getSubimage(
						ii * 64, 448, 64, 64);
			}
			chessPieceImages[BLACK][PAWN] = bi.getSubimage(64, 64, 64, 64);
			chessPieceImages[WHITE][PAWN] = bi.getSubimage(0, 384, 64, 64);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Initializes the icons based on chessgame.getboard(x, y) value
	 */
	private final void setupNewGame() {
		myGame = new ChessGame(); // init new ChessGame
		
		message.setText("Make your move!");
		
		// remove ActionListeners from previous game.
		for ( JButton[] jbtnArr : chessBoardSquares) {
			for ( JButton jbtn : jbtnArr) {
				for ( ActionListener al : jbtn.getActionListeners()) {
					jbtn.removeActionListener(al);
				}
			}
		}
		
		// put client property "row" and "column" for each JButton in 8 by 8
		// board. and add event listener
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			for (int jj = 0; jj < STARTING_ROW.length; jj++) {
				chessBoardSquares[ii][jj].putClientProperty("row", ii);
				chessBoardSquares[ii][jj].putClientProperty("column", jj);
				chessBoardSquares[ii][jj].addActionListener(new ButtonClickListener(this));
			} 
			
		}
		
		// set up piece icons
		initImage();
		
//		// set up the black pieces
//		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//			chessBoardSquares[0][ii].setIcon(new ImageIcon(
//					chessPieceImages[BLACK][STARTING_ROW[ii]]));
//		}
//		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//			chessBoardSquares[1][ii].setIcon(new ImageIcon(
//					chessPieceImages[BLACK][PAWN]));
//		}
//		
//		// set up the empty pieces 
//		for (int ii = 2; ii < 6; ii++) {
//			for (int jj = 0; jj < STARTING_ROW.length; jj++) {
//				chessBoardSquares[ii][jj].setIcon(null);
//			}
//		}
//		
//		// set up the white pieces
//		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//			chessBoardSquares[6][ii].setIcon(new ImageIcon(
//					chessPieceImages[WHITE][PAWN]));
//		}
//		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//			chessBoardSquares[7][ii].setIcon(new ImageIcon(
//					chessPieceImages[WHITE][STARTING_ROW[ii]]));
//		}
		
		myGame.printBoard();
	}
	
	private void initImage() {
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			for (int jj = 0; jj < STARTING_ROW.length; jj++) {
				char currentPiece = myGame.getBoard(ii, jj);
				if (currentPiece == 'R') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][ROOK]));
				} else if (currentPiece == 'r') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][ROOK]));
				} else if (currentPiece == 'N') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][KNIGHT]));
				} else if (currentPiece == 'n') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][KNIGHT]));
				} else if (currentPiece == 'B') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][BISHOP]));
				} else if (currentPiece == 'b') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][BISHOP]));
				} else if (currentPiece == 'K') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][KING]));
				} else if (currentPiece == 'k') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][KING]));
				} else if (currentPiece == 'Q') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][QUEEN]));
				} else if (currentPiece == 'q') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][QUEEN]));
				} else if (currentPiece == 'P') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[BLACK][PAWN]));
				} else if (currentPiece == 'p') {
					chessBoardSquares[ii][jj].setIcon(new ImageIcon(
							chessPieceImages[WHITE][PAWN]));
				}	else {
					chessBoardSquares[ii][jj].setIcon(null);
				}
			}
		}
	}
	// 외부 클래스(이벤트 리스너)에서 JLabel message를 set 해주기 위한 메소드
	public void setMessage(String message) {
		this.message.setText(message);
	}
	
	// 외부 클래스(이벤트 리스너)에서 JButton selectedButton을 set 해주기 위한 메소드
//	public void setSelectedButton(int ii, int jj) {
//		this.selectedButton = chessBoardSquares[ii][jj];
//	}
//	
//	public JButton getSelectedButton() {
//		return this.selectedButton;
//	}

	// chessBoardSquares로부터 JButton을 리턴 받는 메소드
	public JButton getButton(int row, int column) {
		return chessBoardSquares[row][column];
	}
	
	// main 함수는 MainTest 부분으로 이동.
/*
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				ChessGUI cg = new ChessGUI();
				
				JFrame f = new JFrame("ChessChamp");
				f.add(cg.getGui());
				// Ensures JVM closes after frame(s) closed and
				// all non-daemon threads are finished
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				// See http://stackoverflow.com/a/7143398/418556 for demo.
				f.setLocationByPlatform(true);
				
				// ensures the frame is the minimum size it needs to be
				// in order display the components within it
				f.pack();
				// ensure the minimum size is enforced.
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		};
		// Swing GUIs should be created and updated on the EDT
		// http://docs.oracle.com/javase/tutorial/uiswing/concurrency
		SwingUtilities.invokeLater(r);
	}
*/
}










