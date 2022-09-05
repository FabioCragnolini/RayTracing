package lib;

public class Box {
    public Point min;
    public Point max;
    public Point color;
    public Material material;

    public Box(Point min, Point max, Point color, Material material) {
        this.min = min;
        this.max = max;
        this.color = color;
        this.material = material;
    }
}
