import java.util.Vector;
import lib.*;

public class Main {
    public static void main(String[] args) {
        Material plastic = new Material(Material.Type.DIFFUSE);
        Material metal = new Material(Material.Type.REFLECTIVE);
        metal.setShiny(0.33, 50);
        metal.setReflection(0.4);

        Vector<Sphere> vs = new Vector<Sphere>();
        vs.add(new Sphere(1.5, new Point(3, 0, 0), new Point(100, 100, 100), metal));
        
        vs.add(new Sphere(1.5, new Point(0, 0, 0), new Point(255, 0, 0), plastic));
        vs.add(new Sphere(100, new Point(0, -100 - 1.5, 0), new Point(0, 128, 0), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-2, 5, 3)));

        Camera myCamera = new Camera(new Point(0, 1, 9), new Point(0, 0, 0), 60, 1920, 1080);
        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.render();
        myScene.out();
    }
}