package lib;

import java.util.Vector;

public class Sphere extends Entity {
    public double radius;
    public Point center;

    public Sphere(double radius, Point center, Point color, Material material) {
        this.radius = radius;
        this.center = center;
        this.color = color;
        this.material = material;
    }

    @Override
    public double intersect(Ray r) {
        Point d = Point.diff(r.getOrigin(), center);
        double a = Point.dot(r.getDir(), r.getDir());
        double b = 2.0 * Point.dot(r.getDir(), d);
        double c = Point.dot(d, d) - radius * radius;
        double delta = b * b - 4.0 * a * c;
        if (delta < 0) // no intersection
            return -1.0;
        double t = (-b - Math.sqrt(delta)) / (2.0 * a);
        if (t < 0)
            return -1.0; // intersection behind ray
        else
            return t; // intersection
    }

    @Override
    public Ray normal(Point p) {
        Point normalDir = Point.diff(p, center).normalize();
        return new Ray(p, normalDir);
    }

    public int intersect(Entity e) {
        Sphere s = (Sphere) e;
        Point d = Point.diff(center, s.center);
        if (d.length() > radius + s.radius)
            return 0;
        else
            return 1;
    }

    public int intersect(Vector<Entity> vs) {
        for (int i = 0; i < vs.size(); i++) {
            if (intersect(vs.elementAt(i)) == 1)
                return 1;
        }
        return 0;
    }
}
