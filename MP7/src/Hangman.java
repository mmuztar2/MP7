import java.util.Scanner;
import java.util.ArrayList;

public class Hangman {
	
	/**List of possible words that you may need to guess for hangman*/
	public static String[] possibleWords = {"computer science", "machine", "processor", "java", 
			"python", "byte", "bit", "pixel", "computer", "lollipop", "marshmallow", "word", 
			"cupcake", "cookie", "honeycomb", "ice cream", "ice cream sandwich", "nougat",
			"gingerbread", "candy cane", "jelly bean", "kitkat", "program", "data",
			"memory", "operating system", "algorithm", "compile", "debug", "hardware", "computer",
			"apple", "carrot", "peach", "orange", "tangerine", "football", "basketball",
			"banana", "mango", "watermelon", "broccoli", "cauliflower", "spinach", "tomato",
			"lettuce", "cabbage", "fish", "chicken", "persimmon", "food", "kiwi", "pineapple",
			"baseball", "hockey", "kickball", "method", "variable", "final", "a penny for your thoughts",
			"blessing in disguise", "last straw", "piece of cake", "great minds think alike",
			"your guess is as good as mine", "an eye for an eye", "a needle in a haystack",
			"two heads are better than one"};
	/**A word that is picked out from possibleWords and that the player has to guess*/
	public static final String wordToGuess = wordPicker(possibleWords).toUpperCase();
	/**playerInput scanner*/
	public static final Scanner playerInput = new Scanner(System.in);
	
	public static void main(String args[]) {
		/**if the player wins, playerWins is set to true*/
		boolean playerWins = false;
		/**the number of tries the player is allowed to guess the word*/
		int tries = 6;
		/**the dash representation of the wordToGuess*/
		String wordDashes = createDashes(wordToGuess);
		
		System.out.println(wordDashes);
		
		System.out.println("You have 6 tries to completely guess the word. Guess a letter or type out the word:");
		/**the string in which the player's input will be put into*/
		String guess = "";
		/**list of the past guesses that the player made*/
		ArrayList<String> pastGuesses = new ArrayList<String>();
		
		//while the player still has lives available,
		//the game will continue to run
		while (tries != 0) {
			
			//print the past guesses for the player to see
			printGuessesUsed(pastGuesses);	
			guess = playerInput.nextLine();
			guess = guess.toUpperCase();
			
			//Checks to see if the player used the letter/phrase previously
			if (!guessUsed(guess, pastGuesses)) {
				pastGuesses.add(guess);
				//check to see if the player typed in a letter or the whole word
				if (guess.length() > 1) {
					//check to see if the word/phrase the player typed in
					//matches the length of the word the player has to guess
					if (guess.length() == wordToGuess.length()) {
						//if the guess matches the word that needed to be guessed,
						//the player wins the game
						if (guess.equals(wordToGuess)) {
							System.out.println("\nYou guessed the right word: " + guess + "\nCongratulations! You win!");
							tries = 0;
							playerWins = true;
						//if the player guessed incorrectly, lives will be deducted
						} else {
							tries--;
							System.out.println("\nSorry, you guessed incorrectly. You have " + tries + " tries left.");
							//if the player still has lives available, they can guess again
							if (tries != 0) {
								System.out.print(" Please guess again: ");
								System.out.println(wordDashes);
							}
						}
					//if the player doesn't match the length of the word
					//needed to be guessed, the player doesn't lose any lives
					} else if (guess.length() > wordToGuess.length()) {
						System.out.println("\nYour guess was longer than the actual word. Please try again:");
						System.out.println(wordDashes);
					} else if (guess.length() < wordToGuess.length()) {
						System.out.println("\nYour guess was shorter than the actual word. Please try again:");
						System.out.println(wordDashes);
					}
				//if the player typed in a letter instead of a word/phrase
				} else {
					/**if the letter was found in the word that needed to be guessed*/
					boolean characterFound = findCharacter(wordToGuess, guess);
					//if the letter is part of the word that needs to be guessed
					if (characterFound) {
						wordDashes = replaceDashes(wordToGuess, guess, wordDashes);
						System.out.println("\nYour guess was correct! You have " + tries + " tries left.");
						//checks to see if all blank spaces have been filled with a letter
						//if so, the game is won
						if (dashesFilled(wordDashes)) {
							System.out.println("You were able to complete the word: " + wordToGuess + "\nCongratulations! You win!");
							tries = 0;
							playerWins = true;
						//if the player hasn't completed the word
						} else {
							System.out.println("Please continue to guess or type out the word: ");
							System.out.println(wordDashes);
						}
					//if the letter is not part of the word that has to be guessed
					} else {
						tries--;
						System.out.println("\nThe letter you guessed was not found in the word. You have " + tries + " tries left.");
						//if the character still has lives available, they can guess again
						if (tries != 0) {
							System.out.print("Please guess again: \n");
							System.out.println(wordDashes);
						}
					}
				}
			/*Print statement for if the player typed in a guess
			 * previously used
			 */
			} else {
				System.out.println("\nYou have already entered this word/letter before. Please enter a new guess: ");
				System.out.println(wordDashes);
			}	
		}
		//if the game stops because the player has run out of lives,
		//the game will print show the word and print "Game Over"
		if (playerWins == false) {
			System.out.println("The word was: " + wordToGuess + "\nGame Over");
		}
	}
	
