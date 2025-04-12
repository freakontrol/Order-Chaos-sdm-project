//can't put a mark where we have one already

package orderandchaos.exceptions;

public class OccupiedPositionException extends OrderAndChaosException {

    public OccupiedPositionException() {
        super("Multiple moves to same position are not allowed");
    }
}