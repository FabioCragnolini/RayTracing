package lib;

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