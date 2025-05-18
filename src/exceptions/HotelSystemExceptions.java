package exceptions;

public class HotelSystemExceptions extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HotelSystemExceptions() {}

    public HotelSystemExceptions(String message) {
        super(message);
    }

    public static class InvalidCardNumberException extends HotelSystemExceptions {
        public InvalidCardNumberException() { super("Invalid credit card number."); }
    }

    public static class InvalidCVVException extends HotelSystemExceptions {
        public InvalidCVVException() { super("Invalid CVV."); }
    }

    public static class DateException extends HotelSystemExceptions {
        private DateException(String message) {
            super(message);
        }

        public static class InvalidFormatException extends DateException {
            public InvalidFormatException() {
                super("Invalid date format. Please use MM/yyyy.");
            }
        }

        public static class ExpiredException extends DateException {
            public ExpiredException() {
                super("Card expired!");
            }
        }
    }

}
