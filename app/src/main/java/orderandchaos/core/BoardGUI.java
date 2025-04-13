package orderandchaos.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import orderandchaos.exceptions.OutOfBoundsException;

public class BoardGUI {
    private static final int BOARD_SIZE = 6;
    
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;

    public BoardGUI(Board board) {
        setLookAndFeel();
        initializeFrame();
        initializeUIComponents();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeFrame() {
        frame = new JFrame("Order & Chaos Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    private void initializeUIComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = createGridPanel();
        playerLabel = createPlayerLabel();

        mainPanel.add(playerLabel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
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

    private JLabel createPlayerLabel() {
        JLabel label = new JLabel("Current turn: ", SwingConstants.CENTER);
        label.setFont(new Font("Ubuntu", Font.BOLD, 18));
        return label;
    }

    public void setCurrentPlayer(Player player) {
        playerLabel.setText("Current turn: " + player.getName());
    }

    public void setMoveListener(ActionListener listener) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].addActionListener(listener);
            }
        }
    }

    public void updateButton(int row, int col, String symbol) {
        if (buttons[row][col].getText().isEmpty()) { 
            buttons[row][col].setText(symbol);
            buttons[row][col].setEnabled(false);
        }
    }

    public String askForSymbol() {
        String[] options = {"X", "O"};
        return (String) JOptionPane.showInputDialog(frame,
                "Do you place X or O? :", "Move",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void dispose() {
        frame.dispose();
    }

    public JButton getButton(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            throw new OutOfBoundsException("Coordinates are outside the grid limits.");
        }
        return buttons[row][col];
    }
    
    public JLabel getPlayerLabel() {
        return playerLabel;
    }

    public void showWinnerMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public boolean askForNewGame() {
        int restart = JOptionPane.showConfirmDialog(frame, "Do you want to play a new game?", "NEW GAME",
                JOptionPane.YES_NO_OPTION);
        return restart == JOptionPane.YES_OPTION;
    }

    public Position getButtonPosition(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        Integer row = (Integer) button.getClientProperty("row");
        Integer col = (Integer) button.getClientProperty("col");
        return (row != null && col != null) ? new Position(row, col) : null;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void resetGrid() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                JButton button = buttons[row][col];
                button.setText("");
                button.setEnabled(true);
            }
        }
    }
}
