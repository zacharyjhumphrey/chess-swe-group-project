package common;

import java.util.ArrayList;

public class InitialBoardPosition {
	public static PieceData[][] getBoardStartingPosition() {
//		Class[][] positions = {
//				{ Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class }, 
//				{ Pawn.class, Pawn.class,   Pawn.class,   Pawn.class,  Pawn.class, Pawn.class, 	 Pawn.class,   Pawn.class }, 
//				{ null, 	  null, 		null, 		  null,		   null,  	   null, 	     null, 		   null }, 
//				{ null, 	  null, 		null, 		  null,		   null,  	   null, 	     null, 		   null }, 
//				{ null, 	  null, 		null, 		  null,		   null,  	   null, 	     null, 		   null }, 
//				{ null, 	  null, 		null, 		  null,		   null,  	   null, 	     null, 		   null }, 
//				{ Pawn.class, Pawn.class,   Pawn.class,   Pawn.class,  Pawn.class, Pawn.class, 	 Pawn.class,   Pawn.class }, 
//				{ Rook.class, Knight.class, Bishop.class, Queen.class, King.class, Bishop.class, Knight.class, Rook.class }, 
//		};
//		
//		PieceData[][] toReturn = new PieceData[8][8];
//		
//		for (int x = 0; x < 8; x++) {
//			for (int y = 0; y < 8; y++) {
//				Class pieceClass = positions[x][y];
//				if (pieceClass == null) {
//					continue;
//				}
//				TODO this approach could work, just need to figure out this last part
//				toReturn[x][y] = new pieceClass(Color.w, 0, 0);
//			}
//		}
		
		PieceData[][] toReturn = new PieceData[8][8];
		toReturn[0][0] = new Rook(Color.b, 0, 0);
		toReturn[1][0] = new Knight(Color.b, 1, 0);
		toReturn[2][0] = new Bishop(Color.b, 2, 0);
		toReturn[3][0] = new Queen(Color.b, 3, 0);
		toReturn[4][0] = new King(Color.b, 4, 0);
		toReturn[5][0] = new Bishop(Color.b, 4, 0);
		toReturn[6][0] = new Knight(Color.b, 4, 0);
		toReturn[7][0] = new Rook(Color.b, 4, 0);
		for (int x = 0; x < 8; x++) {
			toReturn[x][1] = new Pawn(Color.b, x, 1);
		}
		
		for (int x = 0; x < 8; x++) {
			toReturn[x][6] = new Pawn(Color.w, x, 6);
		}
		toReturn[0][7] = new Rook(Color.w, 0, 7);
		toReturn[1][7] = new Knight(Color.w, 1, 7);
		toReturn[2][7] = new Bishop(Color.w, 2, 7);
		toReturn[3][7] = new Queen(Color.w, 3, 7);
		toReturn[4][7] = new King(Color.w, 4, 7);
		toReturn[5][7] = new Bishop(Color.w, 5, 7);
		toReturn[6][7] = new Knight(Color.w, 6, 7);
		toReturn[7][7] = new Rook(Color.w, 7, 7);

		return toReturn;
	}
}
