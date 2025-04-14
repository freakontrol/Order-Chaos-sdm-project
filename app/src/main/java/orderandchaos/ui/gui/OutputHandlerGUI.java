package orderandchaos.ui.gui;

import java.awt.Desktop;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.JLabel;
import orderandchaos.core.OutputHandler;
import orderandchaos.core.Player;

public class OutputHandlerGUI implements OutputHandler {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel playerLabel;

    public OutputHandlerGUI(JFrame frame, JButton[][] buttons, JLabel playerLabel) {
        this.frame = frame;
        this.buttons = buttons;
        this.playerLabel = playerLabel;
    }

    public void setButtons(JButton[][] buttons) {
        this.buttons = buttons;
    }

    public void setPlayerLabel(JLabel playerLabel) {
        this.playerLabel = playerLabel;
    }

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
    public void updatePlayerLabel(Player currentPlayer) {
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

    @Override
    public void gameWelcome() {
    String html = "<html><body style='font-family:sans-serif; font-size:12px;'>"
            + "Welcome to Order and Chaos!<br>"
            + "Get ready for an exciting battle of strategy and wit on a 6x6 board.<br>"
            + "Columns and rows are numbered from 1 to 6, making it easy to plan your moves.<br>"
            + "Choose your player name and let the game begin!<br>"
            + "If you need to brush up on the rules, you can find them "
            + "<a href='https://en.wikipedia.org/wiki/Order_and_Chaos'>here</a>.<br>"
            + "Have fun and may the best player win!"
            + "</body></html>";

    JEditorPane editorPane = new JEditorPane("text/html", html);// Create a JEditorPane to display the HTML content, JOptionPane doesn't work for the purpouse
    editorPane.setEditable(false); //Non editable text
    editorPane.setOpaque(false);
    editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE); // Use native font

    editorPane.addHyperlinkListener(e -> {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                Desktop.getDesktop().browse(new URI(e.getURL().toString()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });

    JOptionPane.showMessageDialog(frame, editorPane, "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }
}