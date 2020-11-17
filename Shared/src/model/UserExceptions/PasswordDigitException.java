package model.UserExceptions;

public class PasswordDigitException extends Exception{
    public PasswordDigitException() {
        super("the password must be contain numbers");
    }
}
