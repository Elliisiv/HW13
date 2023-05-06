package org.hw13.Todos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todos {

    int userId;
    int id;
    String title;
    boolean completed;

    public Todos(int userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Todos() {
    }

    @Override
    public String toString() {
        return "Todos{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }


}