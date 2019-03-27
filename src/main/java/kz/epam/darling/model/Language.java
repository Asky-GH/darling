package kz.epam.darling.model;

public class Language extends Entity {
    private String locale;
    private String name;

    public Language() {
    }

    public Language(int id) {
        super(id);
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
