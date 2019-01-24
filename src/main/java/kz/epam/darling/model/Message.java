package kz.epam.darling.model;

import java.sql.Timestamp;

public class Message extends Entity {
    private String text;
    private Timestamp created_at;
    private int user_id;


    public Message() {
    }

    public Message(int id) {
        super(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
