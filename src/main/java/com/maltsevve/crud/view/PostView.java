package com.maltsevve.crud.view;

import com.maltsevve.crud.controller.LabelController;
import com.maltsevve.crud.controller.PostController;
import com.maltsevve.crud.model.Label;
import com.maltsevve.crud.model.Post;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.maltsevve.crud.Runner.mainMenuLogic;

public class PostView {
    private final PostController pc = new PostController();
    private final LabelController lc = new LabelController();
    Scanner sc = new Scanner(System.in);

    public PostView() {

    }

    private String menu() {
        return ("'POSTS'\n" +
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
                    System.out.println("Input content: ");
                    input = sc.nextLine();
                    pc.save(new Post(input, lc.getAll()));
                    System.out.println();
                    logic();
                }
                case 2 -> { // UPDATE
                    updatePost();
                }
                case 3 -> { // GET BY ID
                    System.out.println("Input id: ");
                    input = sc.nextLine();
                    if (input.matches("\\d+")) {
                        Post post = pc.getByID(Long.parseLong(input));
                        if (post != null) {
                            if (post.getCreated() != null) {
                                System.out.println("Created: " + post.getCreated());
                            }
                            if (post.getUpdated() != null){
                                System.out.println("Updated: " + post.getUpdated());
                            }
                            System.out.print(post.getId() + " " + post.getContent() + " Labels: ");
                            List<String> strings = post.getLabels().stream().map(label ->
                                    label.getId() + "=" + label.getName() + " ").collect(Collectors.toList());
                            strings.forEach(System.out::print);
                            System.out.println("\n");
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
                    List<Post> posts = pc.getAll();
                    if (!posts.isEmpty()) {
                        for (Post post : posts) {
                            if (post.getCreated() != null) {
                                System.out.println("Created: " + post.getCreated());
                            }
                            if (post.getUpdated() != null){
                                System.out.println("Updated: " + post.getUpdated());
                            }
                            System.out.print(post.getId() + " " + post.getContent() + " Labels: ");
                            for (Label label : post.getLabels()) {
                                System.out.print(label.getId() + "=" + label.getName() + " ");
                            }
                            System.out.println("\n");
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
                        if (l > 0 && pc.getAll().stream().anyMatch(pst -> pst.getId().equals(l))) {
                            pc.deleteById(l);
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
            System.out.println("Use digits from 1 to 3.\n");
            logic();
        }
    }

    public void updatePost() {
        System.out.println();
        System.out.println("Select option:\n" +
                "1 - Update content\n" +
                "2 - Update Label\n" +
                "3 - Return");
        String input = sc.nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> { // UPDATE CONTENT
                    System.out.println("Input id and a new content: 'id=content'");
                    String[] str = sc.nextLine().split("=");

                    if (str.length == 2 && str[0].matches("\\d+")) {
                        List<Post> posts = pc.getAll();
                        if (posts.stream().anyMatch(l -> l.getId() == (Long.parseLong(str[0])))) {
                            if (Integer.parseInt(str[0]) > 0 && !posts.get(Integer.
                                    parseInt(str[0]) - 1).getContent().equals(str[1])) {
                                List<Label> labels = posts.get(Integer.parseInt(str[0]) - 1).getLabels();
                                Post post = new Post(str[1], labels);
                                post.setId(Long.parseLong(str[0]));
                                post.setCreated(posts.get(Integer.parseInt(str[0]) - 1).getCreated());
                                pc.update(post);
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
                case 2 -> { // UPDATE LABELS
                    System.out.println("Input post id:");
                    input = sc.nextLine();

                    if (input.matches("\\d+")) {
                        Long postID = Long.parseLong(input);

                        if (pc.getAll().stream().anyMatch(p -> p.getId().equals(postID))) {
                            System.out.println("Input Label id and a new Label name: 'id=name'");
                            String[] str = sc.nextLine().split("=");

                            if (str.length == 2 && str[0].matches("\\d+")) {
                                int labelID = Integer.parseInt(str[0]);
                                List<Label> labels = pc.getByID(postID).getLabels();
                                String content = pc.getByID(postID).getContent();

                                Label label = new Label(str[1]);
                                label.setId(Long.parseLong(str[0]));
                                labels.set(labelID - 1, label);

                                Post post = new Post(content, labels);
                                post.setId(postID);
                                post.setCreated(pc.getByID(postID).getCreated());
                                pc.update(post);
                                System.out.println();
                            } else {
                                System.out.println("Invalid input format.\n");
                            }
                        } else {
                            System.out.println("No such object in the file.\n");
                        }
                    } else {
                        System.out.println("Invalid input format.\n");
                    }
                    logic();
                }
                case 3 -> logic();
                default -> {
                    System.out.println("Non-existent menu item. Try again.\n");
                    logic();
                }
            }
        }
    }
}
