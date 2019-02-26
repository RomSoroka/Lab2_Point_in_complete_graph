package com.company;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    Model model;

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

    void addEdge(int l, int r) {
        if (l<r)  model.addEdge(l,r);
        else{
            model.addEdge(r,l);
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Dimension sSize = new Dimension(400, 500);
        frame.setSize(sSize);

        Controller controller = new Controller();
        controller.model = new Model();

        MouseLocation mouseLocation = new MouseLocation(controller);
        frame.add(mouseLocation);
        frame.setVisible(true);
    }


}

