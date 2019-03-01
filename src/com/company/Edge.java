package com.company;

import java.awt.*;

public class Edge {
    private Point start;
    private Point end;
    int weight;
    Color color;

    @Override
    public String toString() {
        return "\n\t\tEdge{" +
                "start: {x=" + start.getX() + ", y=" + start.getY() + "}" +
                ", end: {x=" + end.getX() + ", y=" + end.getY() + "}" +
                ", weight=" + weight +
                "}\n";
    }

    Edge(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.weight = 1;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
