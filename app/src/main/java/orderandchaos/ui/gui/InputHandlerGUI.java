package orderandchaos.ui.gui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import orderandchaos.core.Player;
import orderandchaos.core.Position;
import orderandchaos.core.Role;
import orderandchaos.core.Type;
import orderandchaos.ui.InputHandler;

import java.io.IOException;

public class InputHandlerGUI implements InputHandler {
    private JFrame frame;

    public InputHandlerGUI(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public Position getPlayerMove(Player actualPlayer) throws IOException {
        // This method will be called with an ActionEvent in the listener
        throw new UnsupportedOperationException("This method should not be called directly");
    }

    public Position getButtonPosition(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        Integer row = (Integer) button.getClientProperty("row");
        Integer col = (Integer) button.getClientProperty("col");
        return new Position(row, col);
    }

    @Override
    public Type getMarkType(Player currentPlayer) throws IOException {
        String[] options = {"X", "O"};
        String choice = (String)  JOptionPane.showInputDialog(frame, "Do you place X or O?", "Move", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == null) {
            return null;
        }

        return choice.equals("X") ? Type.X : Type.O;
    }
    public boolean askToSwitchRoles() {
        int choice = JOptionPane.showConfirmDialog(frame,
                "Do you want to switch roles between ORDER and CHAOS?",
                "Switch Roles",
                JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }
    
    @Override
    public String askForPlayerName(Role role) throws IOException {
        String name = null;
        while (true) {
            name = JOptionPane.showInputDialog(null,
                    "Enter name for " + role + " player:",
                    "Player Name Input",
                    JOptionPane.QUESTION_MESSAGE);

            if (name == null) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Do you want to quit the game?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
                else continue;
            }

            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "The " + role + " name can't be empty",
                        "Choose your name",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                return name.trim();
            }
        }
    }
}