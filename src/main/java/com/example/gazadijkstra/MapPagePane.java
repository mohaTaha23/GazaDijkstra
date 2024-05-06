package com.example.gazadijkstra;

import javafx.collections.FXCollections;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MapPagePane extends Pane {

    Graph graph = new Graph();
    ComboBox<String> sourceBox = new ComboBox<>();
    ComboBox<String> distBox = new ComboBox<>();
    Button cook = new Button("Find the way");

    Pane imagePane = new Pane();

    TextArea textArea = new TextArea();
    public MapPagePane(File file){
        this.setPrefSize(1412,900);
        render();
        makeList(file);
    }

    public void render(){       // add front end to the pane
        Image image = new Image(getClass().getResource("/images/mapdone.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(840);
        imageView.setFitWidth(1012);
        imageView.setOnMouseClicked(e->{
            System.out.println("x="+e.getX()+"  y="+e.getY());
        });

        imagePane.setPrefSize(1012,840);
        imagePane.getChildren().add(imageView);

        sourceBox.setLayoutX(1212);
        sourceBox.setLayoutY(200);

        sourceBox.setPrefSize(128,64);
        distBox.setLayoutX(1212);
        distBox.setLayoutY(300);
        distBox.setPrefSize(128,64);

        cook.setPrefSize(128,64);
        cook.setLayoutY(420);
        cook.setLayoutX(1212);

        textArea.setPrefSize(300,200);
        textArea.setLayoutX(1132);
        textArea.setLayoutY(500);
        textArea.setEditable(false);

        Label src = new Label("Source: ");
        src.setLayoutX(1100);
        src.setLayoutY(220);
        Label dist = new Label("Destination: ");
        dist.setLayoutX(1100);
        dist.setLayoutY(320);
        this.getStyleClass().add("styled-pane");
        this.getChildren().addAll(imagePane,sourceBox,distBox,cook,src,dist,textArea);
        cook.setOnAction(e->{
            Node source = graph.find(sourceBox.getValue());
            Node diste = graph.find(distBox.getValue());
            if (source!= null&& diste != null){
                kitchen();
            }
            textArea.clear();
            while (diste!=null){
                if (!diste.getName().startsWith("mafraq"))
                    textArea.appendText(diste.getName()+"\n");
                diste=diste.getPrevious();
            }
        });
    }


    private void makeList(File file){
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()){
                String input = reader.nextLine();
                System.out.println(input);
                if (!input.contains("|")){                      // then its city/coord or city/adj
                    if (!input.contains(",")) continue;
                    String [] tkz = input.split(",");
                    try {
                        double x = Double.parseDouble(tkz[1].trim());
                        double y = Double.parseDouble(tkz[2].trim());
                        Node city = new Node(tkz[0].trim(),x,y);
                        graph.getNodes().add(city);
                    }
                    catch (NumberFormatException e) {
                        //tkz[0] and tkz[1] are cities , tkz[2] is the street name
                        Node nodeA =  graph.find(tkz[0].trim());
                        Node nodeB = graph.find(tkz[1].trim());
                        Point2D A = new Point2D(nodeA.getX(),nodeA.getY());
                        Point2D B = new Point2D(nodeB.getX(),nodeB.getY());
                        nodeA.addDestination(nodeB,(int)Street.calc(A,B));
                        nodeB.addDestination(nodeA,(int)Street.calc(A,B));
                        if (tkz.length > 2) {
                            nodeA.getWay().put(graph.find(tkz[1].trim()),tkz[2].trim());
                            nodeB.getWay().put(graph.find(tkz[0].trim()),tkz[2].trim());
                        }
                        else {
                            nodeA.getWay().put(graph.find(tkz[1].trim())," ");
                            nodeB.getWay().put(graph.find(tkz[0].trim())," ");
                        }
                    }
                }
                else {
                    String[] tkz = input.split("\\|");
                    Street street = new Street(tkz[0]);
                    for (int i=1;i<tkz.length;i++){
                        double x = Double.parseDouble(tkz[i].split(",")[0]);
                        double y = Double.parseDouble(tkz[i].split(",")[1]);
                        street.addPoint(x,y);
                    }
                    graph.getStreets().add(street);
                }
            }
        }
        catch (FileNotFoundException f){
            Alert alert = new Alert(Alert.AlertType.ERROR,"file isnt right, try again");
        }
        for (Node city:graph.getNodes()){
            if (!city.getName().startsWith("mafraq")) {
                sourceBox.getItems().add(city.getName());
                distBox.getItems().add(city.getName());
                Point2D p = Graph.convert(city.getX(),city.getY());
                Circle circle = new Circle(p.getX(),p.getY(),5, Color.RED);
                Label label = new Label(city.getName());
                label.getStyleClass().add("city-label");
                label.setLayoutX(p.getX());
                label.setLayoutY(p.getY());
                System.out.println(label.getLayoutX()+"=="+label.getLayoutY());
                imagePane.getChildren().addAll(circle,label);
            }
        }
        FXCollections.sort(sourceBox.getItems());
        FXCollections.sort(distBox.getItems());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,sourceBox.getItems().size()+" cities found");
        alert.show();
    }
    private void kitchen() {
        clearLines();
        sourceBox.setEditable(false);
        Node source = graph.find(sourceBox.getValue());
        Node dist = graph.find(distBox.getValue());
        graph=Graph.calculateShortestPathFromSource(graph,source);
        source = graph.find(sourceBox.getValue());
        dist = graph.find(distBox.getValue());
        if (dist == source) return;             // check if they are not the same
        if (dist != null && source != null){    // test Case :P
            while (dist!=source){               // loop thru!
                Node nextNode =dist.getPrevious();
                String streetName = dist.getWay().get(nextNode);
                Street street = graph.findStreet(streetName);
                if (street == null || street.getName().equals(" ")){    // now either there is street between the cities , or they are straight
                    drawCity(dist,nextNode);
                }
                else{
                    drawStreet(dist,street,nextNode);
                }
                System.out.println(dist.getPrevious().getName());
                if (dist.getPrevious() == null){
                    System.out.println("here?");
                    drawTriangle(dist);
                }
                dist=dist.getPrevious();
            }
            drawTriangle(dist);
        }
        for (Object o:imagePane.getChildren()){
            if (o instanceof Line){
                ((Line) o).getStyleClass().add("styled-line");
            }
        }
    }

    private void clearLines() {
        ArrayList<Object> a = new ArrayList<>(imagePane.getChildren());
        for (Object o : a){
            if (o instanceof Line){
                imagePane.getChildren().remove(o);
            }
        }
    }

    private void drawCity(Node dist,Node src){
        Point2D firstPoint = Graph.convert(dist.getX(),dist.getY());
        Point2D secondPoint = Graph.convert(src.getX(),src.getY());
        Line line = new Line(firstPoint.getX(),firstPoint.getY(),secondPoint.getX(),secondPoint.getY());
        imagePane.getChildren().add(line);
    }
    private void drawStreet(Node dist, Street street,Node src){
        street.sort(new Point2D(dist.getX(),dist.getY()));
        Point2D firstPoint = Graph.convert(dist.getX(),dist.getY());
        Point2D sec = Graph.convert(street.getPoints().get(0).getX(),street.getPoints().get(0).getY());
        Line line = new Line(firstPoint.getX(),firstPoint.getY(),sec.getX(),sec.getY());
        imagePane.getChildren().add(line);      // draw between first node and the street
        if (street.getPoints().size()>1){
            for (int i = 1; i< street.getPoints().size();i++){
                Point2D p = street.getPoints().get(i);
                Point2D temp = Graph.convert(p.getX(),p.getY());
                imagePane.getChildren().add(new Line(sec.getX(),sec.getY(),temp.getX(),temp.getY()));
                sec = temp;
            }
        }
        imagePane.getChildren().add(new Line(sec.getX(),sec.getY(),Graph.convert(src.getX(),src.getY()).getX(),Graph.convert(src.getX(),src.getY()).getY()));
    }


    private void drawTriangle(Node dist) {

        Point2D point = Graph.convert(dist.getX(),dist.getY());
        double x = point.getX();
        double y = point.getY();
        Polygon tringle = new Polygon();
        System.out.println(x +"=>>>>>>>>" + y);
        tringle.getPoints().setAll(
                x-5,y-5,
                x+20,y,
                x,y+20
        );
        tringle.setFill(Color.GREEN);
        tringle.getStyleClass().add("Triangle");
        imagePane.getChildren().add(tringle);
    }

}
