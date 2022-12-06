package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import backend.Game;
import common.*;
// TODO For all of the pieces, we need a test that asserts they can't move into checkmate
// TODO we also need code that prevents moving into checkmate
public class PieceTest {
	// TODO Create new test with friendly piece in front of pawn and no opposing pieces on board
	@Test
	public void testPawnAvailableMoves() {
		Game g = new Game(new Player("u", "pw"), new Player("u", "pw"));
		PieceData[][] board = new PieceData[8][8];
		PositionData piecePosition = new PositionData(4, 4);
		board[piecePosition.x][piecePosition.y] = new Pawn(PieceColor.w, piecePosition.x, piecePosition.y);
		board[piecePosition.x - 1][piecePosition.y - 1] = new Pawn(PieceColor.b, piecePosition.x - 1,
				piecePosition.y - 1);
		board[piecePosition.x + 1][piecePosition.y - 1] = new Pawn(PieceColor.b, piecePosition.x + 1,
				piecePosition.y - 1);
		g.updateBoard(new Board(board));

		AvailableMoves actualMoves = g.getAvailableMoves(piecePosition);

		AvailableMoves expectedMoves = new AvailableMoves(new ArrayList<>(Arrays.asList(new PositionData[] {
				new PositionData(4, 3), new PositionData(4, 2), new PositionData(3, 3), new PositionData(5, 3) })));
		assertTrue(expectedMoves.equals(actualMoves));
	}
	
	@Test
	public void testPawnGetFilePath() {
		Pawn pawn = new Pawn(PieceColor.w, 4, 4);
		assertEquals(pawn.getFilePath(), "wPawn.png");
	}
}
