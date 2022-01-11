import java.util.Vector;
import lib.*;

public class Main {
    public static void main(String[] args) {
        Material plastic = new Material(Material.Type.DIFFUSE);
        // plastic.setShiny(0.33, 30);

        Vector<Sphere> vs = new Vector<Sphere>();
        vs.add(new Sphere(1.5, new Point(0, 0, 0), new Point(255, 0, 0), plastic));
        vs.add(new Sphere(100, new Point(0, -100, -20), new Point(0, 128, 0), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-2, 5, 2)));

        Camera myCamera = new Camera(new Point(0, 0, 9), new Point(0, 0, 0), 60, 1920, 1080);
        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.render();
        myScene.out();
    }
}