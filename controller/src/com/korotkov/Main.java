package com.korotkov;

import com.korotkov.chrome.Controller;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final Controller controller = new Controller(5239);
        controller.start();
        System.out.println("started!");
        final Scanner in = new Scanner(System.in);
        while (true) {
            final String msg = in.next();
            if ("exit".equals(msg)) {
                break;
            }
            if (msg.matches("\\d+")) {
                controller.removeTab(Integer.parseInt(msg));
            } else {
                controller.createTab(msg);
            }
        }
    }
}
