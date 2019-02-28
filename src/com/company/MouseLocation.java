package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class MouseLocation extends JPanel {
    Controller controller;
    private boolean isPointCheckModeFlag = false;
    private boolean isPointInside = false;
    private Point pointToCheck;
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
//        pointToCheck = new Point(100, 300);
//        isPointInside = pnpoly();
//    }

    public MouseLocation(Controller controller) {
        this.controller = controller;

        this.setLayout(null);
        JButton button = new JButton("Close");
        button.setBounds(10, 10, 150, 20);

        JPanel panel = this;                                                                //govnokod?
        ActionListener closeAndOrderAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = DrawState.CONNECT_EDGES;
                controller.sortPoints();
                ArrayList<Point> points = controller.getPoints();
                for (int i = 0; i < points.size(); i++) {
                    JLabel l = new JLabel(String.valueOf(i));
                    Point p = points.get(i);
                    l.setBounds(p.getX() + diametr, p.getY() + diametr, 20, 20);
                    panel.add(l);
                }

                isPointCheckModeFlag = true;
                repaint();
            }
        };
        button.addActionListener(closeAndOrderAction);
        this.add(button);

        left = new JTextField("");
        right = new JTextField("");
        left.setBounds(200, 10, 40, 20);
        right.setBounds(250, 10, 40, 20);
        this.add(left);
        this.add(right);

        JButton newEdgeButton = new JButton("Add");
        newEdgeButton.setBounds(300, 10, 50, 20);

        ActionListener addEdgeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        };
        newEdgeButton.addActionListener(addEdgeAction);
        this.add(newEdgeButton);

        JButton edgesDone = new JButton("Done");
        edgesDone.setBounds(370, 10, 50, 20);

        //regularize, make chains, make tree with chains
        ActionListener edgesDoneAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = DrawState.LOCALIZATION;
                controller.regularize();
                controller.makeChainsAndTree();
                for (int i = 0; i < 4; i++) {
                    panel.getComponent(i).setEnabled(false);
                }
                repaint();
            }
        };
        edgesDone.addActionListener(edgesDoneAction);
        this.add(edgesDone);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                Point p = new Point(x, y);

                if (state == DrawState.PLACE_POINTS) {
                    controller.add(p);
                } else if (state == DrawState.CONNECT_EDGES) {

                } else {
                    controller.locatePoint(p);
                    pointToCheck = p;
                }

//                if (!isPointCheckModeFlag) {
//                    x = e.getX();
//                    y = e.getY();
//                    Point p = new Point(x, y);
//                    controller.add(p);
//                } else {
//                    x = e.getX();
//                    y = e.getY();
//                    pointToCheck = new Point(x, y);
//                    isPointInside = pnpoly();
//                }
                repaint();
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoints(g);
        drawEdges(g);

        if ((state == DrawState.LOCALIZATION) && (pointToCheck != null)) {
            drawPointToCheck(g);
        }



//        if (!isPointCheckModeFlag) {
//            drawPolygonalChain(g);
//        }
//        else
//        {
//            drawClosedPolygonalChain(g);
//
//            if(pointToCheck != null){
//                drawCircle(g);
//            }
//        }

    }

    private void drawPointToCheck(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(pointToCheck.getX() - diametr / 2, pointToCheck.getY() - diametr / 2, diametr, diametr);
    }

    private void drawPoints(Graphics g) {
        ArrayList<Point> points = controller.getPoints();
        g.setColor(Color.BLUE);
        for (Point p : points) {
            g.fillOval(p.getX() - diametr / 2, p.getY() - diametr / 2, diametr, diametr);
        }
    }

    private void drawEdges(Graphics g) {
        ArrayList<Edge> edges = controller.getEdges();
        for (Edge currEdge:edges) {
            g.setColor(Color.ORANGE);
            g.drawLine(currEdge.getStart().getX(), currEdge.getStart().getY(),
                    currEdge.getEnd().getX(), currEdge.getEnd().getY());
        }

    }




    private boolean pnpoly() {
        boolean c = false;
        for (int i = 0, j = arrPoints.size() - 1; i < arrPoints.size(); j = i++) {
            int yi = arrPoints.get(i).getY();
            if (

                    (((arrPoints.get(i).getY() <= pointToCheck.getY()) && (pointToCheck.getY() < arrPoints.get(j).getY())) || ((arrPoints.get(j).getY() <= pointToCheck.getY()) && (pointToCheck.getY() < arrPoints.get(i).getY()))) && (((arrPoints.get(j).getX() - arrPoints.get(i).getX()) * (pointToCheck.getY() - arrPoints.get(i).getY()) / (arrPoints.get(j).getY() - arrPoints.get(i).getY()) + arrPoints.get(i).getX()) < pointToCheck.getX()))
                c = !c;
        }
        return c;
    }


}