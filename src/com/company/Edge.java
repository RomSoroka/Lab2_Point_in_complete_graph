package com.company;

public class Edge {
    private Point start;
    private Point end;
    int weight;

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
