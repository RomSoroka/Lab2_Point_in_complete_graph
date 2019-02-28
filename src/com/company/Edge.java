package com.company;

import java.awt.*;

public class Edge {
    private Point start;
    private Point end;
    int weight;
    Color color;

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
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
