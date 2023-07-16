package com.gorbunov.crudapp.view;

import java.util.Scanner;

public class mainView {
    private final writerView writerView = new writerView();
    private final postView postView = new postView();
    private final labelView labelView = new labelView();
    private final Scanner reader = new Scanner(System.in);

    public void Start() {
        showListOfCommands();

        String command;

        while (true) {
            command = reader.nextLine();

            switch (command) {
                case "1" -> writerView.handleRequest();
                case "2" -> postView.handleRequest();
                case "3" -> labelView.handleRequest();
                case "help" -> showListOfCommands();
                case "exit" -> {
                    System.out.println("Завершение программы");
                    return;
                }
                default -> System.out.println("Вы ввели неверную команду\n" +
                        "список команд: help");
            }

        }
    }

    private static void showListOfCommands() {
        System.out.printf("\n***Вы в главном меню***\n\n" +
                "Команды для работы с хранилищем данных:\n" +
                "%-19s 1\n" +
                "%-19s 2\n" +
                "%-19s 3\n" +
                "%-18s help\n" +
                "%-18s exit\n", "writers.json:", "posts.json:", "labels.json:", "список команд:", "завершить работу:");
    }
}



