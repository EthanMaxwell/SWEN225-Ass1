package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import GameCode.Game;
import GameCode.Location;
import GameCode.Room;
import GameCode.Square;

public class GameTests {
	/**
	 * Test a basic game loop of the players moving to square
	 */
	@Test
	public void test_01() {
		Game game = new Game(new noDisplay());

		// Game should require player number to be set
		assertEquals(Game.GameState.SelectPlayerNumber, game.getGameState());
		assertTrue(game.dealHands(Set.copyOf(game.getCharacters())));

		// Player through 100 player turns
		for (int i = 0; i < 100; i++) {
			// First player must roll dice
			assertEquals(Game.GameState.RollDice, game.getGameState());
			assertTrue(game.rollDice());
			assertTrue(game.diceRoll() >= 2);
			assertTrue(game.diceRoll() <= 12);

			// Then player must move
			assertEquals(Game.GameState.MovePlayer, game.getGameState());
			// Select any square to move to
			for (Location l : game.canMoveTo()) {
				if (l instanceof Square) {
					assertTrue(game.MovePlayer(l));
					break;
				}
			}
		}
	}

	/**
	 * Test the player is able to actually move
	 */
	@Test
	public void test_02() {
		Game game = new Game(new noDisplay());
		assertTrue(game.dealHands(Set.copyOf(game.getCharacters())));

		// Record current player locations
		Map<GameCode.Character, Location> positions = game.getCharacters().stream()
				.collect(Collectors.toMap(i -> i, i -> i.getLocation()));

		// Take 100 turns
		for (int i = 0; i < 100; i++) {
			assertTrue(game.rollDice());
			for (Location l : game.canMoveTo()) {
				if (l instanceof Square) {
					// Check the player is in the correct spot
					assertEquals(game.takingTurn().getLocation(), positions.get(game.takingTurn()));
					// Update their location
					positions.put(game.takingTurn(), l);
					assertTrue(game.MovePlayer(l));
					break;
				}
			}
		}
	}

	/**
	 * Test the player is able to move into a room
	 */
	@Test
	public void test_03() {
		Game game = new Game(new noDisplay());
		assertTrue(game.dealHands(Set.copyOf(game.getCharacters())));

		// Try to move a player into a room 1000 times.
		for (int i = 0; i < 1000; i++) {
			assertTrue(game.rollDice());

			// Try to move the player to a room
			for (Location l : game.canMoveTo()) {
				if (l instanceof Room) {
					assertTrue(game.MovePlayer(l));
					break;
				}
			}
			
			// If the player was moved into a room check it worked correctly
			if (game.getGameState().equals(Game.GameState.AskGuessOrSolve)) {
				GameCode.Character inRoom = game.takingTurn();
				// Check the player in now in the room
				assertTrue(inRoom.isInRoom());
				assertTrue(inRoom.getLocation().hasPartOf());
				assertEquals(inRoom.getLocation().getPartOf(), inRoom.getInRoom());
				assertTrue(game.askGuessOrSolve(true));
				// Test complete
				return;
			}

			// If player was unable to move to a room, pick a random square
			for (Location l : game.canMoveTo()) {
				if (l instanceof Square) {
					assertTrue(game.MovePlayer(l));
					break;
				}
			}
		}

		fail("No player entered a room");
	}
}

/**
 * An observer for testing that doesn't produce a GUI
 */
class noDisplay implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		// Do nothing
	}
}