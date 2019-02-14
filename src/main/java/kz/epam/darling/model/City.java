package kz.epam.darling.model;

public class City extends Entity {
    private String name;
    private int countryId;


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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
