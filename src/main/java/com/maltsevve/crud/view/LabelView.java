package com.maltsevve.crud.view;

import com.maltsevve.crud.controller.LabelController;
import com.maltsevve.crud.model.Label;

import java.util.List;
import java.util.Scanner;

class LabelView {
    private final LabelController lc = new LabelController();
    Scanner sc = new Scanner(System.in);

    LabelView() {

    }

    private String menu() {
        return ("Select menu item:\n" +
                "1 - Save\n" +
                "2 - Update\n" +
                "3 - Get\n" +
                "4 - Get all\n" +
                "5 - Delete\n" +
                "6 - Exit");
    }

    public void logic() {
        System.out.println(menu());
        String input = sc.nextLine();

        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> { // SAVE
                    System.out.println("Input name: ");
                    input = sc.nextLine();
                    // Возможно требуется проверка. Уточнить существуют ли недопустимые имена.
                    lc.save(new Label(input));
                    System.out.println();
                    logic();
                }
                case 2 -> { // UPDATE
                    System.out.println("Input id and a new name: 'id=name'");
                    String[] strs = sc.nextLine().split("=");

                    if (strs.length == 2 && strs[0].matches("\\d+")) {
                        List<Label> labels = lc.getAll();
                        if (labels.stream().anyMatch(l -> l.getId() == (Long.parseLong(strs[0])))) {
                            // Следующая проверка для случаев когда файл огромен, а пользователь,
                            // по невнимательности, попытается изменить имя на идентичное существующему
                            if (Integer.parseInt(strs[0]) > 0 && !labels.get(Integer.
                                    parseInt(strs[0]) - 1).getName().equals(strs[1])) {
                                Label label = new Label(strs[1]);
                                label.setId(Long.parseLong(strs[0]));
                                lc.update(label);
                                System.out.println();
                            } else {
                                System.out.println("The entered name is identical to the existing one.\n");
                            }
                        } else {
                            System.out.println("No such object in the file.\n");
                        }
                    } else {
                        System.out.println("Invalid input format.\n");
                    }

                    logic();
                }
                case 3 -> { // GET BY ID
                    System.out.println("Input id: ");
                    input = sc.nextLine();
                    if (input.matches("\\d+")) {
                        Label lb = lc.getByID(Long.parseLong(input));
                        if (lb != null) {
                            System.out.println(lb.getId() + " " + lb.getName() + "\n");
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
                    if (!lc.getAll().isEmpty()) {
                        lc.getAll().forEach((n) -> System.out.println(n.getId() + " " + n.getName()));
                        System.out.println();
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
                        if (l > 0 && lc.getAll().stream().anyMatch(lb -> lb.getId() == l)) {
                            lc.deleteById(l);
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
                }
                default -> {
                    System.out.println("Non-existent menu item. Try again.\n");
                    logic();
                }
            }
        } else {
            System.out.println("Use digits from 1 to 6.\n");
            logic();
        }
    }
}
