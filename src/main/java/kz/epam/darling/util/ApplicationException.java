package kz.epam.darling.util;

public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super("ApplicationException: for more details see log file!");
    }
}
