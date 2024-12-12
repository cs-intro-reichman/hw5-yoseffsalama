import java.util.Scanner;

public class Scrabble {

    static final String WORDS_FILE = "dictionary.txt";
    static final int[] SCRABBLE_LETTER_VALUES = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    static final int HAND_SIZE = 10;
    static final int MAX_NUMBER_OF_WORDS = 100000;
    static String[] DICTIONARY = new String[MAX_NUMBER_OF_WORDS];
    static int NUM_OF_WORDS;

    public static void init() {
        Scanner scanner = new Scanner(Scrabble.class.getResourceAsStream(WORDS_FILE));
        System.out.println("Loading word list from file...");
        NUM_OF_WORDS = 0;
        while (scanner.hasNext() && NUM_OF_WORDS < MAX_NUMBER_OF_WORDS) {
            DICTIONARY[NUM_OF_WORDS++] = scanner.next().toLowerCase();
        }
        System.out.println(NUM_OF_WORDS + " words loaded.");
    }

    public static boolean isWordInDictionary(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        for (int i = 0; i < NUM_OF_WORDS; i++) {
            if (word.equals(DICTIONARY[i])) {
                return true;
            }
        }
        return false;
    }

    public static int wordScore(String word) {
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            score += SCRABBLE_LETTER_VALUES[index];
        }
        score *= word.length();
        if (word.length() == HAND_SIZE) {
            score += 50;
        }
        if (MyString.subsetOf("runi", word)) {
            score += 1000;
        }
        return score;
    }

    public static String createHand() {
        String hand = MyString.randomStringOfLetters(HAND_SIZE - 2);
        hand = MyString.insertRandomly('a', hand);
        hand = MyString.insertRandomly('e', hand);
        return hand;
    }

    public static void playHand(String hand) {
        int score = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Current Hand: " + MyString.spacedString(hand));
            System.out.println("Enter a word, or '.' to finish playing this hand:");
            String input = scanner.nextLine().trim();

            if (".".equals(input)) {
                System.out.println("End of hand. Total score: " + score + " points.");
                break;
            }

            if (MyString.subsetOf(input, hand)) {
                if (isWordInDictionary(input)) {
                    hand = MyString.remove(hand, input);
                    int points = wordScore(input);
                    score += points;
                    System.out.println(input + " earned " + points + " points. Total score: " + score + " points.");
                } else {
                    System.out.println("No such word in the dictionary. Try again.");
                }
            } else {
                System.out.println("Invalid word. Try again.");
            }

            if (hand.isEmpty()) {
                System.out.println("Ran out of letters. Total score: " + score + " points.");
                break;
            }
        }
    }

    public static void playGame() {
        init();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 'n' to deal a new hand, or 'e' to end the game:");
            String input = scanner.nextLine().trim();

            if ("n".equals(input)) {
                playHand(createHand());
            } else if ("e".equals(input)) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        playGame();
    }
}
