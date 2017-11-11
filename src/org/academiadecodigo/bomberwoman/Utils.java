package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.threads.ServerThread;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by codecadet on 06/11/17.
 */
public class Utils {

    public static int wrapAround(int value, int min, int max) {

        if(value > max) {

            value = min;
        }

        if(value < min) {

            value = max;
        }

        return value;
    }

    public static int clamp(int value, int min, int max) {

        if(value < min) {

            value = min;
        }

        if(value > max) {

            value = max;
        }

        return value;
    }

    public static void rawMode() {

        execute("/bin/sh", "-c", "stty raw </dev/tty");
    }

    public static void bufferedMode() {

        execute("/bin/sh", "-c", "stty cooked </dev/tty");
    }

    public static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void execute(String... cmd) {

        try {

            Runtime.getRuntime().exec(cmd).waitFor();
        }
        catch(InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumber(String text) {

        return text.matches("-?\\d+(\\.\\d+)?");
    }

    public static void quitGame() {

        bufferedMode();
        clearScreen();
        System.exit(0);
    }

    public static void hostAndConnect(int numbersOfPlayers) {

        Game.getInstance().submitTask(new ServerThread(numbersOfPlayers));
        Game.getInstance().connectTo("localhost");
    }
}