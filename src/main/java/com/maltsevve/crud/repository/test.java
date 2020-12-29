package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;

import java.util.List;

class test {
    public static void main(String[] args) {
        LabelRepository lr = new LabelRepository();
        lr.save(new Label("hhhhh"));
        lr.save(new Label("ggggg"));
        lr.save(new Label("fffff"));
        List<Label> ll = lr.getAll();
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());

        System.out.println();

        lr.deleteById(2);
        ll = lr.getAll();
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());

        System.out.println();

        Label lb = lr.getById(1);
        System.out.println(lb.getId() + " " + lb.getName());

        System.out.println();

        Label label = new Label("AAAAA");
        label.setId(1);
        lr.update(label);
        ll = lr.getAll();
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());
    }
}
