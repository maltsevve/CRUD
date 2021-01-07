package com.maltsevve.crud.controller;

import com.maltsevve.crud.model.Label;
import com.maltsevve.crud.repository.LabelRepository;

import java.util.List;

public class LabelController { //Тут так же выполняем проверку всех входящих данных
    LabelRepository lr = new LabelRepository();

    public LabelController() {
    }

    public Label getByID(long id) {
        // добавить проверку: существет ли такой id
        return lr.getById(id);
    }

    public List<Label> getAll() {
        return lr.getAll();
    }

    public Label save(Label label) {
        return lr.save(label);
    }

    public Label update(Label label) {
        // добавить проверку: существет ли такой id
        // возможно добавить проверку на равенство нового и старого name
        return lr.update(label);
    }

    public void deleteById(long id) {
        // добавить проверку: существет ли такой id
        lr.deleteById(id);
    }
}
