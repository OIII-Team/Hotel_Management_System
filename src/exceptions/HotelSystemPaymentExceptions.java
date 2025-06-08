package exceptions;

public class HotelSystemPaymentExceptions extends RuntimeException
{
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

    public static class InvalidEmailException extends HotelSystemPaymentExceptions
    {
        public InvalidEmailException()
        {
            super("Invalid email address.");
        }
    }

    public static class InvalisPayerIdException extends HotelSystemPaymentExceptions
    {
        public InvalisPayerIdException()
        {
            super("Invalid payer ID.");
        }
    }
}
