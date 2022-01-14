package lib;

public class Light {
    public enum Type {
        DISTANT, SPHERICAL
    };

    public Point origin;
    public double power;
    public Type type;

    public Light(Point origin, double power, Type type) {
        this.origin = origin;
        this.power = power;
        this.type = type;
    }

    public double getIntensity(Point p) {
        switch (type) {
            case DISTANT:
                return power;
            case SPHERICAL:
                return power / (4.0 * Math.PI * Point.diff(origin, p).length2());
            default:
                return 0;
        }
    }

}
