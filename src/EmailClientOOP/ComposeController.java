package EmailClientOOP;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import static EmailClientOOP.Main.client;
public class ComposeController {
    public TextArea body;
    public TextField receiveremail;
    public TextField subject;
    public void send(MouseEvent mouseEvent) {
        String receiveremail = this.receiveremail.getText();
        String subject = this.subject.getText();
        String body =  this.body.getText();
        if(client.Send(receiveremail,subject,body))
        {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle(client.getEmail());
            confirm.setHeaderText("email confirmation");
            confirm.setContentText("email sent successfully to " + receiveremail);
            confirm.showAndWait();
        }
        else
        {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle(client.getEmail());
            error.setHeaderText("Error");
            error.setContentText("Failed to send email to " + receiveremail);
            error.showAndWait();
        }
    }
}
