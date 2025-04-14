package orderandchaos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import orderandchaos.core.*;

import java.io.IOException;

public class GUIMain extends OrderAndChaos<InputHandlerUI, OutputHandlerUI> {
    private static final int BOARD_SIZE = 6;
    private static JFrame frame;
    private static JButton[][] buttons;
    private static JLabel playerLabel;
    private OutputHandlerUI outputHandler;

    public GUIMain(InputHandlerUI inputHandler, OutputHandlerUI outputHandler) {
        super(inputHandler, outputHandler);
        this.outputHandler = outputHandler;
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listener
    private class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Position position = inputHandler.getButtonPosition(e);

                if (!board.isOccupied(position)) {
                    Type markType = inputHandler.getMarkType(currentPlayer);

                    if (markType == null) {
                        return;
                    }

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
                    outputHandler.updatePlayerLabel(currentPlayer);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                // Handle the exception as needed
            }
        }
    }

    private void restartGame() {
        board.clearBoard();
        isGameOver = false;
        outputHandler.resetBoardDisplay();
        currentPlayer = playerOrder;
        outputHandler.updatePlayerLabel(currentPlayer);
    }

    public void exitGame() {
        frame.dispose();
        System.exit(0);
    }

    public void startGame() {
        setMoveListener(new MoveListener());
    }

    protected void preInitializeGame() {
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

        // Initialize OutputHandlerUI after buttons and playerLabel are set up
        outputHandler.setButtons(buttons);
        outputHandler.setPlayerLabel(playerLabel);
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
    protected Position getPlayerMove() throws IOException {
        throw new UnsupportedOperationException("This method should not be called directly");
    }

    public static void main(String[] args) {
        InputHandlerUI inputHandler = new InputHandlerUI(frame);
        OutputHandlerUI outputHandler = new OutputHandlerUI(frame, null, null); // Initialize with null, will be set later
        GUIMain guiMain = new GUIMain(inputHandler, outputHandler);
        guiMain.initializeGame();
        guiMain.startGame();
    }
}