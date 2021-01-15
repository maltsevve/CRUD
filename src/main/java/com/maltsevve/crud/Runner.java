package com.maltsevve.crud;

import com.maltsevve.crud.view.LabelView;
import com.maltsevve.crud.view.PostView;
import com.maltsevve.crud.view.WriterView;

import java.util.Scanner;

public class Runner {
    static Scanner sc = new Scanner(System.in);
    static WriterView wv = new WriterView();
    static PostView pv = new PostView();
    static LabelView lv = new LabelView();

    public static void main(String[] args) {
        mainMenuLogic();
    }

    public static String mainMenu() {
        return ("'MENU'\n" +
                "Select menu item:\n" +
                "1 - Labels\n" +
                "2 - Posts\n" +
                "3 - Writers\n" +
                "4 - Exit");
    }

    public static void mainMenuLogic() {
        System.out.println(mainMenu());
        String input = sc.nextLine();
        if (input.matches("\\d+")) {
            switch (Integer.parseInt(input)) {
                case 1 -> lv.logic();
                case 2 -> pv.logic();
                case 3 -> wv.logic();
                case 4 -> {}
                default -> {
                    System.out.println("Non-existent menu item. Try again.\n");
                    mainMenuLogic();
                }
            }
        } else {
            System.out.println("Use digits from 1 to 4.\n");
            mainMenuLogic();
        }
    }
}
