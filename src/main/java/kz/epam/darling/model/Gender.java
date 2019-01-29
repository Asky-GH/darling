package kz.epam.darling.model;

public class Gender extends Entity {
    private String type;


    public Gender() {
    }

    public Gender(int id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
