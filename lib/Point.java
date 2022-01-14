package lib;

public class Point {
    private double x;
    private double y;
    private double z;

    public Point() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0.0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Point sum(Point p1, Point p2) {
        Point p = new Point(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
        return p;
    }

    public static Point sum(Point p1, Point p2, Point p3) {
        Point p = new Point(p1.x + p2.x + p3.x, p1.y + p2.y + p3.y, p1.z + p2.z + p3.z);
        return p;
    }

    public static Point diff(Point p1, Point p2) {
        Point p = new Point(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        return p;
    }

    public static Point scalar(double k, Point p1) {
        Point p = new Point(k * p1.x, k * p1.y, k * p1.z);
        return p;
    }

    public static double dot(Point p1, Point p2) {
        return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z;
    }

    public static Point cross(Point p1, Point p2) {
        return new Point(p1.y * p2.z - p1.z * p2.y, p1.z * p2.x - p1.x * p2.z, p1.x * p2.y - p1.y * p2.x);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double length2() {
        return x * x + y * y + z * z;
    }

    public Point normalize() {
        return new Point(x / length(), y / length(), z / length());
    }

    public Point clamp(double min, double max) {
        return new Point(clamp(x, min, max), clamp(y, min, max), clamp(z, min, max));
    }

    private static double clamp(double x, double min, double max) {
        return Math.max(0, Math.min(x, max));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}