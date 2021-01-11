package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;
import com.maltsevve.crud.model.Post;
import com.maltsevve.crud.model.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaIOWriterRepositoryImpl implements WriterRepository{
    private final String writers = "src/main/resources/files/writers.txt";

    public JavaIOWriterRepositoryImpl(){}

    @Override
    public Writer save(Writer writer) {
        writer.setId(generateID());
        List<Writer> writers = readToList();
        writers.add(writer);
        writeFromList(writers);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        List<Writer> writers = readToList();
        Writer writer1 = writers.stream().filter((n) -> n.getId().equals(writer.getId())).findFirst().orElse(null);
        if (writer1 == null) {
            System.out.println("Update is unavailable: no such ID in the file.");
        } else {
            writers.set(writers.indexOf(writer1), writer);
            writeFromList(writers);
        }
        return writer;
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> writers = readToList();
        return writers.stream().filter((n) -> n.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Writer> getAll() {
        return readToList();
    }

    @Override
    public void deleteById(Long id) {
        List<Writer> writers = readToList();
        if (writers.removeIf(writer -> writer.getId().equals(id))) {
            System.out.println("writer " + id + " deleted.");
        }
        writeFromList(writers);
    }

    private void writeFromList(List<Writer> list) {
        Path path = Paths.get(writers);
        List<String> strings = list.stream().map((w) -> w.getId() + "=" + w.getFirstName() + "=" + w.getLastName() +
                ": " + w.getPosts().stream().map((p) -> p.getId() + "=" + p.getContent() + ": " + p.getLabels().stream().
                map((l) -> l.getId() + "=" + l.getName()).collect(Collectors.toList()) + " : " + p.getCreated() + ": " +
                p.getUpdated())).collect(Collectors.toList());
        try {
            Files.write(path, strings, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    private List<Writer> readToList() {          // МЕТОД УЖАСЕН, ПЕРЕДЕЛАТЬ! Начать с аналогичного в IOLabelRepository
//        List<Writer> list = new ArrayList<>();
//        Path path = Paths.get(writers);
//
//        try (Stream<String> lineStream = Files.lines(path)) {
//            List<String> strings = lineStream.collect(Collectors.toList());
//
//            if (!strings.isEmpty()) {
//                for (String s : strings) {
//                    // 0 - writers id & names; 1 - post id & content; 2 - labels id & name; 3 - Create date; 4 - Update date.
//                    String[] str = s.split(": ");
//                    //
//                    String[] str1 = str[1].substring(1, str[1].length() - 1).split("=");
//                    String[] str2 = str[2].substring(1, str[2].length() - 2).split(", ");
//
//                    List<Post> posts = new ArrayList<>();
//                    for (String string : str2) {
//                        String[] str3 = string.split("=");
//                        Post post = new Post(str3[1], );
//                        label.setId(Long.parseLong(str3[0]));
//                        labels.add(label);
//                    }
//
//                    String[] str4 = str[0].split("=");
//                    Post post = new Post(str4[1], labels);
//                    post.setId(Long.parseLong(str4[0]));
//
//                    if (str[2].length() > 4) {
//                        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//                        try {
//                            post.setCreated(dateFormat.parse(str[2]));
//                        } catch (ParseException e) {
//                            System.out.println("Не удалось установить дату создания.");
//                        }
//                    }
//                    if (str[3].length() > 4) {
//                        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//                        try {
//                            post.setUpdated(dateFormat.parse(str[3]));
//                        } catch (ParseException e) {
//                            System.out.println("Не удалось установить дату изменения.");
//                        }
//                    }
//                    list.add(post);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Файл не найден: " + e);
//        } catch (IOException e) {
//            System.out.println("Ошибка ввода-вывода: " + e);
//        }
//        return list;
        return null;
    }


    Long generateID() {
        List<Writer> writers = readToList();
        if (!writers.isEmpty()) {
            return Objects.requireNonNull(writers.stream().max(Comparator.
                    comparing(Writer::getId)).orElse(null)).getId() + 1;
        } else {
            return 1L;
        }
    }
}
