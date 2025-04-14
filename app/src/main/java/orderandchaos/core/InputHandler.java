package orderandchaos.core;

import java.io.IOException;

public interface InputHandler {
    String askForSymbol() throws IOException;
    Position getPlayerMove(Player actualPlayer) throws IOException;
    String askForPlayerName(Role role) throws IOException;
    Type getMarkType(Player currentPlayer) throws IOException;
}