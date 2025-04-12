//alternate player

package orderandchaos.exceptions;

public class InvalidPlayerException extends OrderAndChaosException {
    
    public InvalidPlayerException() {
        super("Two subsequent moves from the same player are not allowed");
    }
}