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
    public String askForSymbol() throws IOException {
        System.out.print("Do you place X or O? ");
        String choice = reader.readLine();
        while (!choice.equalsIgnoreCase("X") && !choice.equalsIgnoreCase("O")) {
            System.out.print("Invalid choice. Do you place X or O? ");
            choice = reader.readLine();
        }
        return choice;
    }

    @Override
    public Position getPlayerMove(Player currentPlayer) throws IOException {
        System.out.print(currentPlayer.getName() + ", enter your move (row column): ");
        String input = reader.readLine();
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            throw new IOException("Invalid input format. Please enter row and column separated by a space.");
        }
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        return new Position(row, col);
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
}