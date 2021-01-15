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

public class JavaIOWriterRepositoryImpl implements WriterRepository {
    private final String writers = "src/main/resources/files/writers.txt";

    public JavaIOWriterRepositoryImpl() {
    }

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
            System.out.println("Writer " + id + " deleted.");
        }
        writeFromList(writers);
    }

    private void writeFromList(List<Writer> list) {
        Path path = Paths.get(writers);
        List<String> strings = list.stream().map((w) -> w.getId() + "=" + w.getFirstName() + "=" + w.getLastName() +
                ": \n" + w.getPosts().stream().map((p) -> p.getId() + "=" + p.getContent() + ": " + p.getLabels().stream().
                map((l) -> l.getId() + "=" + l.getName()).collect(Collectors.toList()) + ": " + p.getCreated() + ": " +
                p.getUpdated() + "\n").collect(Collectors.toList()) + "\n").collect(Collectors.toList());
        try {
            Files.write(path, strings, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    private List<Writer> readToList() {          // МЕТОД УЖАСЕН, ПЕРЕДЕЛАТЬ! Начать с аналогичного в IOLabelRepository
        Path path = Paths.get(writers);
        List<Writer> writers = new ArrayList<>();

        try (Stream<String> lineStream = Files.lines(path)) {
            List<String> strings = lineStream.collect(Collectors.toList());

            if (!strings.isEmpty()) {
                List<Post> posts = new ArrayList<>();
                String firstName = "";
                String lastName = "";
                Long id = 0L;

                for (String s : strings) {
                    if (!s.isEmpty()) {
                        String[] str = s.split("=");
                        if (s.substring(0, str[0].length()).matches("\\d+")) {
                            id = Long.parseLong(str[0]);
                            firstName = str[1];
                            lastName = str[2].substring(0, str[2].length() - 2);
                        } else if (!s.substring(0, 1).matches("]")){
                            String[] str1 = s.split(": ");
                            String[] str2 = str1[1].substring(1, str1[1].length() - 1).split(", ");

                            List<Label> labels = new ArrayList<>();
                            for (String string : str2) {
                                String[] str3 = string.split("=");
                                Label label = new Label(str3[1]);
                                label.setId(Long.parseLong(str3[0]));
                                labels.add(label);
                            }

                            String[] str4 = str1[0].split("=");
                            String postID;
                            if (str4[0].substring(0, 1).matches("\\[")) {
                                postID = str4[0].substring(1, 2);
                            } else if (str4[0].substring(0, 2).matches(", ")) {
                                postID = str4[0].substring(2, 3);
                            } else {
                                postID = str4[0].substring(0, 1);
                            }
                            Post post = new Post(str4[1], labels);
                            post.setId(Long.parseLong(postID));

                            if (str1[2].length() > 4) {
                                DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                try {
                                    post.setCreated(dateFormat.parse(str1[2]));
                                } catch (ParseException e) {
                                    System.out.println("Не удалось установить дату создания.");
                                }
                            }
                            if (str1[3].length() > 4) {
                                DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                                try {
                                    post.setUpdated(dateFormat.parse(str1[3]));
                                } catch (ParseException e) {
                                    System.out.println("Не удалось установить дату изменения.");
                                }
                            }
                            posts.add(post);
                        }
                    } else if (!firstName.isEmpty() && !lastName.isEmpty() && !posts.isEmpty() && id > 0) {
                        Writer writer = new Writer(firstName, lastName, posts);
                        writer.setId(id);
                        writers.add(writer);
                        posts = new ArrayList<>();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return writers;
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
