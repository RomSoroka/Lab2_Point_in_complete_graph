package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.BiFunction;

class Model {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Edge> edg = new ArrayList<>();
    ArrayList<Chain> chains = new ArrayList<>();

    void addEdge(int start, int end) {
        Point startPoint = points.get(start);
        Point endPoint = points.get(end);
        Edge e = new Edge(startPoint, endPoint);
        edg.add(e);
        addEdgeStart(e);
        addEdgeEnd(e);
    }

    void addEdge(Point startPoint, Point endPoint) {
        Edge e = new Edge(startPoint, endPoint);
        edg.add(e);
        addEdgeStart(e);
        addEdgeEnd(e);
    }

    void start() {
        regularisation();
        weightBalancing();
        createChains();
        System.out.println(chains);
    }

    private void createChains() {
        Point startingPoint = points.get(0);
        Edge rightmostEdge = startingPoint.out.get(startingPoint.out.size() - 1);
        while (rightmostEdge.weight != 0) {
            Chain currChain = new Chain();
            Point currPoint = points.get(0);
            while (currPoint != points.get(points.size() - 1)) {
                int i = 0;
                while (i < currPoint.out.size()) {
                    Edge currEdge = currPoint.out.get(i);
                    if (currEdge.weight > 0) {
                        currChain.edges.add(currEdge);
                        currEdge.weight--;
                        currPoint = currEdge.getEnd();
                        break;
                    } else {
                        i++;
                    }
                }
            }
            chains.add(currChain);
        }
    }

    private void weightBalancing() {
        //Top to bottom
        for (int i = 1; i < points.size() - 1; i++) {
            Point currentPoint = points.get(i);
            int wIN = currentPoint.in.stream().mapToInt(edg -> edg.weight).sum();
            int vOUT = currentPoint.out.size();
            if (wIN > vOUT) {
                currentPoint.out.get(0).weight = wIN - vOUT + 1;
            }
        }

        //Bottom to top
        for (int i = points.size() - 2; i > 0; i--) {
            Point currentPoint = points.get(i);
            int wOUT = currentPoint.out.stream().mapToInt(edg -> edg.weight).sum();
            int wIN = currentPoint.in.stream().mapToInt(edg -> edg.weight).sum();
            if (wOUT > wIN) {
                currentPoint.in.get(0).weight = wOUT - wIN + currentPoint.in.get(0).weight;
            }

        }
    }

    private void regularisation() {
        forwardMove();
        backwardMove();
    }

    private void backwardMove() {
        ArrayList<Edge> status = new ArrayList<>();
        status.addAll(points.get(points.size() - 1).in);
        for (int i = points.size() - 2; i >= 0; i--) {
            Point currPoint = points.get(i);
            int index = binaryPointSearch(currPoint, status, this::rightSideCheck);
            if (currPoint.out.isEmpty()) {
                addEdge(currPoint, closestTopPoint(status, index));
            }
            for (Edge e : currPoint.in)
                status.add(index, e);
            for (Edge e : currPoint.out)
                status.remove(e);
        }
    }

    private void forwardMove() {
        ArrayList<Edge> status = new ArrayList<>();
        status.addAll(points.get(0).out);
        for (int i = 1; i < points.size(); i++) {
            Point currPoint = points.get(i);
            int index = binaryPointSearch(currPoint, status, this::rightSideCheck);
            if (currPoint.in.isEmpty()) {
                addEdge(closestBotPoint(status, index), currPoint);
            }
            for (Edge e : currPoint.out)
                status.add(index, e);
            for (Edge e : currPoint.in)
                status.remove(e);
        }
    }

    private Point closestBotPoint(ArrayList<Edge> status, int index) {
        Point leftPoint, rightPoint;
        if (index == 0) return status.get(index).getStart();
        if (index == status.size()) return status.get(index - 1).getStart();
        leftPoint = status.get(index - 1).getStart();
        rightPoint = status.get(index).getStart();
        if (rightPoint.getY() < leftPoint.getY())
            return leftPoint;
        else
            return rightPoint;
    }

    private Point closestTopPoint(ArrayList<Edge> status, int index) {
        Point leftPoint, rightPoint;
        if (index == 0) return status.get(index).getEnd();
        if (index == status.size()) return status.get(index - 1).getEnd();
        leftPoint = status.get(index - 1).getEnd();
        rightPoint = status.get(index).getEnd();
        if (rightPoint.getY() > leftPoint.getY())
            return leftPoint;
        else
            return rightPoint;

    }


