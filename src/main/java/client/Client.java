package client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket socket;
    private static final List<Client> clients = new ArrayList<>();

    public Client(Socket socket) {
        this.socket = socket;
    }
}
