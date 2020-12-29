package com.maltsevve.crud.model;

public class Label {
    private static long count = 0;
    private long id;
    private String name;

    public Label(String name) {
        this.name = name;
        id = ++count;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
