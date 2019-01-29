package kz.epam.darling.model;

public class Role extends Entity {
    private String type;


    public Role() {
    }

    public Role(int id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
