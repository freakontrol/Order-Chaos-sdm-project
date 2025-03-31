package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BoardGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;

    public BoardGUI(Board board) {
        frame = new JFrame("Order & Chaos Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);
        frame.setResizable(false);
        
        JOptionPane.showMessageDialog(frame, "Player ORDER is your round", "BEFORE WE START", JOptionPane.INFORMATION_MESSAGE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(6, 6));

        buttons = new JButton[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                buttons[i][j] = new JButton();                
                buttons[i][j].setFont(new Font("Ubuntu", Font.BOLD, 40));
                buttons[i][j].putClientProperty("row", i);
                buttons[i][j].putClientProperty("col", j);
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setForeground(Color.BLACK);
                buttons[i][j].setFocusPainted(false); 
                gridPanel.add(buttons[i][j]);
            }
        }

       playerLabel = new JLabel("shift of:", SwingConstants.CENTER);
       playerLabel.setFont(new Font("Ubuntu", Font.BOLD, 18));
       mainPanel.add(playerLabel, BorderLayout.NORTH);
       mainPanel.add(gridPanel, BorderLayout.CENTER);
        

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void setCurrentPlayer(Player player) {
        playerLabel.setText("shift of:   " + player.getName());
    }

    public void setMoveListener(ActionListener listener) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                buttons[i][j].addActionListener(listener);
            }
        }
    }

    public void updateButton(int row, int col, String symbol) {
        buttons[row][col].setText(symbol);
        buttons[row][col].setEnabled(false);
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
}
