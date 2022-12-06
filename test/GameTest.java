package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import backend.*;
import common.*;

public class GameTest {
	
	@Test 
	public void testAvailableMovesEqual() {
		AvailableMoves actualMoves = new AvailableMoves(
				new ArrayList<>(Arrays.asList(new PositionData[] { new PositionData(1, 0), new PositionData(2, 0) })));
		
		AvailableMoves expectedMoves = new AvailableMoves(
				new ArrayList<>(Arrays.asList(new PositionData[] { new PositionData(2, 0), new PositionData(1, 0) })));
		assertEquals(expectedMoves, actualMoves);
	}


}
