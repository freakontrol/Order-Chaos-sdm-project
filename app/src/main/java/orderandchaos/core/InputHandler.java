package orderandchaos.core;

import java.io.IOException;

public interface InputHandler {
    String askForSymbol() throws IOException;
    Position getPlayerMove() throws IOException;
    String askForPlayerName(Role role) throws IOException;
    Type getMarkType() throws IOException;
}