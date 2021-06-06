package com.skilldistillery.common;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards = new ArrayList<>();
	private String name;

	public Hand(String name) {
		this.name = name;
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public int getHandValue() {
		int value = 0;
		for (Card card : cards) {
			value += card.getValue();
		}
		return value;
	}

	public int getDealersHandValue() {
		int value = 0;
		for (int i = 1; i < cards.size(); i++) {
			Card card = cards.get(i);
			value += card.getValue();
		}
		return value;
	}

	public void displayDealersHand() {
		for (int i = 1; i < cards.size(); i++) {
			Card card = cards.get(i);
			System.out.println(card);
		}
	}

	public void fold() {
		cards.clear();
	}

	public void displayHand() {
		if (cards.size() == 0) {
			System.out.println(" --------------------------------- ");
			System.out.println("  No cards.");
			System.out.println(" --------------------------------- ");

		} else {
			for (Card card : cards) {
				System.out.println(card);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		return builder.toString();
	}
}