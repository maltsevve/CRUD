package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;

import java.util.List;

class test {
    public static void main(String[] args) {
        LabelRepository lr = new LabelRepository();
        lr.save(new Label(1L, "hhhhh"));
        lr.save(new Label(2L, "ggggg"));
        lr.save(new Label(3L, "fffff"));
        List<Label> ll = lr.getAll();
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());

        System.out.println();

        lr.deleteById(1);
        ll = lr.getAll();
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());

        System.out.println();

        Label lb = lr.getById(2L);
        System.out.println(lb.getId() + " " + lb.getName());

        System.out.println();

        // UPDATE НЕ РАБОТАЕТ, ИСПРАВИТЬ
//        lr.update(new Label(4L, "ttttt"));
//        lr.update(new Label(3L, "aaaaa"));
//        ll = lr.getAll();
//        for (Label l : ll)
//            System.out.println(l.getId() + " " + l.getName());
    }
}
