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
		JButton btn = (JButton)e.getSource();
		Icon tempIcon;
		int row = (int)btn.getClientProperty("row");
		int column = (int)btn.getClientProperty("column");
		
		if (chessgui.getSelectFlag() == ChessGUI.SELECT) {
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
			chessgui.setSelectedButton(row, column);
			chessgame.setSelectedPiece(row, column); // notify ChessGame
														// instance on which
														// piece is selected
			chessgui.setMessage(message);
			chessgui.setSelectFlag(ChessGUI.MOVE); // set selectFlag to MOVE

		} else { // when selectFlag is set to MOVE
			JButton selectedBtn = chessgui.getSelectedButton();
			int moveResult = Move.movePiece(chessgame.getSelectedX(), 
											chessgame.getSelectedY(), 
											row, 
											column, 
											chessgame);
					
			if (moveResult == Move.SUCCESS) {		
				chessgame.setBoard(row, column, chessgame.getBoard((int)selectedBtn.getClientProperty("row"), 
					(int)selectedBtn.getClientProperty("column")));
				chessgame.setBoard((int)selectedBtn.getClientProperty("row"), 
					(int)selectedBtn.getClientProperty("column"), 
					'o');
				
				System.out.println("turn increase start");
				chessgame.increaseTurnCount();
				System.out.println("turn increase end " + chessgame.getTurnCount());
				
				chessgame.printBoard(); // FOR TEST ONLY
				System.out.println();
				
				// chessGUI에서 표시하는 아이콘을 바꿔준다.
				tempIcon = selectedBtn.getIcon();
				btn.setIcon(tempIcon);
				chessgui.getSelectedButton().setIcon(null); // set start position icon empty.
				
				// set selectFlag to SELECT
				chessgui.setSelectFlag(ChessGUI.SELECT);
				
				// set superClass message
				chessgui.setMessage("select piece.");
			} else if (moveResult == Move.INVALID_MOVE) {
				chessgui.setMessage("cannot move to there.");
			} else if (moveResult == Move.BLOCKING_PATH) {
				chessgui.setMessage("other pieces blocking the way.");
			} else if (moveResult == Move.CANNOT_TAKE_ALLEY) {
				chessgui.setMessage("you cannot take your own piece.");
			} else if (moveResult == Move.VICTORY) {
				chessgui.setMessage("You Win!!!");
			} else {
				chessgui.setMessage("Uncaught Exception Occured!!!");
			}
		}
	}

}
