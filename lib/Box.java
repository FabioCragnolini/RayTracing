package lib;

public class Box extends Entity {
    public Point min;
    public Point max;

    public Box(Point min, Point max, Point color, Material material) {
        this.min = min;
        this.max = max;
        this.color = color;
        this.material = material;
    }

    @Override
    public double intersect(Ray r) {
        double tmin, tmax;
        if (r.getDir().x >= 0) {
            tmin = (min.x - r.getOrigin().x) / r.getDir().x;
            tmax = (max.x - r.getOrigin().x) / r.getDir().x;
        } else {
            tmin = (max.x - r.getOrigin().x) / r.getDir().x;
            tmax = (min.x - r.getOrigin().x) / r.getDir().x;
        }

        double tymin, tymax;
        if (r.getDir().y >= 0) {
            tymin = (min.y - r.getOrigin().y) / r.getDir().y;
            tymax = (max.y - r.getOrigin().y) / r.getDir().y;
        } else {
            tymin = (max.y - r.getOrigin().y) / r.getDir().y;
            tymax = (min.y - r.getOrigin().y) / r.getDir().y;
        }
        if ((tmin > tymax) || (tymin > tmax))
            return -1.0;
        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;

        double tzmin, tzmax;
        if (r.getDir().z >= 0) {
            tzmin = (min.z - r.getOrigin().z) / r.getDir().z;
            tzmax = (max.z - r.getOrigin().z) / r.getDir().z;
        } else {
            tzmin = (max.z - r.getOrigin().z) / r.getDir().z;
            tzmax = (min.z - r.getOrigin().z) / r.getDir().z;
        }
        if ((tmin > tzmax) || (tzmin > tmax))
            return -1.0;
        if (tzmin > tmin)
            tmin = tzmin;
        if (tzmax < tmax)
            tmax = tzmax;
        return tmin;
    }

    @Override
    public Ray normal(Point p) {
        Point delta = Point.diff(p, center());
        delta = new Point(delta.x / xLength(), delta.y / yLength(), delta.z / zLength());    // Normalize distances for non squared boxes
        Point deltaAbs = delta.abs();
        if (deltaAbs.x > deltaAbs.y && deltaAbs.x > deltaAbs.z)
            return new Ray(p, new Point(Math.signum(delta.x), 0, 0));
        else if (deltaAbs.y > deltaAbs.z)
            return new Ray(p, new Point(0, Math.signum(delta.y), 0));
        else
            return new Ray(p, new Point(0, 0, Math.signum(delta.z)));
    }

    private Point center() {
        return Point.scalar(0.5, Point.sum(max, min));
    }

    private double xLength() {
        return Math.abs(max.x - min.x);
    }
    private double yLength() {
        return Math.abs(max.y - min.y);
    }
    private double zLength() {
        return Math.abs(max.z - min.z);
    }
}
