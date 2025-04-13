package orderandchaos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import orderandchaos.core.*;

import java.io.IOException;

public class GUIMain extends OrderAndChaos {
    private static final int BOARD_SIZE = 6;
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private InputHandler inputHandler = new InputHandler();
    private OutputHandler outputHandler = new OutputHandler();

    // Input Handler
    private class InputHandler {
        public String askForSymbol() {
            String[] options = {"X", "O"};
            return (String) JOptionPane.showInputDialog(frame,
                    "Do you place X or O?", "Move",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }

        public Position getButtonPosition(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            Integer row = (Integer) button.getClientProperty("row");
            Integer col = (Integer) button.getClientProperty("col");
            return new Position(row, col);
        }
    }

    // Output Handler
    private class OutputHandler {
        public void updateButton(int row, int col, String symbol) {
            if (buttons[row][col].getText().isEmpty()) {
                buttons[row][col].setText(symbol);
                buttons[row][col].setEnabled(false);
            }
        }

        public void showWinnerMessage(String message) {
            JOptionPane.showMessageDialog(frame, message);
        }

        public boolean askForNewGame() {
            int restart = JOptionPane.showConfirmDialog(frame, "Do you want to play a new game?", "NEW GAME",
                    JOptionPane.YES_NO_OPTION);
            return restart == JOptionPane.YES_OPTION;
        }

        public void updatePlayerLabel() {
            playerLabel.setText("Current turn: " + currentPlayer.getName());
        }

        public void resetBoardDisplay() {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    buttons[row][col].setText("");
                    buttons[row][col].setEnabled(true);
                }
            }
        }
    }

    // Listener
    private class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Position position = getPlayerMove(e);

            if (!board.isOccupied(position)) {
                Type markType = getMarkType();

                if (markType != null) {
                    addMove(position, markType);
                    outputHandler.updateButton(position.getRow(), position.getColumn(), markType.getName());

                    if (checkWinCondition()) {
                        String message = board.isFiveInLineFound() ? "Player ORDER wins!" : "Player CHAOS wins!";
                        outputHandler.showWinnerMessage(message);
                        if (outputHandler.askForNewGame()) restartGame();
                        else exitGame();
                        return;
                    }

                    currentPlayer = (currentPlayer.getRole() == Role.ORDER) ? playerChaos : playerOrder;
                    outputHandler.updatePlayerLabel();
                }
            }
        }
    }

    private void restartGame() {
        board.clearBoard();
        isGameOver = false;
        outputHandler.resetBoardDisplay();
        currentPlayer = playerOrder;
        outputHandler.updatePlayerLabel();
    }

    @Override
    public void exitGame() {
        frame.dispose();
        System.exit(0);
    }

    @Override
    public void startGame() {
        initializeGame();
    }

    @Override
    protected void initializeGame() {
        isGameOver = false;
        setLookAndFeel();
        playerOrder = initializePlayer(Role.ORDER);
        playerChaos = initializePlayer(Role.CHAOS);
        currentPlayer = playerOrder;
        setupGUI();
    }

    private Player initializePlayer(Role role) {
        String name = null;
        while (name == null || name.trim().isEmpty()) {
            name = JOptionPane.showInputDialog(null,
                    "Enter name for " + role + " player:",
                    "Player Name Input",
                    JOptionPane.QUESTION_MESSAGE);

            if (name == null || name.trim().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Do you want to quit the game?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            }
        }
        return new Player(role, name.trim());
    }

    private void setupGUI() {
        frame = new JFrame("Order & Chaos Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = createGridPanel();
        playerLabel = new JLabel("Current turn: " + currentPlayer.getName(), SwingConstants.CENTER);
        playerLabel.setFont(new Font("Ubuntu", Font.BOLD, 18));

        mainPanel.add(playerLabel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        setMoveListener(new MoveListener());
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = createGridButton(row, col);
                gridPanel.add(buttons[row][col]);
            }
        }
        return gridPanel;
    }

    private JButton createGridButton(int row, int col) {
        JButton button = new JButton("");
        button.setFont(new Font("Ubuntu", Font.BOLD, 40));
        button.putClientProperty("row", row);
        button.putClientProperty("col", col);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    private void setMoveListener(ActionListener listener) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].addActionListener(listener);
            }
        }
    }

    // Wrapper method to satisfy the superclass method signature
    @Override
    protected Position getPlayerMove() throws IOException {
        // This method is called from the superclass or other parts of the code
        // We need to provide a default implementation or throw an exception
        throw new UnsupportedOperationException("This method should not be called directly");
    }

    // Method that actually handles the ActionEvent
    protected Position getPlayerMove(ActionEvent e) {
        return inputHandler.getButtonPosition(e);
    }

    @Override
    protected Type getMarkType() {
        String choice = inputHandler.askForSymbol();

        if (choice == null) {
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Do you want to quit the game?", "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return getMarkType();
            }
        }

        return choice.equals("X") ? Type.X : Type.O;
    }

    public static void main(String[] args) {
        GUIMain guiMain = new GUIMain();
        guiMain.startGame();
    }
}