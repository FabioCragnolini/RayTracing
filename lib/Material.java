package lib;

public class Material {
    public enum Type {
        DIFFUSE, REFLECTIVE, TRANPARENT
    };

    private Type type;
    private double kDiff = 1;
    private double kShiny = 0.2;
    private double expShiny = 20;
    private double kReflect = 0.8;

    public Material(Type type) {
        this.type = type;
    }

    public void setShiny(double k, double exp) {
        this.kShiny = k;
        this.expShiny = exp;
    }

    public Type getType() {
        return type;
    }

    public double getkDiff() {
        return kDiff;
    }

    public double getkShiny() {
        return kShiny;
    }

    public double getExpShiny() {
        return expShiny;
    }

    public double getkReflect() {
        return kReflect;
    }
}
