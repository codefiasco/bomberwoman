package org.academiadecodigo.bomberwoman;

import java.util.ResourceBundle;

/**
 * Created by codecadet on 06/11/17.
 */
public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
        //new Thread(new ServerThread(1)).start();
    }
}