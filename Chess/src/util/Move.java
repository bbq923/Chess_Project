package util;

import board.ChessBoard;
import main.ChessGame;

/*
 *  현재 위치와 이동시킬 위치, 체스보드를 입력으로 받아 입력한 이동의 유효성을 검사한다.
 *  검사한 결과에 따라 각 상황에 해당하는 상수 값을 리턴한다. 
 */
public class Move {
	public static final int SUCCESS = 0, INVALID_MOVE = 1, BLOCKING_PATH = 2, CANNOT_TAKE_ALLEY = 3, VICTORY = 4;
			
			
	public static int movePiece(int prevX, int prevY, int postX, int postY, ChessGame cBoard){
		char piece = cBoard.getBoard(prevX, prevY); //선택한 말
		char destPiece = cBoard.getBoard(postX, postY); //이동할 위치에 존재하는 말. 없다면 빈칸
		
		int isValid = isValidMove(prevX, prevY, postX, postY, piece, (int)destPiece, cBoard);
		int isPossible = isPossibleMove(prevX, prevY, postX, postY, piece, cBoard);
		
		if (isValid == SUCCESS) {
			if (isPossible == SUCCESS) {
				//유효한 이동인지 검사한 뒤 이동
				cBoard.setBoard(prevX, prevY, 'o');
				cBoard.setBoard(postX, postY, piece);
				
				if (destPiece == 'k' || destPiece == 'K') {
					System.out.println("CheckMate!! You Win!!!");
					return VICTORY;
				} else {
//					cBoard.increaseTurnCount();
					return SUCCESS;	
					
				}
				
			} else {
				System.out.println("진행 경로에 다른 말이 존재합니다.");
				return BLOCKING_PATH;
			}
			
			
		} else if (isValid == INVALID_MOVE){
			System.out.println("잘못된 이동입니다. 다시 선택해 주세요.");
			return INVALID_MOVE;
		} else if (isValid == CANNOT_TAKE_ALLEY) {
			System.out.println("같은 편을 잡을 수 없습니다. 다시 선택해 주세요.");
			return CANNOT_TAKE_ALLEY;
		}
		
		return INVALID_MOVE; // 위에서 SUCCESS에 걸리지 않았다면 뭐가 잘못되었는지는 몰라도 잘못된 Move.
		
	}
	
	public static int isValidMove(int prevX, int prevY, int postX, int postY, int piece, int destPiece, ChessGame cBoard) { 
		//현재 위치와 목표위치, 선택한 말과 목표위치의 말, 체스판을 인자로 받아 가능한 이동인지 검사
		
		if((prevX == postX) && (prevY == postY)) {
			return INVALID_MOVE; // 움직이지 않은 경우 처리
		}
		
		if(destPiece != 'o') { // 목표 위치에 같은 편 유닛이 있는 경우 처리
			if((piece > 95) && (destPiece > 95)) {
				System.out.println("같은 편 유닛을 잡을 수는 없습니다.");
				return CANNOT_TAKE_ALLEY;
			}
			if ((piece < 95) && (destPiece < 95)) {
				System.out.println("같은 편 유닛을 잡을 수는 없습니다.");
				return CANNOT_TAKE_ALLEY;
			}
		}
		
		if (piece == 'p') {  // 백 폰일 경우
			if (destPiece == 'o'){
				if ((postX + 1 == prevX) && (postY == prevY)) {
					return SUCCESS;
				} else if (prevX == 6) {
					if ((postX + 2 == prevX) && (postY == prevY)) {
						return SUCCESS;
					}
				}
			} else { 
				if ((prevX - postX) == 1 && Math.abs(prevY - postY) == 1) {
					if (destPiece < 95) { // 대각선으로 이동하려는 위치에 흑 말이 있을 경우 잡으면서 이동할 수 있다. 
						return SUCCESS;
					}
				}
			}
		}
		
		if (piece == 'P') { // 흑 폰일 경우
			if (destPiece == 'o') {
				if ((postX - 1 == prevX) && (postY == prevY)) {
					return SUCCESS;
				} else if (prevX == 1) {
					if ((postX - 2 == prevX) && (postY == prevY)) {
						return SUCCESS;
					}
				}
			} else {
				if ((postX - prevX) == 1 && Math.abs(prevY - postY) == 1) {
					if ((destPiece > 95) && (destPiece != 'o')) {
						return SUCCESS;
					}
				}
			}
			 
		}
		
		if (piece == 'r' || piece == 'R') { // 흑, 백 룩일 경우
			if (postX == prevX) {
				if (postY != prevY) {
					return SUCCESS;
				}
			} else {
				if (postY == prevY) {
					return SUCCESS;
				}
			}
		}
				
		if (piece == 'b' || piece == 'B') { // 흑, 백 비숍일 경우 
			int temp = (postX - prevX) / (postY - prevY);
			if (temp == 1 || temp == -1) {
				return SUCCESS;
			}
		}
		
		if (piece == 'n' || piece == 'N') { // 흑, 백 나이트일 경우
			int temp = Math.abs(postX - prevX) + Math.abs(postY - prevY);
			if (temp == 3) {
				if ((postX != prevX) && (postY != prevY)) {
					return SUCCESS;
				}
			}
		}
		
		if (piece == 'k' || piece == 'K') { // 흑, 백 킹일 경우 
			int tempX = Math.abs(postX - prevX);
			int tempY = Math.abs(postY - prevY);
			if((tempX < 2) && (tempY < 2)) {
				if((prevX == postX) && (prevY == postY)) {
					return INVALID_MOVE;
				} else {
					return SUCCESS;
				}
			}
		}
		
		if (piece == 'q' || piece == 'Q') { // 흑, 백 퀸일 경우
			// 룩과 같은 움직임을 했을 경우 
			if (postX == prevX) {
				if (postY != prevY) {
					return SUCCESS;
				}
			} else {
				if (postY == prevY) {
					return SUCCESS;
				}
			}
			// 비숍과 같은 움직임을 했을 경우 
			int temp = (postX - prevX) / (postY - prevY);
			if (temp == 1 || temp == -1) {
				return SUCCESS;
			}
		}
		
		// 모든 경우에 대해 유효한 움직임이 아닐 경우 잘못된 움직임이다.
		return INVALID_MOVE;
	}



