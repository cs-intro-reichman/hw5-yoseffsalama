import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Scrabble {

    static final String WORDS_FILE = "dictionary.txt";
    static final int[] SCRABBLE_LETTER_VALUES = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    static final int HAND_SIZE = 10;
    static Set<String> DICTIONARY = new HashSet<>();

    public static void init() {
        Scanner scanner = new Scanner(Scrabble.class.getResourceAsStream(WORDS_FILE));
        if (scanner == null) {
            throw new IllegalStateException("Dictionary file not found: " + WORDS_FILE);
        }
        System.out.println("Loading word list from file...");
        while (scanner.hasNext()) {
            DICTIONARY.add(scanner.next().toLowerCase());
        }
        System.out.println(DICTIONARY.size() + " words loaded.");
    }

    public static boolean isWordInDictionary(String word) {
        return word != null && !word.isEmpty() && DICTIONARY.contains(word.toLowerCase());
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

    public static void main(String[] args) {
        playGame();
    }
}
