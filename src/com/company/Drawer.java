package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Drawer extends JPanel {
    private Controller controller;
    private boolean isPointCheckModeFlag = false;
    private boolean isPointInside = false;
    private Point circle;
    private int diametr = 5;
    private ArrayList<Point> arrPoints = new ArrayList<>();

    private DrawState state = DrawState.PLACE_POINTS;

    JTextField left;
    JTextField right;

    Drawer(Controller controller) {
        this.controller = controller;
        this.setLayout(null);

        addCloseButton(controller);

        addTextFields();

        addNewEdgeButton(controller);

        addStartButton(controller);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x, y;
                if (!isPointCheckModeFlag) {
                    x = e.getX();
                    y = e.getY();
                    Point p = new Point(x, y);
                    controller.addPoint(p);
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
            g.fillOval(p.getX() - diametr / 2 ,p.getY() - diametr / 2, diametr, diametr);
        }
        drawGraph(g);
    }

    /* ---------------PRIVTE FUNCTIONS----------------- */

    private void addStartButton(Controller controller) {
        JButton startButton = new JButton("Start");
        startButton.setBounds(10, 40, 80, 20);

        ActionListener startAction = (ActionEvent e) -> {
            {
                controller.start();
                repaint();
            }
        };
        startButton.addActionListener(startAction);
        this.add(startButton);
    }

    private void addNewEdgeButton(Controller controller) {
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
    }

    private void addTextFields() {
        left = new JTextField("");
        right = new JTextField("");
        left.setBounds(200, 10, 40, 20);
        right.setBounds(250, 10, 40, 20);
        this.add(left);
        this.add(right);
    }

    private void addCloseButton(Controller controller) {
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
    }

    private void drawGraph(Graphics g) {
        ArrayList<Edge> edges = controller.getEdges();
        for (Edge currEdge : edges) {
            g.setColor(Color.ORANGE);
            g.drawLine(currEdge.getStart().getX(), currEdge.getStart().getY(),
                    currEdge.getEnd().getX(), currEdge.getEnd().getY());
        }

    }

    private boolean pnpoly() {
        boolean c = false;
        for (int i = 0, j = arrPoints.size() - 1; i < arrPoints.size(); j = i++) {
            int yi = arrPoints.get(i).getY();
            if ((((arrPoints.get(i).getY() <= circle.getY()) && (circle.getY() < arrPoints.get(j).getY()))
                    || ((arrPoints.get(j).getY() <= circle.getY()) && (circle.getY() < arrPoints.get(i).getY())))
                    && (((arrPoints.get(j).getX() - arrPoints.get(i).getX()) * (circle.getY() - arrPoints.get(i).getY())
                    / (arrPoints.get(j).getY() - arrPoints.get(i).getY()) + arrPoints.get(i).getX()) < circle.getX()))
                c = !c;
        }
        return c;
    }


}