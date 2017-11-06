package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.threads.InputThread;
import org.academiadecodigo.bomberwoman.threads.LogicThread;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;
import org.academiadecodigo.bomberwoman.threads.RenderThread;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class Game {

    public static int WIDTH = 120;

    public static int HEIGHT = 30;

    private final Vector<GameObject> gameObjects = new Vector<>();

    void start() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int timeToDraw = 100;
        Utils.rawMode();
        WIDTH = 50;
        HEIGHT = 10;
        executorService.submit(new RenderThread(WIDTH, HEIGHT, timeToDraw));
        executorService.submit(new NetworkThread());
        executorService.submit(new InputThread());
        executorService.submit(new LogicThread());
    }
}