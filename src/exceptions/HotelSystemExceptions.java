package exceptions;

public class HotelSystemExceptions extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HotelSystemExceptions() {}

    public HotelSystemExceptions(String message) {
        super(message);
    }

    public HotelSystemExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}