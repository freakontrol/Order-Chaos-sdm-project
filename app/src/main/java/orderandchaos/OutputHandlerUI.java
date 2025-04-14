package orderandchaos;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import orderandchaos.core.OutputHandler;
import orderandchaos.core.Player;

public class OutputHandlerUI implements OutputHandler {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;
    private Player currentPlayer;

    public OutputHandlerUI(JFrame frame, JButton[][] buttons, JLabel playerLabel, Player currentPlayer) {
        this.frame = frame;
        this.buttons = buttons;
        this.playerLabel = playerLabel;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void updateButton(int row, int col, String symbol) {
        if (buttons[row][col].getText().isEmpty()) {
            buttons[row][col].setText(symbol);
            buttons[row][col].setEnabled(false);
        }
    }

    @Override
    public void showWinnerMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    @Override
    public boolean askForNewGame() {
        int restart = JOptionPane.showConfirmDialog(frame, "Do you want to play a new game?", "NEW GAME",
                JOptionPane.YES_NO_OPTION);
        return restart == JOptionPane.YES_OPTION;
    }

    @Override
    public void updatePlayerLabel() {
        playerLabel.setText("Current turn: " + currentPlayer.getName());
    }

    @Override
    public void resetBoardDisplay() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
    }
}