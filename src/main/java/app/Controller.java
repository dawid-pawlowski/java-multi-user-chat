package app;

import client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;

public class Controller {
    private Client client;
    @FXML
    private VBox messageContainer;
    @FXML
    private Button sendButton;
    @FXML
    private TextField inputText;
    @FXML
    private ScrollPane scrollPane;

    private static HBox createMessage(String message) {
        HBox hBox = new HBox();
        //hBox.setAlignment(isMine ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        hBox.getChildren().add(new TextFlow(new Text(message)));
        return hBox;
    }

    public static void addMessage(VBox container, String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                container.getChildren().add(createMessage(message));
            }
        });
    }

    @FXML
    public void initialize() throws IOException {
        client = new Client();
        messageContainer.heightProperty().addListener((observableValue, number, t1) -> scrollPane.setVvalue((Double) t1));

        sendButton.setOnAction(actionEvent -> {
            String message = inputText.getText();

            if (message.isEmpty()) return;

            try {
                client.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

            messageContainer.getChildren().add(createMessage(message));
            inputText.clear();
        });

        client.receive(messageContainer);
    }
}
