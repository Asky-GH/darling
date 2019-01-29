package kz.epam.darling.model;

public class City extends Entity {
    private String name;


    public City() {
    }

    public City(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
