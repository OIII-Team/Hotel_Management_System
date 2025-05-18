package exceptions;

public class HotelSystemPaymentExceptions extends RuntimeException
{

    private static final long serialVersionUID = 1L;

    public HotelSystemPaymentExceptions()
    {
    }

    public HotelSystemPaymentExceptions(String message)
    {
        super(message);
    }

    public static class InvalidCardNumberException extends HotelSystemPaymentExceptions
    {
        public InvalidCardNumberException()
        {
            super("Invalid credit card number.");
        }
    }

    public static class InvalidCVVException extends HotelSystemPaymentExceptions
    {
        public InvalidCVVException()
        {
            super("Invalid CVV.");
        }
    }

    public static class DateException extends HotelSystemPaymentExceptions
    {
        private DateException(String message)
        {
            super(message);
        }

        public static class InvalidFormatException extends DateException
        {
            public InvalidFormatException()
            {
                super("Invalid date format. Please use MM/yyyy.");
            }
        }

        public static class ExpiredException extends DateException
        {
            public ExpiredException()
            {
                super("Card expired!");
            }
        }
    }
}
