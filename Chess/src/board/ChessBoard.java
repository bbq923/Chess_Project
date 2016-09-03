package board;

/*
 *  새로운 ChessGame을 시작할 때 초기화를 할 때 사용하는 클래스.
 *  initBoard() 메소드를 사용해 새로운 보드를 얻을 수 있다.
 */

public class ChessBoard {
	private static char mainBoard[][] = {{'R', 'N', 'B', 'K', 'Q', 'B', 'N', 'R' }, //흑백 말은 대소문자로 구분한다. 흑 대문자, 백 소문
			 							 {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
			 							 {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
			 							 {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
			 							 {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
			 							 {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
			 							 {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
			 							 {'r', 'n', 'b', 'k', 'q', 'b', 'n', 'r' }
										};
	
	private static ChessBoard myBoard = new ChessBoard(); // singleton pattern 
	
	// default constructor
	private ChessBoard() {
		
	}
	
//	public void setBoard(int x, int y, char piece) {
//		mainBoard[x][y] = piece;
//	}
//	
//	public char getBoard(int x, int y) {
//		return mainBoard[x][y];
//	}
//	
//	public static ChessBoard getInstance() {
//		return myBoard;
//	}
	
	public static char[][] getBoard() {
		return mainBoard;
	}
	
//	// 보드 출력
//	public void printBoard() {
//		for (int i = 0; i < 8; i++) {
//			System.out.print(8 - i);
//			//System.out.print(i); // for test only
//			for (int j = 0; j < 8; j++) {
//				System.out.print(" " + mainBoard[i][j]);
//			}
//			System.out.println();
//		}
//		System.out.print("  ");
//		for (int i = 1; i <= 8; i++) {
//			System.out.print(i + " ");
//			//System.out.print(i - 1 + " "); // for test only
//		}
//		System.out.println();
//	}

}


