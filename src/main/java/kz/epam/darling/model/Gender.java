package kz.epam.darling.model;

public class Gender extends Entity {
    private String name;


    public Gender() {
    }

    public Gender(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
