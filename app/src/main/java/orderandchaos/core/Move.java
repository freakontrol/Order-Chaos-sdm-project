package orderandchaos.core;

public class Move {
    private final Mark mark;
    private final Player player;


    public Move(Mark mark, Player player) {
        if (mark == null || player == null) {
            throw new IllegalArgumentException("Mark and player must not be null.");
        }
        this.mark = mark;
        this.player = player;
    }

    public Mark getMark() {
        return mark;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "Move{" +
                "mark=" + mark.toString() +
                ", player=" + player.toString() +
                '}';
    }

    public boolean isMarkTypeEqual(Move other) {
        return this.mark.getType().equals(other.getMark().getType());
    }
}
