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
        if (r.getDir().getX() >= 0) {
            tmin = (min.getX() - r.getOrigin().getX()) / r.getDir().getX();
            tmax = (max.getX() - r.getOrigin().getX()) / r.getDir().getX();
        } else {
            tmin = (max.getX() - r.getOrigin().getX()) / r.getDir().getX();
            tmax = (min.getX() - r.getOrigin().getX()) / r.getDir().getX();
        }

        double tymin, tymax;
        if (r.getDir().getY() >= 0) {
            tymin = (min.getY() - r.getOrigin().getY()) / r.getDir().getY();
            tymax = (max.getY() - r.getOrigin().getY()) / r.getDir().getY();
        } else {
            tymin = (max.getY() - r.getOrigin().getY()) / r.getDir().getY();
            tymax = (min.getY() - r.getOrigin().getY()) / r.getDir().getY();
        }
        if ((tmin > tymax) || (tymin > tmax))
            return -1.0;
        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;

        double tzmin, tzmax;
        if (r.getDir().getZ() >= 0) {
            tzmin = (min.getZ() - r.getOrigin().getZ()) / r.getDir().getZ();
            tzmax = (max.getZ() - r.getOrigin().getZ()) / r.getDir().getZ();
        } else {
            tzmin = (max.getZ() - r.getOrigin().getZ()) / r.getDir().getZ();
            tzmax = (min.getZ() - r.getOrigin().getZ()) / r.getDir().getZ();
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
        Point deltaAbs = delta.abs();
        if (deltaAbs.getX() > deltaAbs.getY() && deltaAbs.getX() > deltaAbs.getZ())
            return new Ray(p, new Point(Math.signum(delta.getX()), 0, 0));
        else if (deltaAbs.getY() > deltaAbs.getZ())
            return new Ray(p, new Point(0, Math.signum(delta.getY()), 0));
        else
            return new Ray(p, new Point(0, 0, Math.signum(delta.getZ())));
    }

    private Point center() {
        return Point.scalar(0.5, Point.sum(max, min));
    }
}
