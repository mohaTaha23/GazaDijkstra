package com.example.gazadijkstra;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        Image image = new Image(getClass().getResource("/images/justice.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(600);
        imageView.setFitWidth(1200);
        Button chooseFile = new Button("Hit File");
        chooseFile.setLayoutX(140);
        chooseFile.setLayoutY(270);
        chooseFile.setPrefSize(120,60);

        chooseFile.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            String directory = System.getProperty("user.dir") +"\\src\\main\\java";
            fileChooser.setInitialDirectory(new File(directory));
            System.out.println(directory);
            File chosen = fileChooser.showOpenDialog(new Stage());
            if (chosen!=null && chosen.getName().endsWith(".txt")){
                MapPagePane mapPagePane = new MapPagePane(chosen);
                pane.getChildren().clear();
                Scene scene2 = new Scene(mapPagePane,1412,900);
                scene2.getStylesheets().add(this.getClass().getResource("style2.css").toExternalForm());
                stage.setScene(scene2);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"File is not found");
                alert.show();
            }
        });
        pane.getChildren().addAll(imageView,chooseFile);
        Scene scene = new Scene(pane,1200,600);
        stage.setScene(scene);
        stage.setTitle("Gaza");
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        stage.show();
    }
}
