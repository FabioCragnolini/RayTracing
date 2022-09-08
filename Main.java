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
        //vs.add(new Sphere(1.5, new Point(3, 0, 0), new Point(100, 100, 100), metal));
        //vs.add(new Sphere(1.5, new Point(0, 0, 0), new Point(100, 100, 100), metal));
        vs.add(new Sphere(100, new Point(0, -100 - 1.5, 0), new Point(0, 128, 0), plastic));
        vs.add(new Box(new Point(-1, -1.5, -1), new Point(1, 0.5, 1), new Point(255, 0, 0), plastic));
        //vs.add(new Box(new Point(-10, -3, -10), new Point(10, -1.5, 10), new Point(0, 128, 0), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-5, 10, 0), 1, Light.Type.DISTANT));
        // vl.add(new Light(new Point(6, 10, 0), 0.3, Light.Type.DISTANT));

        Camera myCamera1 = new Camera(new Point(10, 10, 0), new Point(0, 0, 0), 60, 1920, 1080);
        Camera myCamera2 = new Camera(new Point(0, 10, 10), new Point(0, 0, 0), 60, 1920, 1080);
        Scene myScene1 = new Scene(vs, vl, myCamera1);
        myScene1.render();
        Scene myScene2 = new Scene(vs, vl, myCamera2);
        myScene2.render();
        myScene1.out("Render1");
        myScene2.out("Render2");

        /* Entity b = new Box(new Point(0, 0, 0), new Point(10, 10, 10), new Point(255, 0, 0), plastic);
        Entity s = new Sphere(5, new Point(5, 5, 5), new Point(255, 0, 0), plastic);
        System.out.println(b.intersect(new Ray(new Point(4, 4, -1), new Point(0, 0, 1))));
        System.out.println(s.intersect(new Ray(new Point(4, 4, -1), new Point(0, 0, 1)))); */
    }
}