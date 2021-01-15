package com.maltsevve.crud.view;

import com.maltsevve.crud.controller.PostController;
import com.maltsevve.crud.controller.WriterController;
import com.maltsevve.crud.model.Label;
import com.maltsevve.crud.model.Post;
import com.maltsevve.crud.model.Writer;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.maltsevve.crud.Runner.mainMenuLogic;

public class WriterView {
    private final WriterController wc = new WriterController();
    private final PostController pc = new PostController();
    private final PostView pv = new PostView(); // Временное решение, чтобы не раздувать код еще больше.

    Scanner sc = new Scanner(System.in);

    public WriterView() {

    }

    private String menu() {
        return ("'WRITERS'\n" +
                "Select menu item:\n" +
                "1 - Save\n" +
                "2 - Update\n" +
                "3 - Get\n" +
                "4 - Get all\n" +
                "5 - Delete\n" +
                "6 - Return");
    }

    public void logic() {
        System.out.println(menu());
        String input = sc.nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> { // SAVE
                    System.out.println("Input first name: ");
                    input = sc.nextLine();
                    String firstName = input;
                    System.out.println("Input last name: ");
                    input = sc.nextLine();
                    wc.save(new Writer(firstName, input, pc.getAll()));
                    System.out.println();
                    logic();
                }
                case 2 -> { // UPDATE
                    System.out.println();
                    System.out.println("Select option:\n" +
                            "1 - Update first name\n" +
                            "2 - Update last name\n" +
                            "3 - Update Post\n" +
                            "4 - Return");
                    input = sc.nextLine();

                    if (input.matches("\\d+")) {
                        switch (Integer.parseInt(input)) {
                            case 1 -> { // UPDATE FIRSTNAME
                                System.out.println("Input id and a new first name: 'id=first name'");
                                String[] str = sc.nextLine().split("=");

                                if (str.length == 2 && str[0].matches("\\d+")) {
                                    List<Writer> writers = wc.getAll();
                                    if (writers.stream().anyMatch(l -> l.getId() == (Long.parseLong(str[0])))) {
                                        Writer oldWriter = wc.getByID(Long.parseLong(str[0]));
                                        if (Integer.parseInt(str[0]) > 0 && !oldWriter.getFirstName().equals(str[1])) {
                                            String lastName = oldWriter.getLastName();
                                            List<Post> posts = oldWriter.getPosts();

                                            Writer writer = new Writer(str[1], lastName, posts);
                                            writer.setId(Long.parseLong(str[0]));

                                            wc.update(writer);
                                            System.out.println();
                                        } else {
                                            System.out.println("The entered content is identical to the existing one.\n");
                                        }
                                    } else {
                                        System.out.println("No such object in the file.\n");
                                    }
                                } else {
                                    System.out.println("Invalid input format.\n");
                                }
                                logic();
                            }
                            case 2 -> { // UPDATE LASTNAME
                                System.out.println("Input id and a new last name: 'id=last name'");
                                String[] str = sc.nextLine().split("=");

                                if (str.length == 2 && str[0].matches("\\d+")) {
                                    List<Writer> writers = wc.getAll();
                                    if (writers.stream().anyMatch(l -> l.getId() == (Long.parseLong(str[0])))) {
                                        Writer oldWriter = wc.getByID(Long.parseLong(str[0]));
                                        if (Integer.parseInt(str[0]) > 0 && !oldWriter.getLastName().equals(str[1])) {
                                            String firstName = oldWriter.getFirstName();
                                            List<Post> posts = oldWriter.getPosts();

                                            Writer writer = new Writer(firstName, str[1], posts);
                                            writer.setId(Long.parseLong(str[0]));

                                            wc.update(writer);
                                            System.out.println();
                                        } else {
                                            System.out.println("The entered content is identical to the existing one.\n");
                                        }
                                    } else {
                                        System.out.println("No such object in the file.\n");
                                    }
                                } else {
                                    System.out.println("Invalid input format.\n");
                                }
                                logic();
                            }
                            case 3 -> { // UPDATE POST
                                pv.updatePost(); // Временное решение. Возможно перенести updatePost в новый класс?
                                // Главный недостаток - после использования окажемся в меню posts,
                                // хотя до этого работали с writers
                            }
                            case 4 -> logic();
                            default -> {
                                System.out.println("Non-existent menu item. Try again.\n");
                                logic();
                            }
                        }
                    }
                }
                case 3 -> { // GET BY ID
                    System.out.println("Input id: ");
                    input = sc.nextLine();
                    if (input.matches("\\d+")) {
                        Writer writer = wc.getByID(Long.parseLong(input));
                        if (writer != null) {
                            System.out.print(writer.getId() + " " + writer.getFirstName() + " " +
                                    writer.getLastName() + ":\n");
                            List<String> strings = writer.getPosts().stream().map(post ->
                                    post.getId() + "=" + post.getContent() + " Created: " + post.getCreated() +
                                            " Updated: " + post.getUpdated() + " Labels: " + post.getLabels().stream().
                                            map(label -> label.getId() + "=" + label.getName()).
                                            collect(Collectors.toList()) + "\n").collect(Collectors.toList());
                            strings.forEach(System.out::print);
                            System.out.println();
                        } else {
                            System.out.println("No such object in the file.\n");
                        }
                    } else {
                        System.out.println("Invalid ID.\n");
                    }
                    logic();
                }
                case 4 -> { // GET ALL
                    System.out.println();
                    List<Writer> writers = wc.getAll();
                    if (!writers.isEmpty()) {
                        for (Writer writer : writers) {
                            System.out.print(writer.getId() + " " + writer.getFirstName() + " " +
                                    writer.getLastName() + ":\n");

                            for (Post post : writer.getPosts()) {
                                System.out.print(post.getId() + "=" + post.getContent() + " Created: " + post.getCreated()
                                        + " Updated: " + post.getUpdated() + " Labels: ");
                                for (Label label : post.getLabels()) {
                                    System.out.print(label.getId() + "=" + label.getName() + " ");
                                }
                                System.out.println();
                            }
                            System.out.println();
                        }
                    } else {
                        System.out.println("File is empty.\n");
                    }
                    logic();
                }
                case 5 -> { // DELETE BY ID
                    System.out.println("Input id: ");
                    input = sc.nextLine();
                    if (input.matches("\\d+")) {
                        Long l = Long.parseLong(input);
                        if (l > 0 && wc.getAll().stream().anyMatch(wr -> wr.getId().equals(l))) {
                            wc.deleteById(l);
                            System.out.println();
                        } else {
                            System.out.println("No such object in the file.\n");
                        }
                    } else {
                        System.out.println("Invalid ID.\n");
                    }
                    logic();
                }
                case 6 -> {
                    System.out.println();
                    mainMenuLogic();
                }
                default -> {
                    System.out.println("Non-existent menu item. Try again.\n");
                    logic();
                }
            }
        } else {
            System.out.println("Use digits from 1 to 4.\n");
            logic();
        }
    }
}
