package orderandchaos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import orderandchaos.core.InputHandler;
import orderandchaos.core.Position;
import orderandchaos.core.Player;
import orderandchaos.core.Role;
import orderandchaos.core.Type;

public class InputHandlerConsole implements InputHandler {
    private BufferedReader reader;

    public InputHandlerConsole() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Position getPlayerMove(Player currentPlayer) throws IOException {
        Position position = null;
        while (position == null) {
            System.out.print(currentPlayer.getName() + ", enter your move (row column): ");
            String inputLine = reader.readLine();

            if (inputLine == null) {
                // Exit gracefully if input stream is closed
                System.out.println("Input stream closed unexpectedly. Exiting.");
                System.exit(0);
            }

            String[] parts = inputLine.split(" ");

            if (parts.length != 2) {
                System.out.println(
                        "Please provide exactly two values separated by space: row and column.");
                continue;
            }

            int row, col;

            try {
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Row and column must be integers. Try again.");
                continue;
            }

            row -= 1;
            col -=1;

            // Validate coordinates within 0-5
            if (row < 0 || row >= 6 || col < 0 || col >= 6) {
                System.out.println("Row and column must be between 1 and 6. Try again.");
                position = null;
                continue;
            }

            position = new Position(row, col);

        }
        return position;
    }
    
    public String askForPlayerName(Role role) throws IOException {
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter name for " + role + " player: ");
            name = reader.readLine();
        }
        return name;
    }

    public Type getMarkType(Player currentPlayer) throws IOException {
        Type markType = null;
        while (markType == null) {
            System.out.print(currentPlayer.getName() + ", enter your mark (X or O): ");
            String inputLine = reader.readLine();

            if (inputLine == null) {
                // Exit gracefully if input stream is closed
                System.out.println("Input stream closed unexpectedly. Exiting.");
                System.exit(0);
            }

            String markInput = inputLine.toUpperCase();

            switch (markInput) {
                case "X":
                    markType = Type.X;
                    break;
                case "O":
                    markType = Type.O;
                    break;
                default:
                    System.out.println("Invalid mark type. Please enter X or O.");
            }
        }
        return markType;
    }

    public boolean askToSwitchRoles() {
        while (true) {
            System.out.print("Do you want to switch roles between ORDER and CHAOS? (yes/no): ");
            try {
                String input = reader.readLine();
                if (input.equalsIgnoreCase("yes")) {
                    return true;
                } else if (input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }
}