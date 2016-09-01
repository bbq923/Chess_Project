# Chess_Project

체스의 룰 중 기본적인 부분(말을 움직일 수 있는 규칙)만 우선 구현한다. (프로모션 등 부가적인 룰은 일단 무시)
실제 체스 경기에선 물리적으로 킹을 잡을 순 없고 체크메이트 상태에서 게임이 종료되지만 우선은 킹이 잡혔다는 걸 명시적으로 확인하는 것으로 승패를 결정하도록 한다.
A, B, C… 알파벳을 각각 1, 2, 3… 에 대응하였을 때 백색 칸은 x축과 y축 값의 합이 홀수, 흑색 칸은 짝수인 경우이다.

-체스판은 8 by 8의 흑백이 교차로 놓이는 판이다.
-유저로 부터 움직일 말이 놓인 위치를 입력받는다.
-현재 백의 턴인데 흑의 말을 선택한 경우, 혹은 선택할 수 있는 말이 없는 빈 칸을 선택한 경우 경고메시지를 표시한다.
-말을 선택하면 그 다음엔 이동할 위치를 입력받는다.
-입력 받은 위치와 선택한 위치에 놓인 말의 이동규칙을 비교해 가능한지 판별한다.
-가능하다면 움직여준다. 움직인 위치에 다른 말이 있다면 그 말을 잡는다. 잡은 말이 킹이라면 승리한다.

클래스 Piece  //초기에는 보드판 위의 char 값으로 말들을 나타내므로 필요 없는 클래스.
-boolean isFirstTime 처음 움직이는 것인가를 나타낸다. (폰의 최초 움직임, 혹은 캐슬링 가능 여부를 판별할 때 사용)
-String 말의 종류 (체스 말마다 별도의 클래스를 두는 것보다 Piece 클래스 안에 String값으로 말의 종류를 가지고 있는게 좋을 것 같다. 폰의 경우 프로모션 해서 바뀔 수도 있고.)

이동 가능한지 판별하는 Move 클래스

개요 : 사용자가 선택한 말을 사용자가 선택한 위치로 옮길 수 있는 지 판별한다.

이동 규칙

공통
움직이는 경로상에 가로 막는 다른 체스말은 존재하는가? Yes 에러메시지
이건 각 말별로 이동하려고 하는 위치의 유효성을 검사한 뒤 이동하려는 위치와 현재 위치 사이에 다른 말이 없는지 반복적으로
확인 해준다. (나이트 제외)

폰(흑의 경우)
처음 움직이는 것인가?
처음 움직이는 지 여부는
Yes -> (x-1, y) or (x-2, y)
No -> (x-1, y)
대각선 방향에 잡을 수 있는 유닛이 있는가?
Yes -> (x-1, y-1) or (x-1, y+1)

폰(백의 경우)
흑 반대로

룩
움직이려는 곳의 x 나 y 좌표가 현재와 같은가?
if( x == currentX || y == currentY ) return true;

비숍
 if( ((x - currentX) / (y - currentY)) == 1 or -1) return true;

나이트
(currentX + current Y) - (x + y) == 3 AND currentX != x AND currentY != y

퀸
룩 or 비숍

킹
(currentX + current Y) - (x + y) == 1

체스보드
-Piece를 원소로 갖는 8 x 8 의 ArrayList를 가진다.
printChessBoard()
체스 보드를 프린트 한다.




우선순위 순

1. 핵심기능 - game logic
ButtonClickListener에서 selectFlag 로 분기하는 이벤트 처리
SELECT일 때 - 클릭된 좌표정보를 이용해 chessBoard의 char selectedPiece 변수 변경
MOVE 일 때 - 클릭된 좌표정보를 이용해 chessBoard 인스턴스의 mainBoard 갱신, 변경사항을 ChessGUI에 반영

각각의 변경사항은 GUI 화면의 JLabel message를 갱신해서 표시한다.

2. 핵심기능 - newGame
newGame 시 mainBoard에 이전 게임의 정보가 남아있다.
하나의 게임을 관리하는 ChessGame 클래스를 만들고, 초기화 할 때  singleton하게 구현된 ChessBoard 클래스를 사용한다.

3. 부가기능 - chess board graphic
현재 체스판의 아이콘의 흑백이 제대로 표시되지 않고 있다. 체스판은 흑백의 격자무늬, piece의 이동에 따라 판의 무늬가 바뀌는 일도 없게 한다. 아마도 체스 배경이 piece그림에 투영되면 될 것 같은데, piece를 getSubimage로 자를 때부터 이미 배경 색을 포함하는 정사각형으로 잘라져서 불가능할까?

4. 부가기능 - save, restore, resign
save를 하면 현재 게임 상태를 serialization ( 그런데 이 과정이 필요할까?) 해서 File I/O를 통해 저장한다.
restore를 하면 저장된 게임 상태를 다시 복원한다.
resign하면 resign 버튼을 누른 player의 상대 플레이어가 이겼음을 표시하고 new Game을 누르도록 유도한다.

@@@@ 고난도
5. 부가기능 - local server
같은 wifi를 이용하고 있으면 내가 local server에서 Chess application을 구동시키고 있을 때 해당 URL로 접근하여 두명의 플레이어가 대전가능하다. 가능하다면 이 부분은 발전 시켜서 WEB/UI Basic 과제로도 제출할 수 있으면 좋겠다.

방만들기 기능
1명이 만들면 웹 페이지에 생성된 방이 표시되고, 다른 플레이어가 해당 방을 방을 클릭해 게임을 시작할 수 있다.

로그인 기능을 추가한다. ( + 회원가입 기능)
게임의 결과를 저장한다. 승패를 포인트로 환산한다.
획득한 포인트 별로 랭크를 부여한다. 랭크 간 승부 결과는 포인트 가감산에 차등적으로 반영한다.
채팅 패널을 추가한다.

궁금한 점
Move 클래스에서 이동 상황을 처리할 때 ChessGame 인스턴스가 필요한 건 가장 최심부의 isPossibleMoveForRook 과
isPossibleMoveForBishop 뿐인데 이 두 메소드를 위하여 3단계 위의 메소드부터 ChessGame chessGame을 인자로 받아 계속 넘겨주어야 하나?
