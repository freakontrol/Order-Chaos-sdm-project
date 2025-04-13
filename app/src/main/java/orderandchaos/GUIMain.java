package orderandchaos;


import java.awt.event.*;

import javax.swing.JOptionPane;

import orderandchaos.core.*;

public class GUIMain {
    private static Board board;
    private static BoardGUI gui;
    private static Player playerOrder;
    private static Player playerChaos;
    private static Player currentPlayer;

    public static void main(String[] args) {
        initializeGame();
    }

    private static Player initializePlayer(Role role) {
        String name = null;
        while (name == null || name.trim().isEmpty()) {
            name = JOptionPane.showInputDialog(null,
                    "Enter name for " + role + " player:",
                    "Player Name Input",
                    JOptionPane.QUESTION_MESSAGE);
    
            if (name == null || name.trim().isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Do you want to quit the game?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    name = null;
                }
            }
        }
        return new Player(role, name.trim());
    }

    private static void initializeGame() {
       
        board = new Board();
        gui = new BoardGUI(board);

        playerOrder = initializePlayer(Role.ORDER);
        playerChaos = initializePlayer(Role.CHAOS);
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
                }
            }
        }

        private static void restartGame() {
            board.clearBoard();       
            gui.resetGrid();         
            currentPlayer = playerOrder;
            gui.setCurrentPlayer(currentPlayer);
        }
        
    }
}
