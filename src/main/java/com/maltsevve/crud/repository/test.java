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
        System.out.println("Содержимое labels.txt после добавления 3х объектов Label");
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());
        System.out.println();

        lr.deleteById(2);
        ll = lr.getAll();
        System.out.println("Содержимое labels.txt после удаления объекта с индексом 2");
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());
        System.out.println();


        Label lb = lr.getById(1);
        System.out.println("id и name из файла по индексу: " + lb.getId() + " " + lb.getName());
        System.out.println();

        Label label = new Label("AAAAA");
        label.setId(1);
        lr.update(label);
        ll = lr.getAll();
        System.out.println("Содержимое labels.txt после обновления объекта с индексом 1");
        for (Label l : ll)
            System.out.println(l.getId() + " " + l.getName());
        System.out.println();

        System.out.println("Попытка обновить объект которого нет в файле labels.txt: ");
        label.setId(8);
        lr.update(label);
        System.out.println();

        System.out.println("Конец.");
    }
}
