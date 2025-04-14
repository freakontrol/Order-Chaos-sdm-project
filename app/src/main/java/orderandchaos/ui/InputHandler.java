package orderandchaos.ui;

import java.io.IOException;

import orderandchaos.core.Player;
import orderandchaos.core.Position;
import orderandchaos.core.Role;
import orderandchaos.core.Type;

public interface InputHandler {
    Position getPlayerMove(Player actualPlayer) throws IOException;
    String askForPlayerName(Role role) throws IOException;
    Type getMarkType(Player currentPlayer) throws IOException;
}