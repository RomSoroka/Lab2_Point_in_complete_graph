package com.company;

import java.util.ArrayList;

public class Model {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Edge> edg = new ArrayList<>();

    void addEdge(int start, int end) {
        Point startPoint =  points.get(start);
        Point endPoint = points.get(end);
        Edge e = new Edge(startPoint,endPoint);
        edg.add(e);
    }

}
