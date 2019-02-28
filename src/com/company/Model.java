package com.company;

import java.util.ArrayList;

public class Model {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Edge> edg = new ArrayList<>();

    void addEdge(int start, int end) {
        Point startPoint = points.get(start);
        Point endPoint = points.get(end);
        Edge e = new Edge(startPoint, endPoint);
        edg.add(e);
    }

    public void start() {
        edgePositioning();
        regularisation();
    }

    private void regularisation() {
    }

    private void edgePositioning() {
        for (Edge currEdge : edg) {
            addEdgeStart(currEdge);
            addEdgeEnd(currEdge);
        }
    }

    private void addEdgeEnd(Edge currEdge) {
        int index = binaryEdgeSearch(currEdge, false);
        currEdge.getStart().in.add(index, currEdge);
    }

    private void addEdgeStart(Edge currEdge) {
        int index = binaryEdgeSearch(currEdge, true);
        currEdge.getStart().out.add(index, currEdge);
    }

    private int binaryEdgeSearch(Edge currEdge, boolean isStart) {
        ArrayList<Edge> edges;
        int rightBorder;
        int leftBorder = 0;
        Point chechingPoint;
        if (isStart) {
            edges = currEdge.getStart().out;
            chechingPoint = currEdge.getEnd();
        } else {
            edges = currEdge.getEnd().in;
            chechingPoint = currEdge.getStart();
        }
        rightBorder = edges.size();

        if (edges.isEmpty()) return 0;
        else {
            while (rightBorder != leftBorder) {
                Point middle;
                if (isStart) middle = edges.get((rightBorder + leftBorder) / 2).getEnd();
                else middle = edges.get((rightBorder + leftBorder) / 2).getStart();
                if (middle.getX() > chechingPoint.getX()) {
                    rightBorder = (rightBorder + leftBorder) / 2;
                } else {
                    leftBorder = (rightBorder + leftBorder) / 2;
                }
            }
            Point foundPoint;
            if (isStart) {
              foundPoint=edges.get(rightBorder).getEnd();
            }
            else
            {
                foundPoint=edges.get(rightBorder).getStart();
            }
            if(foundPoint.getX()>chechingPoint.getX())
            {
                return rightBorder;
            }
            else {
                return rightBorder+1;
            }

        }
    }
}
