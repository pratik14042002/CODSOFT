
import java.util.Scanner;
import java.util.Random;
public class NumberGame {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();

            int minNum = 1;
            int maxNum = 100;
            int maxAttempts = 10;
            int totalAttempts = 0;
            int roundsWon = 0;

            System.out.println("Welcome to the Number Guessing Game!");
            boolean playAgain = true;
            while (playAgain) {
                int targetNumber = random.nextInt(maxNum - minNum + 1) + minNum;
                int attempts = 0;
                boolean correctGuess = false;

                System.out.println("\nNew round. Guess a number between " + minNum + " and " + maxNum + ".");

                while (attempts < maxAttempts) {
                    System.out.print("Attempt " + (attempts + 1) + ": Enter your guess: ");
                    int userGuess = scanner.nextInt();
                    attempts++;

                    if (userGuess == targetNumber) {
                        correctGuess = true;
                        break;
                    } else if (userGuess < targetNumber) {
                        System.out.println("Too low. Try again.");
                    } else {
                        System.out.println("Too high. Try again.");
                    }
                }

                if (correctGuess) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                    roundsWon++;
                } else {
                    System.out.println("Out of attempts. The correct number was " + targetNumber + ".");
                }

                totalAttempts += attempts;

                System.out.print("Do you want to play again? (yes/no): ");
                String playAgainStr = scanner.next().toLowerCase();
                playAgain = playAgainStr.equals("yes");
            }

            System.out.println("\nGame over!");
            if (roundsWon > 0) {
                System.out.println("You played " + (roundsWon + 1) + " round(s) and won " + roundsWon + " round(s).");
                System.out.println("Your average attempts per round: " + (totalAttempts / (double) (roundsWon + 1)));
            } else {
                System.out.println("You played 1 round and couldn't guess any number.");
            }

            scanner.close();
        }
    }


