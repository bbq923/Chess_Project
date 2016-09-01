package main;

import board.ChessBoard;

/*
 *  새로운 체스 게임을 생성할 때마다 ChessGame 클래스의 인스턴스를 생성하여 관리한다. 
 *  선택된 위치, 턴 수, 체스 보드 를 멤버 변수로 갖는다. 
 */
public class ChessGame {
	private char chessboard[][] = new char[8][8];
	private int turnCount = 1; // 시작 시 turnCount 1.
	private int selectedX, selectedY; // selectFlag == SELECT 일 때 선택한 위치는
										// ButtonClickListener에서 넘겨 주어
										// selectedX, selectedY에 저장된다.

	// Constructor
	public ChessGame() {
		initBoard();
		System.out.println("board initiated!");
	}

	public void initBoard() {
		char copyboard[][] = ChessBoard.getBoard();

		for (int ii = 0; ii < chessboard.length; ii++) {
			for (int jj = 0; jj < chessboard[ii].length; jj++) {
				chessboard[ii][jj] = copyboard[ii][jj]; // copy
														// ChessBoard.mainboard
														// to
														// ChessGame.chessboard
			}
		}
	}

	public char getBoard(int x, int y) {
		return chessboard[x][y];
	}

	public void setBoard(int x, int y, char piece) {
		chessboard[x][y] = piece;
	}

	public int getTurnCount() {
		return turnCount;
	}
	
	public void increaseTurnCount() {
		System.out.println("plus");
		turnCount++;
	}

	public void setSelectedPiece(int selectedX, int selectedY) {
		this.selectedX = selectedX;
		this.selectedY = selectedY;
	}

	public int getSelectedX() {
		return selectedX;
	}
	
	public int getSelectedY() {
		return selectedY;
	}
	
	public char getSelectedPiece() {
		return chessboard[selectedX][selectedY];
	}

	// 보드 출력
	public void printBoard() {
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i);
			// System.out.print(i); // for test only
			for (int j = 0; j < 8; j++) {
				System.out.print(" " + chessboard[i][j]);
			}
			System.out.println();
		}
		System.out.print("  ");
		for (int i = 1; i <= 8; i++) {
			System.out.print(i + " ");
			// System.out.print(i - 1 + " "); // for test only
		}
		System.out.println();
	}
}
