package orderandchaos.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;

    public BoardGUI(Board board) {
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Order & Chaos Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);
        frame.setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(6, 6));

        buttons = new JButton[6][6];

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                buttons[row][col] = new JButton("");                
                buttons[row][col].setFont(new Font("Ubuntu", Font.BOLD, 40));
                buttons[row][col].putClientProperty("row", row);
                buttons[row][col].putClientProperty("col", col);
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setForeground(Color.BLACK);
                buttons[row][col].setFocusPainted(false); 
                gridPanel.add(buttons[row][col]);
            }
        }

       playerLabel = new JLabel("shift of: ", SwingConstants.CENTER);
       playerLabel.setFont(new Font("Ubuntu", Font.BOLD, 18));
       mainPanel.add(playerLabel, BorderLayout.NORTH);
       mainPanel.add(gridPanel, BorderLayout.CENTER);
        

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void setCurrentPlayer(Player player) {
        playerLabel.setText("shift of: " + player.getName());
    }

    public void setMoveListener(ActionListener listener) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
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
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            throw new IllegalArgumentException("Coordinates are outside the grid limits.");
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
