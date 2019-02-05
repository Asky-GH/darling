package kz.epam.darling.model;

public class City extends Entity {
    private String name;
    private int country_id;


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

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
}
