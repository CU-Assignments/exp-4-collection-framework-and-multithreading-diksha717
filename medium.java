package com.capgemini;

import java.util.*;

class Card {
    private String symbol;
    private String value;

    public Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + symbol;
    }
}

public class CardCollection {
    private Map<String, List<Card>> cardMap;

    public CardCollection() {
        cardMap = new HashMap<>();
    }

    // Add a new card
    public void addCard(String symbol, String value) {
        cardMap.putIfAbsent(symbol, new ArrayList<>());
        cardMap.get(symbol).add(new Card(symbol, value));
    }

    // Display all cards
    public void displayCards() {
        if (cardMap.isEmpty()) {
            System.out.println("No cards in the collection.");
            return;
        }
        for (Map.Entry<String, List<Card>> entry : cardMap.entrySet()) {
            for (Card card : entry.getValue()) {
                System.out.println(card);
            }
        }
    }

    // Search for cards by symbol
    public void searchBySymbol(String symbol) {
        List<Card> cards = cardMap.get(symbol);
        if (cards == null || cards.isEmpty()) {
            System.out.println("No cards found for symbol: " + symbol);
        } else {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollection collection = new CardCollection();

        // Adding sample cards
        collection.addCard("Hearts", "Ace");
        collection.addCard("Spades", "King");
        collection.addCard("Diamonds", "Queen");
        collection.addCard("Hearts", "10");

        while (true) {
            System.out.println("\n1. Add Card\n2. Display Cards\n3. Search by Symbol\n4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter card symbol (Hearts, Spades, Diamonds, Clubs): ");
                    String symbol = scanner.nextLine();
                    System.out.print("Enter card value (Ace, 2, 3,..., King): ");
                    String value = scanner.nextLine();
                    collection.addCard(symbol, value);
                    System.out.println("Card added!");
                    break;
                case 2:
                    System.out.println("\nAll Cards:");
                    collection.displayCards();
                    break;
                case 3:
                    System.out.print("Enter symbol to search: ");
                    String searchSymbol = scanner.nextLine();
                    collection.searchBySymbol(searchSymbol);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
