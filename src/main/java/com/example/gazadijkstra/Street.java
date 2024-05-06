package com.example.gazadijkstra;

import javafx.geometry.Point2D;

import java.util.*;

public class Street {
    private double cost =-1;

    private String name ;

    private ArrayList<Point2D> points;
    Street(){
        points = new ArrayList<>();
    }

    Street(String name){
        points = new ArrayList<>();
        this.name =name;
    }

    public void addPoint(double x,double y){
        Point2D point2D = new Point2D(x,y);
        points.add(point2D);
        if (points.size()==2){
            Point2D point2D1 = points.get(0);
            Point2D point2D2 = points.get(1);
            cost=calc(point2D1,point2D2);
        }
        else if (points.size()>2){
            Point2D point2D1 = point2D;
            Point2D point2D2 = points.get(points.size()-2);
            cost+=calc(point2D1,point2D2);
        }
    }

    public String getName() {
        return name;
    }

    public double getCost(){
        return cost;
    }

    public ArrayList<Point2D> getPoints() {
        return points;
    }

    static double calc(Point2D point1 , Point2D point2){
        double x = (point1.getX()-point2.getX())*110000;
        double y = (point1.getY()-point2.getY())*94900;
        return Math.pow(Math.pow(x,2)+Math.pow(y,2),0.5);
    }

    public void setPoints(ArrayList<Point2D> forStreet) {
        this.points=points;
    }
    public void sort(Point2D p1){
        HashMap<Point2D,Double> pointsMap = new HashMap<>();
        for (Point2D point2D : points){
            double cost = Street.calc(p1,point2D);
            pointsMap.put(point2D,cost);
        }
        List<Map.Entry<Point2D, Double>> entryList = new ArrayList<>(pointsMap.entrySet());

        Collections.sort(entryList, Comparator.comparing(Map.Entry::getValue));
        points.clear();
        for (Map.Entry<Point2D, Double> entry : entryList) {
            points.add(entry.getKey());
        }

//        while (!points.isEmpty()){
//            double x = Double.MAX_VALUE;
//            Point2D toReturn = null;
//            for (Map.Entry<Point2D,Double> k : points.entrySet()){
//                if (k.getValue()<x){
//                    toReturn = k.getKey();
//                    x = k.getValue();
//                }
//            }
//            drawBetweenPoints();
//            points.remove(toReturn);
//        }

    }
}
