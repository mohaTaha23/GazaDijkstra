module com.example.gazadijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gazadijkstra to javafx.fxml;
    exports com.example.gazadijkstra;
}