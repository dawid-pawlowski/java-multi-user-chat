package app;

import client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;

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

    private static HBox createMessage(String message, boolean isMine) {
        HBox hBox = new HBox();
        Label text = new Label(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setPadding(new Insets(5, 10, 5, 10));

        if (isMine) {
            hBox.setAlignment(Pos.CENTER_RIGHT);
            text.setStyle("-fx-text-fill: rgb(239, 242, 255); ");
            textFlow.setStyle("-fx-background-color: rgb(15, 125, 242);");
        } else {
            textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);");
        }

        hBox.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);
        return hBox;
    }

    public static void addMessage(VBox container, String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                container.getChildren().add(createMessage(message, false));
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

            messageContainer.getChildren().add(createMessage(message, true));
            inputText.clear();
        });

        client.receive(messageContainer);
    }
}
