package client;

import app.Controller;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public Client() throws IOException {
        socket = new Socket("localhost", 1234);
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void receive(VBox container) {
        Thread thread = new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    Controller.addMessage(container, bufferedReader.readLine());
                } catch (IOException e) {
                    closeConnection();
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void closeConnection() {
        try {
            if (socket != null) socket.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (bufferedReader != null) bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
