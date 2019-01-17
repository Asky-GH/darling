package kz.epam.darling;

public enum RequestMethod {
    POST("POST");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
