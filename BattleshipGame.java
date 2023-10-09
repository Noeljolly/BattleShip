import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        char[][] playerGrid = new char[10][10];
        char[][] computerGrid = new char[10][10];
        int playerShips = 5;
        int computerShips = 5;

        initializeGrid(playerGrid);
        initializeGrid(computerGrid);

        System.out.println("Welcome to Battleship!");
        System.out.println("You have 5 ships to place. Enter coordinates as letter and number (e.g., A1, B2).");
        printGrid(playerGrid);
        placeShips(playerGrid, "Player");
        placeShips(computerGrid);

        playGame(playerGrid, computerGrid, playerShips, computerShips);

    }

    // Initialize the grid with empty spaces
    public static void initializeGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    // Print the grid with row and column labels
public static void printGrid(char[][] grid) {
    System.out.print("  ");
    for (int i = 0; i < 10; i++) {
        System.out.print((i + 1) + " ");
    }
    System.out.println();

    for (int i = 0; i < grid.length; i++) {
        System.out.print((char)('A' + i) + " ");
        for (int j = 0; j < grid[i].length; j++) {
            System.out.print(grid[i][j] + " ");
        }
        System.out.println();
    }
}
// Print the computer's grid while hiding the ship coordinates
public static void printComputerGrid(char[][] grid) {
    System.out.print("  ");
    for (int i = 0; i < 10; i++) {
        System.out.print((i + 1) + " ");
    }
    System.out.println();

    for (int i = 0; i < grid.length; i++) {
        System.out.print((char)('A' + i) + " ");
        for (int j = 0; j < grid[i].length; j++) {
            char cell = grid[i][j];
            if (cell == 'S') {
                System.out.print(" " + " "); // Hide ship coordinates
            }
            else {
                System.out.print(cell + " ");
            }
        }
        System.out.println();
    }
}


    // Place ships on the grid
    public static void placeShips(char[][] grid, String player) {
        Scanner scanner = new Scanner(System.in);

        for (int ship = 1; ship <= 5; ship++) {
            System.out.println(player + ", place ship " + ship + ":");
            while (true) {
                System.out.print("Enter coordinates (e.g., A1): ");
                String coordinates = scanner.nextLine().toUpperCase();
                if (isValidCoordinates(coordinates)) {
                    int row = coordinates.charAt(0) - 'A';
                    int col = Integer.parseInt(coordinates.substring(1)) - 1;
                    if (grid[row][col] == ' ') {
                        grid[row][col] = 'S'; // Mark ship location
                        break;
                    } else {
                        System.out.println("There is already a ship at this location. Try again.");
                    }
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                }
            }
        }
    }

        // Place ships on the grid for the computer
    public static void placeShips(char[][] grid) {
        Random random = new Random();

        for (int ship = 1; ship <= 5; ship++) {
            while (true) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);

                if (grid[row][col] == ' ') {
                    grid[row][col] = 'S'; // Mark ship location
                    break;
                }
            }
        }
    }

    // Check if coordinates are valid
    public static boolean isValidCoordinates(String coordinates) {
        if (coordinates.length() < 2)
            return false;

        char rowChar = coordinates.charAt(0);
        String colStr = coordinates.substring(1);

        if (rowChar < 'A' || rowChar > 'J')
            return false;

        try {
            int col = Integer.parseInt(colStr) - 1;
            if (col < 0 || col >= 10)
                return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    // Play the game
    public static void playGame(char[][] playerGrid, char[][] computerGrid, int playerShips, int computerShips) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        for (int turn = 1; turn <= 10; turn++) {
            System.out.println("Turn " + turn);

            // Player's turn
            if (turn % 2 == 1) {
                System.out.print("Your guess: ");
                String guess = scanner.nextLine().toUpperCase();

                if (isValidCoordinates(guess)) {
                    int row = guess.charAt(0) - 'A';
                    int col = Integer.parseInt(guess.substring(1)) - 1;

                    if (computerGrid[row][col] == 'S') {
                        System.out.println("It's a HIT!");
                        computerGrid[row][col] = 'X';
                        computerShips--;
                    } else if (computerGrid[row][col] == 'X' || computerGrid[row][col] == 'O') {
                        System.out.println("You've already guessed this location. Try again.");
                        turn--;
                    } else {
                        System.out.println("It's a MISS!");
                        computerGrid[row][col] = 'O';
                    }
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    turn--;
                }
                printComputerGrid(computerGrid);
            }
            // Computer's turn
            else {
                int row, col;
                do {
                    row = random.nextInt(10);
                    col = random.nextInt(10);
                } while (playerGrid[row][col] == 'X' || playerGrid[row][col] == 'O');

                if (playerGrid[row][col] == 'S') {
                    System.out.println("Computer's guess: " + (char) ('A' + row) + (col + 1));
                    System.out.println("It's a HIT!");
                    playerGrid[row][col] = 'X';
                    playerShips--;
                } else {
                    System.out.println("Computer's guess: " + (char) ('A' + row) + (col + 1));
                    System.out.println("It's a MISS!");
                    playerGrid[row][col] = 'O';
                }
                printGrid(playerGrid);
            }

            
            
            System.out.println();
        }

        int playerhits = 5 - computerShips;
        int computerhits = 5 - playerShips;

        if (playerShips < computerShips) {
            System.out.println("Computer wins!");
            System.out.println("Player hits: " + playerhits);
            System.out.println("Computer hits: " + computerhits);
        } 
        else if(playerShips > computerShips) {
            System.out.println("Player wins!");
            System.out.println("Player hits: " + playerhits);
            System.out.println("Computer hits: " + computerhits);
        }
        else {
            System.out.println("It's a draw!");
            System.out.println("Player hits: " + playerhits);
            System.out.println("Computer hits: " + computerhits);
        }

    }


}
