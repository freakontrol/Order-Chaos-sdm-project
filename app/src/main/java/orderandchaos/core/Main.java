package orderandchaos.core;


import java.awt.event.*;

import orderandchaos.exceptions.InvalidPlayerException;
import orderandchaos.exceptions.OccupiedPositionException;

public class Main {
    private static Board board;
    private static BoardGUI gui;
    private static Player playerOrder;
    private static Player playerChaos;
    private static Player currentPlayer;

    public static void main(String[] args) {
        initializeGame();
    }

    private static void initializeGame() {
       
        board = new Board();
        gui = new BoardGUI(board);

        playerOrder = new Player(Role.ORDER, "ORDER");
        playerChaos = new Player(Role.CHAOS, "CHAOS");
        currentPlayer = playerOrder;
        gui.setCurrentPlayer(currentPlayer);

        gui.setMoveListener(new MoveListener());
    }


    private static class MoveListener implements ActionListener {
      
        @Override
        public void actionPerformed(ActionEvent e) {
            Position position = gui.getButtonPosition(e);
        
            if (!board.isOccupied(position)) {
                String choice = gui.askForSymbol();

                if (choice != null) {
                    Type markType = choice.equals("X") ? Type.X : Type.O;
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, currentPlayer);

                    try {
                        board.addMove(move);
                        gui.updateButton(position.getRow(), position.getColumn(), markType.getName());

                        if (board.isFiveInLineFound()) {
                            gui.showWinnerMessage("Player ORDER wins!");
                            if (gui.askForNewGame()) {
                                restartGame();
                            } else {
                                gui.dispose();
                                System.exit(0);
                            }
                            return;
                        }

                        if (board.getMoves().size() == 36) {
                            gui.showWinnerMessage("Player CHAOS wins!");
                            if (gui.askForNewGame()) {
                                restartGame();
                            } else {
                                gui.dispose();
                                System.exit(0);
                            }
                            return;
                        }

                        currentPlayer = (currentPlayer.getRole() == Role.ORDER) ? playerChaos : playerOrder;
                        gui.setCurrentPlayer(currentPlayer);
                    } catch (InvalidPlayerException | OccupiedPositionException ex) {
                        ex.printStackTrace();
                        gui.showErrorMessage(ex.getMessage());
                    }
                }
            }
        }
        private static void restartGame() {
            gui.dispose();
            initializeGame();
        }
    }
}
