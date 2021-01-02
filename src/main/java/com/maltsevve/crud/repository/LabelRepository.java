package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LabelRepository {
    private final String labels = "src/main/resources/files/labels.txt";

    public LabelRepository() {
    }

    Label getById(long id) {
        List<Label> labels = readToList();
        Label label = labels.stream().filter((n) -> n.getId().equals(id)).findFirst().orElse(null);
        if (label == null) System.out.println("No such object in the file.");
        return label;
    }

    List<Label> getAll() {
        return readToList();
    }

    Label save(Label label) {
        label.setId(generateID());
        List<Label> labels = readToList();
        labels.add(label);
        writeFromList(labels);
        return label;
    }

    Label update(Label label) {
        List<Label> labels = readToList();
        Label lab = labels.stream().filter((n) -> n.getId().equals(label.getId())).findFirst().orElse(null);
        if (lab == null) System.out.println("Update is unavailable: no such object in the file.");
        else {
            labels.set(labels.indexOf(lab), label);
            writeFromList(labels);
        }
        return label;
    }

    void deleteById(long id) {
        List<Label> labels = readToList();
        if (labels.removeIf(label -> label.getId().equals(id)))
            System.out.println("Label " + id + " deleted.");
        else System.out.println("Delete is unavailable: no such object in the file.");
        writeFromList(labels);
    }

    private void writeFromList(List<Label> list) {
        Path path = Paths.get(labels);
        List<String> strings = list.stream().map((n) -> n.getId() + "=" + n.getName()).collect(Collectors.toList());
        try {
            Files.write(path, strings, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);;
        }
    }

    private List<Label> readToList() {
        List<Label> list = new ArrayList<>();
        List<String> strings;
        Path path = Paths.get(labels);

        try (Stream<String> lineStream = Files.lines(path)) {
            strings = lineStream.collect(Collectors.toList());
            if (!strings.isEmpty()) {
                for (String s : strings) {
                    String[] str = s.split("=");
                    Label label = new Label(str[1]);
                    label.setId(Long.parseLong(str[0]));
                    list.add(label);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return list;
    }

    public long generateID() {
        List<Label> labels = readToList();
        if (!labels.isEmpty())
            return Objects.requireNonNull(labels.stream().max(Comparator.
                    comparing(Label::getId)).orElse(null)).getId() + 1;
        else return 1;
    }
}
