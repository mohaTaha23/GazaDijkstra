package com.example.gazadijkstra;

import javafx.geometry.Point2D;
import javafx.stage.Window;

public class Helper {

    public static void main(String[] args) {
        Point2D point2D = new Point2D(31.509686333676886, 34.457963852411666);
        Point2D point2D1 = new Point2D(31.510177016731564, 34.43666858360017);
        System.out.println(1012*(point2D.getX()-0));
    }
    static double calc(Point2D point1 , Point2D point2){
        double x = (point1.getX()-point2.getX())*110000;
        double y = (point1.getY()-point2.getY())*94900;
        return Math.pow(Math.pow(x,2)+Math.pow(y,2),0.5);
    }
}
