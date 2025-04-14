package orderandchaos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import orderandchaos.core.*;
import orderandchaos.ui.gui.*;

import java.io.IOException;

public class GUIMain extends OrderAndChaos<InputHandlerGUI, OutputHandlerGUI> {
    private static final int BOARD_SIZE = 6;
    private static JFrame frame;
    private static JButton[][] buttons;
    private static JLabel playerLabel;
    private OutputHandlerGUI outputHandler;

    public GUIMain(InputHandlerGUI inputHandler, OutputHandlerGUI outputHandler) {
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
                        String message = board.isFiveInLineFound() ? "Player "+ playerOrder.getName() + " wins!" : "Player "+ playerChaos.getName()+" wins!";
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
        if (inputHandler.askToSwitchRoles()) {
            Player temp = playerOrder;
            playerOrder = new Player(Role.ORDER, playerChaos.getName());
            playerChaos = new Player(Role.CHAOS, temp.getName());
        }
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
        outputHandler.gameWelcome();

        String nameOrder = null;
        String nameChaos = null;
        
        try {
            nameOrder = inputHandler.askForPlayerName(Role.ORDER);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        do {
            try {
                nameChaos = inputHandler.askForPlayerName(Role.CHAOS);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (nameChaos.equalsIgnoreCase(nameOrder)) {
                JOptionPane.showMessageDialog(null,
                        "Name already taken by ORDER player. Please choose another name.",
                        "Name Conflict",
                        JOptionPane.WARNING_MESSAGE);
            }
        } while (nameChaos.equalsIgnoreCase(nameOrder));

        playerOrder = new Player(Role.ORDER, nameOrder);
        playerChaos = new Player(Role.CHAOS, nameChaos);
        currentPlayer = playerOrder;

        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Order & Chaos Game");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent auto-close
    
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirmExit()) {
                    exitGame();
                }
            }
        });
    
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

        outputHandler.setButtons(buttons);
        outputHandler.setPlayerLabel(playerLabel);
    }

    private boolean confirmExit() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to quit the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
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
        InputHandlerGUI inputHandler = new InputHandlerGUI(frame);
        OutputHandlerGUI outputHandler = new OutputHandlerGUI(frame, null, null); // Initialize with null, will be set later
        GUIMain guiMain = new GUIMain(inputHandler, outputHandler);
        guiMain.initializeGame();
        guiMain.startGame();
    }
}