	public static int isPossibleMove(int prevX, int prevY, int postX, int postY, char piece, ChessGame cBoard) {
		// 현재 위치와 목표위치 사이에 다른 말이 방해가 되어 갈 수 없는지 확인하는 함수 
		// 한 번에 한 칸씩 움직이는 폰과 킹, 장애물에 구애받지 않는 나이트를 제외한 룩, 비숍, 퀸에 대해 검사 
		if (piece == 'r' || piece == 'R') {
			return isPossibleMoveForRook(prevX, prevY, postX, postY, cBoard);
		}
		
		if (piece == 'b' || piece == 'B') {
			return isPossibleMoveForBishop(prevX, prevY, postX, postY, cBoard);
		}
		
		if (piece == 'q' || piece == 'Q') {
			if (isPossibleMoveForRook(prevX, prevY, postX, postY, cBoard) == SUCCESS || isPossibleMoveForBishop(prevX, prevY, postX, postY, cBoard) == SUCCESS) {
				return SUCCESS;
			}
		}
		
		return SUCCESS;
	}
	
	public static int isPossibleMoveForRook(int prevX, int prevY, int postX, int postY, ChessGame cBoard) {
		int i;
		
		if (prevX == postX) {
			if (prevY > postY) {
				for (i = postY + 1; i < prevY; i++) {
					if (cBoard.getBoard(prevX, i) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
			if (prevY < postY) {
				for (i = prevY + 1; i < postY; i++) {
					if (cBoard.getBoard(prevX, i) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
		} else if (prevY == postY) {
			if (prevX > postX) {
				for (i = postX + 1; i < prevX; i++) {
					if (cBoard.getBoard(i, prevY) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
			if (prevX < postX) {
				for (i = prevX + 1; i < postX; i++) {
					if (cBoard.getBoard(i, prevY) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
		}
		
		return SUCCESS;
	}
	
	public static int isPossibleMoveForBishop(int prevX, int prevY, int postX, int postY, ChessGame cBoard) {
		int i;
		
		if (prevX > postX) {
			if (prevY > postY) {
				for (i = 1; i < prevX - postX; i++) {
					if (cBoard.getBoard(postX + i, postY + i) != 'o') {
						return BLOCKING_PATH;
					}	
				}
			} else {
				for (i = 1; i < prevX - postX; i++) {
					if (cBoard.getBoard(postX + i, postY - i) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
		} else {
			if (prevY > postY) {
				for (i = 1; i < postX - prevX; i++) {
					if (cBoard.getBoard(prevX + i, prevY - i) != 'o') {
						return BLOCKING_PATH;
					}	
				}
			} else {
				for (i = 1; i < postX - prevX; i++) {
					if (cBoard.getBoard(prevX + i, prevY + i) != 'o') {
						return BLOCKING_PATH;
					}
				}
			}
		}
		
		return SUCCESS;
	}
}