package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LabelRepository {
    private final String labels = "src/main/resources/files/labels.txt";
    List<Label> temp;

    Label getById(long id) {
        temp = readToList();
        for (Label label : temp) {
            if (label.getId().equals(id))
                return label;
        }
        return null;
    }

    List<Label> getAll() {
        return readToList();
    }

    void save(Label label) {
        temp = readToList();
        temp.add(label);
        writeFromList(temp);
    }

    void update(Label label) {
        temp = readToList();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getId().equals(label.getId()))
                temp.set(i, label);
        }
        writeFromList(temp);
    }

    void deleteById(long id) {
        temp = readToList();
        temp.removeIf(label -> label.getId().equals(id));
        writeFromList(temp);
    }

    private void writeFromList(List<Label> list) {
        File file = new File(labels);
        try (FileWriter fw = new FileWriter(file, false)) {
            for (Label label : list) {
                fw.write(label.getId() + "=" + label.getName() + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    private List<Label> readToList() {
        List<Label> list = new ArrayList<>();
        try (FileReader fr = new FileReader(labels)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fr.read()) != -1) sb.append((char) c);

            if (sb.length() > 0) {
                String[] temp = sb.toString().split("\n");

                for (String s : temp) {
                    String[] str = s.split("=");
                    Label label = new Label(str[1]);
                    label.setId(Long.parseLong(str[0]));
                    list.add(label);
                }
                return list;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return list;
    }
}
