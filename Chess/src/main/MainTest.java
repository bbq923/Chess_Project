package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import board.ChessBoard;
import util.Move;

public class MainTest {

	public static void main(String[] args) throws IOException {
		ChessBoard cBoard = ChessBoard.getInstance();
		BufferedReader br = null;
		int prevX, prevY, postX, postY;
		int turnCount = 1;
		char piece;
		
		cBoard.printBoard();
		
		while(true){
			if ((turnCount % 2) == 1) {
				System.out.println("흑의 턴입니다.");
			} else {
				System.out.println("백의 턴입니다.");
			}
			
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("선택하고자 하는 말의 위치를 입력해 주세요 :");
			String input = br.readLine();
			
			prevX = 56 - input.charAt(0); // 1 ~ 8 은 아스키 코드의 49 ~ 57과 대응한다. 
			prevY = input.charAt(2) - 49;
			piece = cBoard.getBoard(prevX, prevY);
			
			if(piece == 'o') { // 빈 칸일 경우 
				System.out.println("비어 있는 칸입니다.");
				continue;
			} else if ((turnCount % 2 == 1) && (piece > 95)) { // 흑의 턴인데 백의 말(소문자)을 선택한 경우 
				System.out.println("백 플레이어의 말을 선택했습니다. 다시 선택해 주세요.");
				continue;
			} else if ((turnCount % 2 == 0) && (piece < 95)) { // 백의 턴인데 흑의 말(대문자)을 선택한 경우 
				System.out.println("흑 플레이어의 말을 선택했습니다. 다시 선택해 주세요.");
			} else {
				System.out.println("선택한 말은 " + cBoard.getBoard(prevX, prevY) 
				+ "입니다.");

				System.out.println("이동하고자 하는 위치를 입력해 주세요 :");
				input = br.readLine();

				postX = 56 - input.charAt(0);
				postY = input.charAt(2) - 49;
				//System.out.println("destination:" + postX + "," + postY); // for test only
				
				
				boolean isMoved = Move.movePiece(prevX, prevY, postX, postY, cBoard);
				cBoard.printBoard();
				
				if(isMoved){
					turnCount++;
				}
			}
			
		}
	}

}
