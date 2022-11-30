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
		toReturn[0][0] = new Rook(Color.b, 1, 1);
		toReturn[0][1] = new Knight(Color.b, 1, 2);
		toReturn[0][2] = new Bishop(Color.b, 1, 3);
		toReturn[0][3] = new Queen(Color.b, 1, 4);
		toReturn[0][4] = new King(Color.b, 1, 5);
		toReturn[0][5] = new Bishop(Color.b, 1, 6);
		toReturn[0][6] = new Knight(Color.b, 1, 7);
		toReturn[0][7] = new Rook(Color.b, 1, 8);
		for (int x = 0; x < 8; x++) {
			toReturn[1][x] = new Pawn(Color.b, 2, x+1);
			toReturn[6][x] = new Pawn(Color.w, 7, x+1);
		}
		
		toReturn[7][0] = new Rook(Color.w, 8, 1);
		toReturn[7][1] = new Knight(Color.w, 8, 2);
		toReturn[7][2] = new Bishop(Color.w, 8, 3);
		toReturn[7][3] = new Queen(Color.w, 8, 4);
		toReturn[7][4] = new King(Color.w, 8, 5);
		toReturn[7][5] = new Bishop(Color.w, 8, 6);
		toReturn[7][6] = new Knight(Color.w, 8, 7);
		toReturn[7][7] = new Rook(Color.w, 8, 8);

		return toReturn;
	}
}
