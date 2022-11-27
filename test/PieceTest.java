package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import backend.Game;
import common.AvailableMoves;
import common.Board;
import common.Pawn;
import common.PieceColor;
import common.PieceData;
import common.PositionData;

public class PieceTest {
	// TODO implement board into tests 
//	@Test
//	public void testKnightAvailableMoves() {
//		Game g = new Game();
//		PieceData[][] board = new PieceData[8][8];
//		PositionData piecePosition = new PositionData(1, 4);
//		board[piecePosition.x][piecePosition.y] = new Pawn(PieceColor.w, piecePosition.x, piecePosition.y);
//		board[piecePosition.x + 1][piecePosition.y + 2] = new Pawn(PieceColor.b, piecePosition.x + 1, piecePosition.y + 2);
//		g.updateBoard(board);
//		
//		AvailableMoves actualMoves =  g.getAvailableMoves(piecePosition);
//		
//		AvailableMoves expectedMoves = new AvailableMoves(
//				new ArrayList<>(Arrays.asList(new PositionData[] { new PositionData(4, 3), new PositionData(4, 2), new PositionData(3, 3), new PositionData(5, 3) })));
//		assertTrue(expectedMoves.equals(actualMoves));
//	}
	
	@Test
	public void testPawnAvailableMoves() {
		Game g = new Game();
		PieceData[][] board = new PieceData[8][8];
		PositionData piecePosition = new PositionData(4, 4);
		board[piecePosition.x][piecePosition.y] = new Pawn(PieceColor.w, piecePosition.x, piecePosition.y);
		board[piecePosition.x - 1][piecePosition.y - 1] = new Pawn(PieceColor.b, piecePosition.x - 1, piecePosition.y - 1);
		board[piecePosition.x + 1][piecePosition.y - 1] = new Pawn(PieceColor.b, piecePosition.x + 1, piecePosition.y - 1);
		g.updateBoard(new Board(board));
		
		AvailableMoves actualMoves =  g.getAvailableMoves(piecePosition);
		
		AvailableMoves expectedMoves = new AvailableMoves(
				new ArrayList<>(Arrays.asList(new PositionData[] { new PositionData(4, 3), new PositionData(4, 2), new PositionData(3, 3), new PositionData(5, 3) })));
		assertTrue(expectedMoves.equals(actualMoves));
	}


}
