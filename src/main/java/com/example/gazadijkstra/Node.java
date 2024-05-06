package com.example.gazadijkstra;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private String name;
    private HashMap<Node ,String> way = new HashMap<Node,String>();  // node is the adj city, String is street name

    private Node previous = null;

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    private double x;
    private double y;

    public Node(){}
    public Node(String name) {
        this.name = name;
    }

    public Node(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public HashMap<Node, String> getWay() {
        return way;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWay(HashMap<Node, String> way) {
        this.way = way;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
}
