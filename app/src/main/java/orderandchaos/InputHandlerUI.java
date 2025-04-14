package orderandchaos;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import orderandchaos.core.InputHandler;
import orderandchaos.core.Position;

import java.io.IOException;

public class InputHandlerUI implements InputHandler {
    private JFrame frame;

    public InputHandlerUI(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public String askForSymbol() throws IOException {
        String[] options = {"X", "O"};
        return (String) JOptionPane.showInputDialog(frame,
                "Do you place X or O?", "Move",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    @Override
    public Position getPlayerMove() throws IOException {
        // This method will be called with an ActionEvent in the listener
        throw new UnsupportedOperationException("This method should not be called directly");
    }

    public Position getButtonPosition(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        Integer row = (Integer) button.getClientProperty("row");
        Integer col = (Integer) button.getClientProperty("col");
        return new Position(row, col);
    }
}