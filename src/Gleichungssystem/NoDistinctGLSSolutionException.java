package Gleichungssystem;

public class NoDistinctGLSSolutionException extends Exception {
    public NoDistinctGLSSolutionException(String message) {
        super(message);
    }

    public NoDistinctGLSSolutionException() {
        super();
    }
}
