package EmailClientOOP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

import static EmailClientOOP.Main.client;

public class MainwindowController {
    public ListView mailbox;
    public Button refresh;
    public Button logout;
    public Button compose;
    @FXML
    protected void initialize()
    {
        try
        {
            client.ReadMessages();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for (email message : client.messages)
            if(! mailbox.getItems().contains(message.getFrom() + " : " + message.getSubject()))
                this.mailbox.getItems().add(message.getFrom() + " : " + message.getSubject());
    }

    public void showmessage(MouseEvent mouseEvent) {
        String[] indexes = ((String) (mailbox.getSelectionModel().getSelectedItem())).split(" : ");
        for (email message : client.messages) {
            if (message.getFrom().equals(indexes[0]) && message.getSubject().equals(indexes[1])) {
                Alert email = new Alert(Alert.AlertType.INFORMATION);
                email.setContentText(message.getBody());
                email.setTitle(indexes[0]);
                email.setHeaderText(indexes[1]);
                email.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                email.show();
                break;
            }
        }
    }

    public void logout(MouseEvent mouseEvent) {
        Parent newstage = null;
        try {
            newstage = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene nextscene = new Scene(newstage,600,400);
            Stage window = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            window.setScene(nextscene);
            window.setTitle("Email login");
            window.show();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("logout confirmation");
            confirm.setHeaderText(client.getEmail());
            confirm.setContentText("Logout successfull");
            confirm.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compose(MouseEvent mouseEvent) {
        Parent newstage = null;
        try {
            newstage = FXMLLoader.load(getClass().getResource("compose.fxml"));
            Scene nextscene = new Scene(newstage, 600, 400);
            Stage window = new Stage();
            window.setScene(nextscene);
            window.setTitle("Compose email");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
