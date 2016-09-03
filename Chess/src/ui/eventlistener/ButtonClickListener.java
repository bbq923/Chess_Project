package ui.eventlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import main.ChessGame;
import ui.ChessGUI;
import util.Move;

public class ButtonClickListener implements ActionListener{
	ChessGUI chessgui; // chessGUI 객체 담을 곳 
	ChessGame chessgame = chessgui.myGame; // 클릭한 위치 정보를 넘겨 처리해 줄 ChessGame 클래스
	
	public ButtonClickListener(ChessGUI superClass) {
		this.chessgui = superClass;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(chessgame.getSelectedX() + ", " + chessgame.getSelectedY() + "selected");
		
		JButton btn = (JButton)e.getSource();
		Icon tempIcon;
		int row = (int)btn.getClientProperty("row");
		int column = (int)btn.getClientProperty("column");
		
		if (chessgame.getSelectFlag() == ChessGame.SELECT) {
			if (chessgame.getBoard(row, column) == 'o') {
				chessgui.setMessage("no piece selected.");
				return;
			} 
			
			if ((chessgame.getBoard(row, column) < 95) && (chessgame.getTurnCount() % 2 == 0)) {
				chessgui.setMessage("White Turn. please select white piece.");
				return;
			} 
			
			if ((chessgame.getBoard(row, column) > 95) && (chessgame.getTurnCount() % 2 == 1)) {
				chessgui.setMessage("Black Turn. please select black piece.");
				return;
			} 

			String message = "clicked row " + row + ", column " + column + " Chess piece "
					+ chessgame.getBoard(row, column) + " selected.";
			//chessgui.setSelectedButton(row, column);
			chessgame.setSelectedPiece(row, column); // notify ChessGame
														// instance on which
														// piece is selected
			System.out.println("select & show selected piece : " + chessgame.getSelectedPiece());
			chessgui.setMessage(message);
			chessgame.setSelectFlag(ChessGame.MOVE); // set selectFlag to MOVE

		} else { // when selectFlag is set to MOVE
			JButton selectedBtn = chessgui.getButton(chessgame.getSelectedX(), chessgame.getSelectedY()); // chessgui.getSelectedButton();
			int moveResult = Move.movePiece(chessgame.getSelectedX(), 
											chessgame.getSelectedY(), 
											row, 
											column, 
											chessgame);
					
			if (moveResult == Move.SUCCESS) {	
//				chessgame.setBoard(chessgame.getSelectedX(), 
//					chessgame.getSelectedY(), 
//					'o');
//				chessgame.setBoard(row, column, chessgame.getSelectedPiece());
//				
				chessgame.increaseTurnCount();
				
				System.out.println();
				
				// chessGUI에서 표시하는 아이콘을 바꿔준다.
				tempIcon = selectedBtn.getIcon();
				btn.setIcon(tempIcon);
				//chessgui.getSelectedButton().setIcon(null); // set start position icon empty.
				selectedBtn.setIcon(null);
				
				chessgame.printBoard();
				
				// set superClass message
				chessgui.setMessage("select piece.");
			} else if (moveResult == Move.INVALID_MOVE) {
				chessgui.setMessage("cannot move to there.");
			} else if (moveResult == Move.BLOCKING_PATH) {
				chessgui.setMessage("other pieces blocking the way.");
			} else if (moveResult == Move.CANNOT_TAKE_ALLEY) {
				chessgui.setMessage("you cannot take your own piece.");
			} else if (moveResult == Move.VICTORY) {
				if (chessgame.getTurnCount() % 2 == 1) {
					chessgui.setMessage("Checkmate!!! Black player Win!!!");
				} else {
					chessgui.setMessage("Checkmate!!! White player Win!!!");
				}
			} else {
				chessgui.setMessage("Uncaught Exception Occured!!!");
			}
			
			// set selectFlag to SELECT
			chessgame.setSelectFlag(ChessGame.SELECT);
		}
		
	}

}
