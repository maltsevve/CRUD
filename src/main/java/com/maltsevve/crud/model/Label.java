package com.maltsevve.crud.model;

public class Label {
    private Long id;
    private String name;

    public Label() {

    }

    public Label(String name) {
        this.name = name;
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
