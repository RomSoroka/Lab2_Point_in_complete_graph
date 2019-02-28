package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class Drawer extends JPanel {
    Controller controller;
    private boolean isPointCheckModeFlag = false;
    private boolean isPointInside = false;
    private Point circle;
    private int diametr = 5;
    private ArrayList<Point> arrPoints = new ArrayList<Point>();

    DrawState state = DrawState.PLACE_POINTS;

    JTextField left;
    JTextField right;

//    {
//        arrPoints.add(new Point(100, 100));
//        arrPoints.add(new Point(300, 100));
//        arrPoints.add(new Point(300, 300));
//        arrPoints.add(new Point(100, 300));
//        isPointCheckModeFlag = true;
//        circle = new Point(100, 300);
//        isPointInside = pnpoly();
//    }

    public Drawer(Controller controller) {
        this.controller = controller;

        this.setLayout(null);
        JButton button = new JButton("Close");
        button.setBounds(10, 10, 150, 20);

        JPanel panel = this;                                                                //govnokod?
        ActionListener click = (ActionEvent e) -> {
            state = DrawState.CONNECT_EDGES;
            controller.sortPoints();
            ArrayList<Point> points = controller.getPoints();
            for (int i = 0; i < points.size(); i++) {
                JLabel l = new JLabel(String.valueOf(i));
                Point p = points.get(i);
                l.setBounds(p.getX(), p.getY() + diametr, 20, 20);
                panel.add(l);
            }

            isPointCheckModeFlag = true;
            repaint();

        };
        button.addActionListener(click);
        this.add(button);

        left = new JTextField("");
        right = new JTextField("");
        left.setBounds(200, 10, 40, 20);
        right.setBounds(250, 10, 40, 20);
        this.add(left);
        this.add(right);

        JButton newEdgeButton = new JButton("Add");
        newEdgeButton.setBounds(300, 10, 50, 20);

        ActionListener addEdgeAction = (ActionEvent e) -> {
            int l, r;
            try {
                l = Integer.valueOf(left.getText());
                r = Integer.valueOf(right.getText());
            } catch (NumberFormatException exe) {
                System.out.println("Not a number");
                return;
            }
            controller.addEdge(l, r);
            repaint();
        };
        newEdgeButton.addActionListener(addEdgeAction);
        this.add(newEdgeButton);

        JButton startButton = new JButton("Start");
        newEdgeButton.setBounds(10, 40, 80, 20);

        ActionListener startAction = (ActionEvent e) -> {
            {
                controller.start();
                repaint();
            }
        };
        startButton.addActionListener(startAction);
        this.add(startButton);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x, y;
                if (!isPointCheckModeFlag) {
                    x = e.getX();
                    y = e.getY();
                    Point p = new Point(x, y);
                    controller.add(p);
                } else {
                    x = e.getX();
                    y = e.getY();
                    circle = new Point(x, y);
                    isPointInside = pnpoly();
                }
                repaint();
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<Point> points = controller.getPoints();
        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.fillOval(p.getX() - diametr / 2, p.getY() - diametr / 2, diametr, diametr);
        }

        if (state == DrawState.CONNECT_EDGES) {
            drawGraph(g);
        }


//        if (!isPointCheckModeFlag) {
//            drawPolygonalChain(g);
//        }
//        else
//        {
//            drawClosedPolygonalChain(g);
//
//            if(circle != null){
//                drawCircle(g);
//            }
//        }

    }

    private void drawGraph(Graphics g) {
        ArrayList<Edge> edges = controller.getEdges();
        for (Edge currEdge : edges) {
            g.setColor(Color.ORANGE);
            g.drawLine(currEdge.getStart().getX(), currEdge.getStart().getY(),
                    currEdge.getEnd().getX(), currEdge.getEnd().getY());
        }

    }

    private void drawClosedPolygonalChain(Graphics g) {
        if (arrPoints.size() > 1) {
            Point previousPoint = arrPoints.get(0);
            for (int i = 1; i < arrPoints.size(); i++) {
                Point currPoint = arrPoints.get(i);
                g.setColor(Color.ORANGE);
                g.drawLine(previousPoint.getX(), previousPoint.getY(), currPoint.getX(), currPoint.getY());
                previousPoint = currPoint;
            }
            previousPoint = arrPoints.get(0);
            Point lastPoint = arrPoints.get(arrPoints.size() - 1);
            g.setColor(Color.ORANGE);
            g.drawLine(previousPoint.getX(), previousPoint.getY(), lastPoint.getX(), lastPoint.getY());
        }
    }

    private void drawCircle(Graphics g) {
        if (isPointInside) g.setColor(Color.GREEN);
        else g.setColor(Color.RED);
        g.fillOval(circle.getX() - diametr / 2, circle.getY() - diametr / 2, diametr, diametr);
    }

    private boolean pnpoly() {
        boolean c = false;
        for (int i = 0, j = arrPoints.size() - 1; i < arrPoints.size(); j = i++) {
            int yi = arrPoints.get(i).getY();
            if (

                    (((arrPoints.get(i).getY() <= circle.getY()) && (circle.getY() < arrPoints.get(j).getY())) || ((arrPoints.get(j).getY() <= circle.getY()) && (circle.getY() < arrPoints.get(i).getY()))) && (((arrPoints.get(j).getX() - arrPoints.get(i).getX()) * (circle.getY() - arrPoints.get(i).getY()) / (arrPoints.get(j).getY() - arrPoints.get(i).getY()) + arrPoints.get(i).getX()) < circle.getX()))
                c = !c;
        }
        return c;
    }


}