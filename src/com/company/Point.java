package com.company;

import java.util.ArrayList;

public class Point implements Comparable<Point> {
    private int x;
    private int y;
    ArrayList<Edge> in;
    ArrayList<Edge> out;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.in = new ArrayList<>();
        this.out = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + "}\n";
    }

    @Override
    public int compareTo(Point o) {
        if (y < o.y || ((y == o.y) && (x < o.x)) ) return -1;
        else return 1;
    }
}
