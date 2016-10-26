package textGenerator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorPopUp {

    public static void display(String message) {
        Stage stage = new Stage();
        Label errorLbl = new Label(message);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");

        VBox layout = new VBox();
        layout.getChildren().add(errorLbl);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 200, 50);
        stage.setScene(scene);
        stage.showAndWait();


    }

}
