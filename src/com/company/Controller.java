package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    private Model model;

    void addPoint(Point p) {
        model.points.add(p);
    }

    void sortPoints() {
        Collections.sort(model.points);
        for (int i = 0; i < model.points.size(); i++ ) {
            model.points.get(i).arrayNumber = i;
        }
    }

    public ArrayList<Point> getPoints() {
        return model.points;
    }

    public ArrayList<Edge> getEdges() {
        return model.edg;
    }

    private static void testCase1(Controller controller) {
        controller.addPoint(new Point(100, 300));
        controller.addPoint(new Point(220, 120));
        controller.addPoint(new Point(220, 220));
        controller.addPoint(new Point(330, 400));
        controller.addPoint(new Point(150, 80));
        controller.addPoint(new Point(400, 150));
        controller.sortPoints();
        controller.addEdge(0, 1);
        controller.addEdge(0, 2);
        controller.addEdge(0, 4);
        //controller.addEdge(1,3);
        controller.addEdge(4, 5);
        //controller.addEdge(3, 5);
        controller.addEdge(2, 5);
        controller.addEdge(1, 2);
        controller.addEdge(1, 4);
        controller.addEdge(1, 5);
    }

    void start() {
        model.start();
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Dimension sSize = new Dimension(500, 500);
        frame.setSize(sSize);

        Controller controller = new Controller();
        controller.model = new Model();

        testCase1(controller);

        Drawer drawer = new Drawer(controller);
        frame.add(drawer);
        frame.setVisible(true);
    }

    void addEdge(int l, int r) {                                                 //check for unic
        if (l < r) model.addEdge(l, r);
        else if (l > r) {
            model.addEdge(r, l);
        }
    }


    public void locatePoint(Point p) {

    }
}

