package EmailClientOOP;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class Main extends Application {
    public static Client client;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Email login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        CSVReader reader = null;
        String [] nextLine;
        try {
            reader = new CSVReader(new FileReader("configrations.csv"));
            while ((nextLine = reader.readNext()) != null)
                Client.configerations.add(new emailconfigration(nextLine[0] , nextLine[1], nextLine[2], Integer.parseInt(nextLine[3]),Integer.parseInt(nextLine[4])));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }

}
