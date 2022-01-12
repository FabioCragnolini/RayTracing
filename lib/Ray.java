package lib;

import java.lang.Math;

public class Ray {
    private Point origin;
    private Point dir;

    public Ray(Point origin, Point dir) {
        this.origin = origin;
        this.dir = dir;
    }

    public Point getOrigin() {
        return origin;
    }

    public Point getDir() {
        return dir;
    }

    public double intersect(Sphere s) {
        Point d = Point.diff(origin, s.center);
        double a = Point.dot(dir, dir);
        double b = 2.0 * Point.dot(dir, d);
        double c = Point.dot(d, d) - s.radius * s.radius;
        double delta = b * b - 4.0 * a * c;
        if (delta < 0) // no intersection
            return -1.0;
        double t = (-b - Math.sqrt(delta)) / (2.0 * a);
        if (t < 0)
            return -1.0; // intersection behind ray
        else
            return t; // intersection
    }

    public Point at(double t) {
        return Point.sum(origin, Point.scalar(t, dir));
    }

    public Ray reflection(Ray n)
    {
        return new Ray(n.origin, Point.diff(dir, Point.scalar(2.0 * Point.dot(dir, n.dir), n.dir)));
    }

    @Override
    public String toString() {
        return "Origin: " + origin.toString() + "\n" +
                "Direction: " + dir.toString();
    }
}