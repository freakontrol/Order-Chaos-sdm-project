package orderandchaos.core;

import java.io.IOException;

public interface InputHandler {
    String askForSymbol() throws IOException;
    Position getPlayerMove() throws IOException;
}