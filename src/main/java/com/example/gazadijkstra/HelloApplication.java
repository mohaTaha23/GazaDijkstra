package com.example.gazadijkstra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {         // test things, run MainPage
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane pane = new Pane();
        Scene scene = new Scene(pane,1012,840);
        Point2D point2D = new Point2D(31.538957575955067, 34.539774017633604);
        Point2D point2D1 = new Point2D(31.551689969776984, 34.50903688098947);
        Point2D p1 = Graph.convert(point2D.getX(), point2D.getY());
        Point2D p2 = Graph.convert(point2D1.getX(), point2D1.getY());
        System.out.println(p1.getX()+" = " +p1.getY());
        pane.getChildren().add(new Line(p1.getX(),p1.getY(),p2.getX(),p2.getY()));
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}