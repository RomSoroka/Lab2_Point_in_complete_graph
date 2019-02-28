package com.company;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    private Model model;

    void add(Point p) {
        model.points.add(p);
    }

    void sortPoints() {
        Collections.sort(model.points);
        System.out.println(model.points);
    }

    public ArrayList<Point> getPoints() {
        return model.points;
    }

    public ArrayList<Edge> getEdges() {
        return model.edg;
    }

    void addEdge(int l, int r) {                                                 //check for unic
        if (l<r)  model.addEdge(l,r);
        else{
            model.addEdge(r,l);
        }
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

        Drawer drawer = new Drawer(controller);
        frame.add(drawer);
        frame.setVisible(true);
    }

    //TODO
    public void regularize() { }

    public void makeChainsAndTree() {

    }

    public void locatePoint(Point p) {
    }
}

