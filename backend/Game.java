package backend;

public class Game {
	private PieceData[][] board = new PieceData[8][8];
	private PieceData[] pieces = new PieceData[32];
	
	public void initializeBoard() {
		pieces[0] = new PieceData("b","rook",0,0);
		pieces[1] = new PieceData("b","knight",1,0);
		pieces[2] = new PieceData("b","bishop",2,0);
		pieces[3] = new PieceData("b","queen",3,0);
		pieces[4] = new PieceData("b","king",4,0);
		pieces[5] = new PieceData("b","bishop",5,0);
		pieces[6] = new PieceData("b","knight",6,0);
		pieces[7] = new PieceData("b","rook",7,0);
		for (int x = 0; x<board.length;x++) {
			pieces[8+x] = new PieceData("b","pawn",x,1);
		}
		for (int x = 0; x<board.length;x++) {
			pieces[16+x] = new PieceData("w","pawn",x,6);
		}
		pieces[24] = new PieceData("w","rook",0,7);
		pieces[25] = new PieceData("w","knight",1,7);
		pieces[26] = new PieceData("w","bishop",2,7);
		pieces[27] = new PieceData("w","queen",3,7);
		pieces[28] = new PieceData("w","king",4,7);
		pieces[29] = new PieceData("w","bishop",5,7);
		pieces[30] = new PieceData("w","knight",6,7);
		pieces[31] = new PieceData("w","rook",7,7);
		
		
		
		updateBoard();
	}
	
	public void updateBoard() {
		board = new PieceData[8][8];
		
		for(int i = 0; i < pieces.length;i++) {
			board[pieces[i].getPosition().y][pieces[i].getPosition().x] = pieces[i];
		}
		System.out.println(this.toString());
	}
	
	
	public boolean inbounds(int x, int y) {
		
		if(x<0 || x>board.length) {
			return false;
		}
		if(y<0 || y>board[x].length) {
			return false;
		} 
		
		return true;
	}
	
	public PieceData[] getPieces() {
		return pieces;
	}
	
	public String toString() {
		String r = "";
		for(int i = 0; i<board.length; i++) {
			for(int j = 0; j<board[i].length; j++) {
				if(board[i][j]!=null) {
					String t=String.format("%10s", board[i][j].getColor()+","+board[i][j].getType());
				r+="|"+t+"|";
				}
				else {
					String t=String.format("%10s", "NULL");
					r+="|"+t+"|";
				}
			}
			r+="\n";
		}
		
		return r;
	}

}
