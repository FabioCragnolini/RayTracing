import java.util.Vector;

public class Sphere {
    public double radius;
    public Point center;
    public Point color;

    public Sphere(double radius, Point center, Point color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    public int intersect(Sphere s) {
        Point d = Point.diff(center, s.center);
        if (d.length() > radius + s.radius)
            return 0;
        else
            return 1;
    }
    public int intersect(Vector<Sphere> vs) {
        for(int i = 0; i < vs.size(); i++)
        {
            if(intersect(vs.elementAt(i)) == 1)
            return 1;
        }
        return 0;
    }
}
