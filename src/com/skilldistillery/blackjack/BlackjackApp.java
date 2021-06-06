package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.common.Deck;
import com.skilldistillery.common.Hand;

public class BlackjackApp {

	Hand dealer = new Hand("Dealer");
	Hand player = new Hand("Player");
	int turn = 0;
	boolean gameOver = false;
	String input = "h";

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		BlackjackApp app = new BlackjackApp();
		app.run(kb);
	}

	private void run(Scanner kb) {
		Deck deck = new Deck();
		List<Hand> players = new ArrayList<>();
		players.add(dealer);
		players.add(player);
		turn = 0;
		player.fold();
		dealer.fold();
		lines();
		System.out.println("       Let's Play Blackjack        ");
		deck.shuffle();
		dealInitialHands(deck);
		displayTable();
		gameLogic(players, deck, kb);
		kb.close();
	}

	public void displayTable() {
		lines();
		if (gameOver) {
			System.out.println("  Dealer's hand: " + dealer.getHandValue());
			dealer.displayHand();
		} else {
			System.out.println("  Dealer's hand: " + dealer.getDealersHandValue());
			System.out.println("  Hidden Card                      ");
			dealer.displayDealersHand();
		}

		lines();
		System.out.println("  Your hand: " + player.getHandValue());
		player.displayHand();
		lines();
	}

	public void dealInitialHands(Deck deck) {
		dealer.addCard(deck.dealCard());
		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());
		player.addCard(deck.dealCard());
	}

	public void displayInvalidInput(List<Hand> players, Deck deck, Scanner kb) {
		lines();
		System.out.println("           Invalid Input           ");
		gameLogic(players, deck, kb);

	}

	public void gameOver(List<Hand> players, Deck deck, Scanner kb) {
		gameOver = true;
		displayTable();
		gameLogic(players, deck, kb);
	}

	public void lines() {
		System.out.println(" --------------------------------- ");

	}

	public void gameLogic(List<Hand> players, Deck deck, Scanner kb) {

		if (gameOver == false) {
			for (Hand hand : players) {
				if ((player.getHandValue() == 21) && (turn == 0)) {
					System.out.println("  Blackjack! You win!");
					gameOver(players, deck, kb);

				} else if (player.getHandValue() == (dealer.getHandValue()) && (turn > 0)
						&& (dealer.getHandValue() >= 17)) {
					System.out.println("  Push!");
					gameOver(players, deck, kb);

				} else if ((hand.getHandValue() > 21) && (turn > 0)) {
					System.out.println("  " + hand + " is Bust!");
					gameOver(players, deck, kb);

				} else if ((hand.getHandValue() == 21) && (turn > 0)) {
					System.out.println("  " + hand + " Wins!");
					gameOver(players, deck, kb);

				} else if ((player.getHandValue() > dealer.getHandValue()) && (dealer.getHandValue() >= 17)
						&& (input.equalsIgnoreCase("s"))) {
					System.out.println("  You win with " + player.getHandValue() + " points: ");
					gameOver(players, deck, kb);

				} else if ((dealer.getHandValue() > player.getHandValue()) && (dealer.getHandValue() >= 17)
						&& (input.equalsIgnoreCase("s"))) {
					System.out.println("  Dealer wins with " + dealer.getHandValue() + " points: ");
					gameOver(players, deck, kb);

				} else if ((dealer.getHandValue() < 17) && (input.equalsIgnoreCase("s"))) {
					dealer.addCard(deck.dealCard());
					gameLogic(players, deck, kb);

				} else if ((player.getHandValue() < 21) && (dealer.getHandValue() < 21)
						&& (deck.checkDeckSize() < 10)) {
					run(kb);

				} else if ((player.getHandValue() < 21) && (dealer.getHandValue() < 21)
						&& (deck.checkDeckSize() > 10)) {
					System.out.println("         [S]tand or [H]it          ");
					lines();
					System.out.print("   > ");
					input = kb.next();
					if (input.equalsIgnoreCase("s")) {
						turn++;
						gameLogic(players, deck, kb);

					} else if (input.equalsIgnoreCase("h")) {
						turn++;
						player.addCard(deck.dealCard());
						displayTable();
						gameLogic(players, deck, kb);

					} else {
						displayInvalidInput(players, deck, kb);

					}
				}
			}
		}

		else if (gameOver) {
			System.out.println("     [P]lay Again or [Q]uit?       ");
			lines();
			System.out.print("   > ");
			input = kb.next();

			if (input.equalsIgnoreCase("p")) {
				gameOver = false;
				input = "h";
				run(kb);

			} else if (input.equalsIgnoreCase("q")) {
				lines();
				System.out.println("             Goodbye!              ");
				lines();
				System.exit(0);

			} else {
				displayInvalidInput(players, deck, kb);
			}
		}
	}
}
