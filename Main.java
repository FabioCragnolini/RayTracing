import java.util.Vector;
import lib.*;

public class Main {
    public static void main(String[] args) {
        Material plastic = new Material(Material.Type.DIFFUSE);
        Material metal = new Material(Material.Type.REFLECTIVE);
        plastic.setShiny(0.33, 30);
        metal.setReflection(0.4);

        Vector<Entity> vs = new Vector<Entity>();
        vs.add(new Sphere(1.5, new Point(-3, 0, 0), new Point(255, 0, 0), plastic));
        vs.add(new Sphere(1.5, new Point(3, 0, 0), new Point(100, 100, 100), metal));
        vs.add(new Sphere(1.5, new Point(0, 0, 0), new Point(100, 100, 100), metal));
        vs.add(new Sphere(100, new Point(0, -100 - 1.5, 0), new Point(0, 128, 0), plastic));
        //vs.add(new Box(new Point(-1.5, -2.5, -1.5), new Point(1.5, -1.5, 1.5), new Point(255, 0, 0), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-2, 5, 3), 1, Light.Type.DISTANT));

        Camera myCamera = new Camera(new Point(4, 1.5, 9), new Point(0, 0, 0), 60, 1920, 1080);
        //Camera myCamera = new Camera(new Point(-7, 2, 7), new Point(0, -0.8, 0), 60, 1920, 1080);
        
        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.render();
        myScene.out("Render");
    }
}