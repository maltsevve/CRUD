package com.maltsevve.crud.repository;

import com.maltsevve.crud.model.Label;
import com.maltsevve.crud.model.Post;

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

public class JavaIOPostRepositoryImpl implements PostRepository {
    private final String posts = "src/main/resources/files/posts.txt";

    public JavaIOPostRepositoryImpl() {

    }

    @Override
    public Post save(Post post) {
        post.setId(generateID());
        post.setCreated(new Date());
        List<Post> posts = readToList();
        posts.add(post);
        writeFromList(posts);
        return post;
    }

    @Override
    public Post update(Post post) {
        post.setUpdated(new Date());
        List<Post> posts = readToList();
        Post post1 = posts.stream().filter((n) -> n.getId().equals(post.getId())).findFirst().orElse(null);
        if (post1 == null) {
            System.out.println("Update is unavailable: no such ID in the file.");
        } else {
            posts.set(posts.indexOf(post1), post);
            writeFromList(posts);
        }
        return post;
    }

    @Override
    public Post getById(Long id) {
        List<Post> posts = readToList();
        return posts.stream().filter((n) -> n.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Post> getAll() {
        return readToList();
    }

    @Override
    public void deleteById(Long id) {
        List<Post> posts = readToList();
        if (posts.removeIf(post -> post.getId().equals(id))) {
            System.out.println("Post " + id + " deleted.");
        }
        writeFromList(posts);
    }

    private void writeFromList(List<Post> list) {
        Path path = Paths.get(posts);
        List<String> strings = list.stream().map((p) -> p.getId() + "=" + p.getContent() + ": " + p.getLabels().stream().
                map((l) -> l.getId() + "=" + l.getName()).collect(Collectors.toList()) + " : " + p.getCreated() + ": " +
                p.getUpdated()).collect(Collectors.toList());
        try {
            Files.write(path, strings, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    private List<Post> readToList() {          // МЕТОД УЖАСЕН, ПЕРЕДЕЛАТЬ! Начать с аналогичного в IOLabelRepository
        List<Post> list = new ArrayList<>();
        Path path = Paths.get(posts);

        try (Stream<String> lineStream = Files.lines(path)) {
            List<String> strings = lineStream.collect(Collectors.toList());

            if (!strings.isEmpty()) {
                for (String s : strings) {
                    String[] str = s.split(": ");
                    String[] str2 = str[1].substring(1, str[1].length() - 2).split(", ");

                    List<Label> labels = new ArrayList<>();
                    for (String string : str2) {
                        String[] str3 = string.split("=");
                        Label label = new Label(str3[1]);
                        label.setId(Long.parseLong(str3[0]));
                        labels.add(label);
                    }

                    String[] str4 = str[0].split("=");
                    Post post = new Post(str4[1], labels);
                    post.setId(Long.parseLong(str4[0]));

                    if (str[2].length() > 4) {
                        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        try {
                            post.setCreated(dateFormat.parse(str[2]));
                        } catch (ParseException e) {
                            System.out.println("Не удалось установить дату создания.");
                        }
                    }
                    if (str[3].length() > 4) {
                        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                        try {
                            post.setUpdated(dateFormat.parse(str[3]));
                        } catch (ParseException e) {
                            System.out.println("Не удалось установить дату изменения.");
                        }
                    }
                    list.add(post);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + e);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return list;
    }

    Long generateID() {
        List<Post> posts = readToList();
        if (!posts.isEmpty()) {
            return Objects.requireNonNull(posts.stream().max(Comparator.
                    comparing(Post::getId)).orElse(null)).getId() + 1;
        } else {
            return 1L;
        }
    }
}
