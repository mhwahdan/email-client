package EmailClientOOP;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static EmailClientOOP.Main.client;

public class loginController {
    public PasswordField password;
    public TextField email;
    public Label isloding;
    public void login(ActionEvent event)
    {
        try
        {
            client = new Client(email.getText(),password.getText());
            if (client.is_authenticated())
            {
                Alert error = new Alert(AlertType.ERROR);
                error.setContentText("Error logging in");
                error.show();
            }
            else
            {
                isloding.setVisible(true);
                Alert error = new Alert(AlertType.CONFIRMATION);
                error.setContentText("Login Successfull");
                error.showAndWait();
                Parent newstage = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
                Scene nextscene = new Scene(newstage,1000,600);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setTitle(client.getEmail());
                window.setScene(nextscene);
                window.show();
            }
        }
        catch (Exception e)
        {
            Alert error = new Alert(AlertType.ERROR);
            error.setContentText("App has crashed");
            error.showAndWait();
        }
    }


}
