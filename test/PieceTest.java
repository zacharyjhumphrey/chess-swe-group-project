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

	// TODO add friendly piece to position where knight would move
	@Test
	public void testKnightAvailableMoves() {
		Game g = new Game(new Player("u", "pw"), new Player("u", "pw"));
		PieceData[][] board = new PieceData[8][8];
		PositionData pos = new PositionData(1, 3);
		board[pos.x][pos.y] = new Knight(PieceColor.w, pos.x, pos.y);
		board[pos.x + 1][pos.y + 2] = new Pawn(PieceColor.b, pos.x + 1, pos.y - 1);
		g.updateBoard(new Board(board));

		AvailableMoves actualMoves = g.getAvailableMoves(pos);

		AvailableMoves expectedMoves = new AvailableMoves(new ArrayList<>(Arrays.asList(
				new PositionData[] { pos.getRelativePos(1, -2), pos.getRelativePos(2, -1), pos.getRelativePos(2, 1),
						pos.getRelativePos(1, 2), pos.getRelativePos(-1, 2), pos.getRelativePos(-1, -2), })));
		assertTrue(expectedMoves.equals(actualMoves));
	}

}
