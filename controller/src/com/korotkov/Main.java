package com.korotkov;

import com.korotkov.chrome.Controller;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final Controller controller = Controller.accept(5239);
        controller.handShake();
        System.out.println("accepted!");
        new Thread(controller).start();
        final Scanner in = new Scanner(System.in);
        while (true) {
            final String msg = in.next();
            if ("exit".equals(msg)) {
                break;
            }
            controller.sendUrl(msg);
        }
    }
}
