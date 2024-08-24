package org.csystem.demo;

public class Point {
    public double x;
    public double y;

    public Point()
    {
    }

    public Point(int x, int y)
    {
        this((double)x, y);
    }

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    //...
}
