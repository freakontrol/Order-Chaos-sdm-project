package orderandchaos.core;

import java.io.IOException;

public interface InputHandler {
    Position getPlayerMove(Player actualPlayer) throws IOException;
    String askForPlayerName(Role role) throws IOException;
    Type getMarkType(Player currentPlayer) throws IOException;
}