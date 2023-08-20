
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Wordcounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your texts:");
        String input = scanner.nextLine().trim();

        String text;
        if (input.endsWith(".txt")) {
            try {
                text = readFileToString(input);
            } catch (IOException e) {
                System.out.println("Error reading the file.");
                return;
            }
        } else {
            text = input;
        }

        String[] words = text.split("\\s+|\\p{Punct}+");
        int totalWords = words.length;
        int uniqueWords = countUniqueWords(words);
        Map<String, Integer> wordFrequency = getWordFrequency(words);

        System.out.println("Total words: " + totalWords);
        System.out.println("Unique words: " + uniqueWords);
        System.out.println("Word frequency: " + wordFrequency);
    }

    private static String readFileToString(String filePath) throws IOException {
        File file = new File(filePath);
        return new String(Files.readAllBytes(file.toPath()));
    }

    private static int countUniqueWords(String[] words) {
        Set<String> uniqueWordsSet = new HashSet<>(Arrays.asList(words));
        return uniqueWordsSet.size();
    }

    private static Map<String, Integer> getWordFrequency(String[] words) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }
}


