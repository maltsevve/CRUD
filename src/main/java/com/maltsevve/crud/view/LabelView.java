package com.maltsevve.crud.view;

import com.maltsevve.crud.controller.LabelController;
import com.maltsevve.crud.model.Label;

import java.util.Scanner;

class LabelView {
    private final LabelController lc = new LabelController();

    public void logic() {
        System.out.println(menu());
        String input = userInput();

        switch (Integer.parseInt(input)) {
            case 1 -> {
                System.out.println("Input name: ");
                input = userInput();
                lc.save(new Label(input));
                // Подтвердить сохранение для пользователя
                System.out.println();
                logic();
            }
            case 2 -> {
                System.out.println("Input id and a new name: 'id=name'");
                input = userInput();
                String[] strs = input.split("=");
                // Добавить проверку на наличие id и если есть, то новое ли имя?
                Label label = new Label(strs[1]);
                label.setId(Long.parseLong(strs[0]));
                lc.update(label);
                // Подтвердить удаление для пользователя
                System.out.println();
                logic();
            }
            case 3 -> {
                System.out.println("Input id: ");
                input = userInput();
                // Добавить проверку на корректность и наличие id
                Label lb = lc.getByID(Long.parseLong(input));
                System.out.println(lb.getId() + " " + lb.getName() +"\n");
                logic();
            }
            case 4 -> {
                System.out.println();
                lc.getAll().forEach((n) -> System.out.println(n.getId() + " " + n.getName()));
                System.out.println();
                logic();
            }
            case 5 -> {
                System.out.println("Input id: ");
                input = userInput();
                // Добавить проверку на корректность и наличие id
                lc.deleteById(Long.parseLong(input));
                System.out.println();
                logic();
            }
            case 6 -> {
            }
            default -> {
                System.out.println("Non-existent menu item. Try again.\n");
                logic();
            }
        }
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

    private String userInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