	/**
	 * Picks a word from a list for the player to guess
	 * @param words the list that contains words that need to be guessed
	 * @return the word that was picked from the list of possible words needed to be guessed
	 */
	public static String wordPicker(String[] words) {
		int index = (int)(Math.random() * words.length);
		return words[index];
	}
	
	/**
	 * Creates blank spaces for the word that needs to be guessed showing the player
	 * the number of letters in the word
	 * @param word the word that the player needs to guess
	 * @return String of blank spaces that represent letters in the word needed to be guessed
	 */
	public static String createDashes(String word) {
		String dashes = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != ' ' && word.charAt(i) != '-') {
				dashes += "_ ";
			} else if (word.charAt(i) == ' ') {
				dashes += "  ";
			} else if (word.charAt(i) == '-') {
				dashes += "- ";
			}
		}
		return dashes;
	}
	
	/**
	 * Checks to see if the player guessed what they previously guessed before
	 * @param newGuess the guess that the player currently guessed
	 * @param previousGuesses the list of past guesses the player made
	 * @return whether the player guessed what they guessed previously
	 */
	public static boolean guessUsed(String newGuess, ArrayList<String> previousGuesses) {
		for (int i = 0; i < previousGuesses.size() - 1; i++) {
			if (newGuess.equals(previousGuesses.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Prints the letters, words, or phrases the player guessed previously
	 * @param previousGuesses the list of past guesses the player made
	 */
	public static void printGuessesUsed(ArrayList<String> previousGuesses) {
		if (!previousGuesses.isEmpty()) {
			System.out.println("You have already used the following words/letters:");
		}
		for (int i = 0; i < previousGuesses.size(); i++) {
			System.out.print("'" + previousGuesses.get(i) + "'" + " ");
		}
	}
	
	/**
	 * Find the letter the player guessed within the word that needs to be guessed
	 * @param word the word that needs to be guessed correctly
	 * @param letter the letter the player guessed
	 * @return whether the letter the player guessed is contained within the word needed to be guessed
	 */
	public static boolean findCharacter(String word, String letter) {
		for (int i = 0; i < word.length(); i++) {
			if (letter.equals(word.substring(i, i + 1))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param word the word needed to be guessed
	 * @param letter the letter the player guessed
	 * @param dashes the underscore representation of the word needed to be guessed
	 * @return the underscore representation of the word replaced with the correct letter
	 */
	public static String replaceDashes(String word, String letter, String dashes) {
		char charLetter = letter.charAt(0);
		String newDash = dashes;
		char[] charDash = newDash.toCharArray();
		for (int i = 0; i < word.length(); i++) {
			if (letter.equals(word.substring(i, i + 1))) {
				charDash[i * 2] = charLetter;
			}
		}
		newDash = String.valueOf(charDash);
		return newDash;
	}
	
	/**
	 * 
	 * @param dashes underscore representation of the word needed to be guessed
	 * @return if the underscores in the underscore representation of the word are filled
	 */
	public static boolean dashesFilled(String dashes) {
		if (dashes.indexOf('_') == -1) {
			return true;
		}
		return false;
	}
}
