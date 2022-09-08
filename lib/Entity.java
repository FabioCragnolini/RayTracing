package lib;

abstract public class Entity {
    public Point color;
    public Material material;

    
    abstract public double intersect(Ray r);
    abstract public Ray normal(Point p);
}
