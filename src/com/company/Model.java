package com.company;

import java.util.ArrayList;

class Model {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Edge> edg = new ArrayList<>();

    {
        points.add(new Point(100, 300));
        points.add(new Point(220, 120));
        points.add(new Point(220, 220));
        points.add(new Point(330, 400));
        points.add(new Point(150, 80));
        points.add(new Point(400, 150));

        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 3);
        addEdge(0, 4);
        addEdge(4, 5);
        addEdge(3, 5);
        addEdge(3, 2);
        addEdge(1, 2);
        addEdge(1, 4);
        addEdge(1, 5);
    }


    void addEdge(int start, int end) {
        Point startPoint = points.get(start);
        Point endPoint = points.get(end);
        Edge e = new Edge(startPoint, endPoint);
        edg.add(e);
    }


    void start() {
        System.out.println(edg);
        System.out.println(points);
        addEdgesToPoints();
        System.out.println("=========After===========");
        System.out.println(points);
        regularisation();

    }

    private void regularisation() {

    }

    private void addEdgesToPoints() {
        for (Edge currEdge : edg) {
            addEdgeStart(currEdge);
            addEdgeEnd(currEdge);
        }
    }

    private void addEdgeEnd(Edge currEdge) {
        int index = binaryEdgeSearch(currEdge, false);
        currEdge.getEnd().in.add(index, currEdge);
    }

    private void addEdgeStart(Edge currEdge) {
        int index = binaryEdgeSearch(currEdge, true);
        currEdge.getStart().out.add(index, currEdge);
    }

    private int binaryEdgeSearch(Edge currEdge, boolean isStart) {
        ArrayList<Edge> edges;
        int rightBorder;
        int leftBorder = 0;
        Point checkingPoint;
        if (isStart) {
            edges = currEdge.getStart().out;
            checkingPoint = currEdge.getEnd();
        } else {
            edges = currEdge.getEnd().in;
            checkingPoint = currEdge.getStart();
        }
        if (edges.isEmpty()) return 0;
        rightBorder = edges.size()-1;

        while (rightBorder - leftBorder > 1) {
            Point middle;
            if (isStart) middle = edges.get((rightBorder + leftBorder) / 2).getEnd();
            else middle = edges.get((rightBorder + leftBorder) / 2).getStart();

            if (checkingPoint.getX() < middle.getX()) {
                rightBorder = (rightBorder + leftBorder) / 2;
            } else {
                leftBorder = (rightBorder + leftBorder) / 2;
            }
        }
        Point foundPoint;
        if (isStart) foundPoint = edges.get(rightBorder).getEnd();
        else foundPoint = edges.get(rightBorder).getStart();

        if (foundPoint.getX() > checkingPoint.getX() && rightBorder - leftBorder == 1) {
            if (isStart) foundPoint = edges.get(leftBorder).getEnd();
            else foundPoint = edges.get(leftBorder).getStart();
        }

        if (foundPoint.getX() > checkingPoint.getX()) {
            return rightBorder;
        } else {
            return rightBorder + 1;
        }

    }

}
