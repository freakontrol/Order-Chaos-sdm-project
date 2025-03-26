package org.example;

public class Move {
    private final Mark mark;
    private final Player player;

    // Costruttore che imposta 'mark' e 'player'
    public Move(Mark mark, Player player) {
        if (mark == null || player == null) {
            throw new IllegalArgumentException("Mark and player must not be null.");
        }
        this.mark = mark;
        this.player = player;
    }

    // Getter per 'mark' (senza setter, poiché 'mark' è final)
    public Mark getMark() {
        return mark;
    }

    // Getter per 'player' (senza setter, poiché 'player' è final)
    public Player getPlayer() {
        return player;
    }

    // Metodo toString per rappresentare l'oggetto Move come stringa
    @Override
    public String toString() {
        return "Move{" +
                "mark=" + mark.toString() +
                ", player=" + player.toString() +
                '}';
    }

    // Metodo per confrontare il tipo di 'mark' tra due oggetti Move
    public boolean isMarkTypeEqual(Move other) {
        return this.mark.getType().equals(other.getMark().getType());
    }
}
