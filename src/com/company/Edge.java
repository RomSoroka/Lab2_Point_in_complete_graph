package com.company;

import java.awt.*;

public class Edge {
    private Point start;
    private Point end;
    int weight;
    Color color = Color.ORANGE;

    @Override
    public String toString() {
        return "\n\t\tEdge{" +
                "from: " + start.arrayNumber + " to: " + end.arrayNumber +
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
