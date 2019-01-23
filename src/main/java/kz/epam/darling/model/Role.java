package kz.epam.darling.model;

public class Role extends Entity {
    private String name;


    public Role() {
    }

    public Role(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
