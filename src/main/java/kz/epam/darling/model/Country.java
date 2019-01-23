package kz.epam.darling.model;

public class Country extends Entity {
    private String name;


    public Country() {
    }

    public Country(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
