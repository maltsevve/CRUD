package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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

//        for (Label label : labels) {
//            if (label.getId().equals(id))
//                return label;
//        }
//        System.out.println("No such object in the file.");
//        return null;
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

//        for (int i = 0; i < labels.size(); i++) {
//            if (labels.get(i).getId().equals(label.getId())) {
//                labels.set(i, label);
//                writeFromList(labels);
//                return label;
//            }
//        }
//        System.out.println("Update is unavailable: no such object in the file.");
//        return label;
    }

    void deleteById(long id) {
        List<Label> labels = readToList();
        if (labels.removeIf(label -> label.getId().equals(id)))
            System.out.println("Label " + id + " deleted.");
        else System.out.println("Delete is unavailable: no such object in the file.");
        writeFromList(labels);
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
//        List<String> strings = new ArrayList<>();
//        Path path = Paths.get(labels);
//
//        try (Stream<String> lineStream = Files.lines(path)) {
//            strings = lineStream.collect(Collectors.toList());
//            if (!strings.isEmpty()) {
//
//            }
        try (FileReader fr = new FileReader(labels)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fr.read()) != -1) sb.append((char) c);

            if (sb.length() > 0) {
                String[] labels = sb.toString().split("\n");

                for (String s : labels) {
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

    public long generateID() {
        List<Label> labels = readToList();
        if (!labels.isEmpty())
            return Objects.requireNonNull(labels.stream().max(Comparator.
                    comparing(Label::getId)).orElse(null)).getId() + 1;
        else return 1;

//        long count = 1;
//        for (Label label : labels) {
//            count = label.getId() >= count ? label.getId() + 1 : count;
//        }
//        return count;
    }
}
