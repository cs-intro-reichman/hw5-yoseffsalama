import java.util.*;
import java.io.*;

public class Scrabble {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Map<Character, Integer> LETTER_SCORES = new HashMap<>();
    private static final String DICTIONARY_FILE = "dictionary.txt";
    private static final int HAND_SIZE = 10;

    static {
        // Initialize letter scores
        int[] scores = {
            1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
            1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
        };
        for (int i = 0; i < ALPHABET.length(); i++) {
            LETTER_SCORES.put(ALPHABET.charAt(i), scores[i]);
        }
    }

    private List<String> dictionary;

    public Scrabble() {
        this.dictionary = loadDictionary();
    }

    private List<String> loadDictionary() {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
        }
        return words;
    }

    private String createHand() {
        StringBuilder hand = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < HAND_SIZE - 2; i++) {
            hand.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        hand.append('a');
        hand.append('e');
        return shuffleString(hand.toString());
    }

    private String shuffleString(String str) {
        List<Character> characters = new ArrayList<>();
        for (char c : str.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }

    private int wordScore(String word, String hand, boolean firstWord) {
        if (!isSubset(word, hand) || !dictionary.contains(word)) {
            return 0;
        }

        int score = 0;
        for (char c : word.toCharArray()) {
            score += LETTER_SCORES.get(c);
        }

        score *= word.length();

        if (firstWord && word.length() == HAND_SIZE) {
            score += 50;
        }

        if (word.contains("r") && word.contains("u") && word.contains("n") && word.contains("i")) {
            score += 1000;
        }

        return score;
    }

    private boolean isSubset(String word, String hand) {
        StringBuilder handBuilder = new StringBuilder(hand);
        for (char c : word.toCharArray()) {
            int index = handBuilder.indexOf(String.valueOf(c));
            if (index == -1) {
                return false;
            }
            handBuilder.deleteCharAt(index);
        }
        return true;
    }

    public void playHand(String hand) {
        Scanner scanner = new Scanner(System.in);
        int totalScore = 0;
        boolean firstWord = true;

        while (!hand.isEmpty()) {
            System.out.println("Current Hand: " + formatHand(hand));
            System.out.print("Enter a word, or '.' to finish playing this hand: ");
            String word = scanner.nextLine().toLowerCase();

            if (word.equals(".")) {
                break;
            }

            int score = wordScore(word, hand, firstWord);
            if (score > 0) {
                totalScore += score;
                System.out.println(word + " earned " + score + " points. Score: " + totalScore + " points");
                hand = remove(word, hand);
                firstWord = false;
            } else {
                System.out.println("Invalid word. Try again.");
            }
        }

        System.out.println("End of hand. Total score: " + totalScore + " points");
    }

    private String formatHand(String hand) {
        return hand.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    private String remove(String word, String hand) {
        StringBuilder handBuilder = new StringBuilder(hand);
        for (char c : word.toCharArray()) {
            int index = handBuilder.indexOf(String.valueOf(c));
            if (index != -1) {
                handBuilder.deleteCharAt(index);
            }
        }
        return handBuilder.toString();
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter n to deal a new hand, or e to end the game: ");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("e")) {
                break;
            } else if (command.equals("n")) {
                String hand = createHand();
                playHand(hand);
            } else {
                System.out.println("Invalid command.");
            }
        }
    }

    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.playGame();
    }
}