    private void addEdgeEnd(Edge currEdge) {
        int index = binaryPointSearch(middlePoint(currEdge), currEdge.getEnd().in, this::rightSideCheck);
        currEdge.getEnd().in.add(index, currEdge);
    }

    private void addEdgeStart(Edge currEdge) {
        int index = binaryPointSearch(middlePoint(currEdge), currEdge.getStart().out, this::rightSideCheck);
        currEdge.getStart().out.add(index, currEdge);
    }

    private Point middlePoint(Edge currEdge) {
        int pointX = (currEdge.getEnd().getX() + currEdge.getStart().getX()) / 2;
        int pointY = (currEdge.getStart().getY() + currEdge.getEnd().getY()) / 2;
        return new Point(pointX, pointY);
    }

    void localisation(Point chillingPoint) {
        System.out.println("Entry");
        int rightBorder;
        int leftBorder = 0;
        if (chains.isEmpty()) return;
        rightBorder = chains.size() - 1;
        while (rightBorder - leftBorder > 1) {
            boolean isAlRight;
            Chain middle = chains.get((rightBorder + leftBorder) / 2);
            isAlRight = chainsRightCheck(chillingPoint, middle);
            if (isAlRight) {
                for (Edge tempEdge : middle.edges)
                    tempEdge.color = Color.RED;
                leftBorder = (rightBorder + leftBorder) / 2;
            } else {
                for (Edge tempEdge : middle.edges)
                    tempEdge.color = Color.GREEN;
                rightBorder = (rightBorder + leftBorder) / 2;
            }
        }
        if (leftBorder == 0) {
            lastPerforms(chillingPoint, leftBorder);
        }
        if (rightBorder == chains.size() - 1) {
            lastPerforms(chillingPoint, rightBorder);
        }
        System.out.println("Exit");
    }

    private void lastPerforms(Point chillingPoint, int leftBorder) {
        Chain left = chains.get(leftBorder);
        if (chainsRightCheck(chillingPoint, left)) {
            for (Edge tempEdge : left.edges)
                tempEdge.color = Color.RED;
        } else {
            for (Edge tempEdge : left.edges)
                tempEdge.color = Color.GREEN;
        }
    }

    private boolean chainsRightCheck(Point chillingPoint, Chain middle) {
        boolean isAlRight;
        int edgeNum = binaryPointSearch(chillingPoint, middle.edges, this::topCheck);
        if (edgeNum == middle.edges.size())
            isAlRight = middle.edges.get(middle.edges.size() - 1).getEnd().getX() < chillingPoint.getX();
        else {
            Edge currEdge = middle.edges.get(edgeNum);
            if (edgeNum == 0 && currEdge.getStart().getY() > chillingPoint.getY()) {
                isAlRight = currEdge.getStart().getX() < chillingPoint.getX();
            } else
                isAlRight = rightSideCheck(chillingPoint, currEdge);
        }
        return isAlRight;
    }

    private int binaryPointSearch(Point checkingPoint, ArrayList<Edge> edges, BiFunction<Point, Edge, Boolean> comp) {

        int rightBorder;
        int leftBorder = 0;
        if (edges.isEmpty()) return 0;
        rightBorder = edges.size() - 1;

        while (rightBorder - leftBorder > 1) {
            Edge middle;
            middle = edges.get((rightBorder + leftBorder) / 2);
            if (comp.apply(checkingPoint, middle))
                leftBorder = (rightBorder + leftBorder) / 2;
            else
                rightBorder = (rightBorder + leftBorder) / 2;
        }

        Edge rightEdge = edges.get(rightBorder);

        if (comp.apply(checkingPoint, rightEdge))
            return rightBorder + 1;                                 // size() check endpoint
        else {
            Edge leftEdge = edges.get(leftBorder);
            if (comp.apply(checkingPoint, leftEdge))
                return leftBorder + 1;
            else
                return leftBorder;
        }


    }

    private boolean topCheck(Point checkingPoint, Edge edge) {
        Point startPoint = edge.getStart();
        Point endPoint = edge.getEnd();
        if (checkingPoint.getY() > endPoint.getY()) return true;
        return false;
    }

    private boolean rightSideCheck(Point checkingPoint, Edge edge) {
        Point startPoint = edge.getStart();
        Point endPoint = edge.getEnd();
        return ((endPoint.getX() - startPoint.getX()) * (checkingPoint.getY() - startPoint.getY())
                / (endPoint.getY() - startPoint.getY()) + startPoint.getX()) < checkingPoint.getX();
    }

}
