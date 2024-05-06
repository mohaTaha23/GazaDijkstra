package com.example.gazadijkstra;

import javafx.geometry.Point2D;

import java.util.*;

import java.util.HashSet;

public class Graph {

    private Set<Node> nodes = new HashSet<>();
    private ArrayList<Street> streets = new ArrayList<>();

    private static final double XMin = 34.08389;
    private static final double XMax = 34.64570;
    private static final double YMin = 31.60199;
    private static final double YMax = 31.22404;

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
    // getters and setters


    public Node find(String name){
        for (Node needed : nodes){
            if (needed.getName().equals(name))
                return needed;
        }
        return null;
    }
    public ArrayList<Street> getStreets(){
        return streets;
    }
    public  Street findStreet(String name){
        for (Street needed : streets){
            if (needed.getName().equals(name))
                return needed;
        }
        return null;
    }
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> visitedNodes = new HashSet<>();
        Set<Node> unknownNodes = new HashSet<>();

        unknownNodes.add(source);

        while (unknownNodes.size() != 0) {
            Node currentNode = getLowestCostNode(unknownNodes);
            unknownNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {

                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!visitedNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unknownNodes.add(adjacentNode);
                }
            }
            visitedNodes.add(currentNode);
        }
        return graph;
    }
    private static Node getLowestCostNode(Set <Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            evaluationNode.setPrevious(sourceNode);
        }
    }
    public static Point2D convert(double y , double x){
        double xPoint = 1010*((x -XMin)/(XMax-XMin));                // screen size
        double yPoint = 835*((y-YMin)/(YMax-YMin));
        return new Point2D(xPoint,yPoint);
    }
}
