package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.events.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class ServerThread implements Runnable {

    private ServerSocket serverSocket;

    private int numberOfPlayers;

    private Socket[] clientConnections;

    private ExecutorService threadPool;

    public ServerThread(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        clientConnections = new Socket[numberOfPlayers];
        threadPool = Executors.newFixedThreadPool(numberOfPlayers);
    }

    @Override
    public void run() {

        try {

            serverSocket = new ServerSocket(Constants.PORT);
        }
        catch(IOException e) {

            e.printStackTrace();
        }

        waitClientConnections();
        startGame();
    }

    private void waitClientConnections() {

        int numberOfConnections = 0;

        while(numberOfConnections < numberOfPlayers) {

            try {

                clientConnections[numberOfConnections] = serverSocket.accept();
                threadPool.submit(new ClientDispatcher(clientConnections[numberOfConnections]));
            }
            catch(IOException e) {

            }

            numberOfConnections++;
        }
    }

    private void startGame() {

        broadcast("start");
    }

    private void broadcast(String message) {

        for(Socket s : clientConnections) {

            try {

                PrintWriter out = new PrintWriter(s.getOutputStream());

                out.write(message);
                out.flush();
            }
            catch(IOException e) {

                e.printStackTrace();
            }
        }
    }

    private class ClientDispatcher implements Runnable {

        private Socket clientConnection;

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        private ClientDispatcher(Socket clientConnection) {

            this.clientConnection = clientConnection;
        }

        @Override
        public void run() {

            while(!clientConnection.isClosed()) {

                try {

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

                    String line = in.readLine();
                    if(line != null) {

                        //TODO do not broadcast, send the message to server
                        //TODO if the msg is an event, execute it
                        //TODO if the msg is not in the proper format, discard it
                        //broadcast(line);
                        System.out.println(Event.isEvent(line));
                    }
                }
                catch(IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}