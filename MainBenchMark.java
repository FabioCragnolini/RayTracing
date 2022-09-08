import java.util.Vector;
import lib.*;

public class MainBenchMark {
    public static void main(String[] args) {
        /* === Benchmark scene === */
        long t1 = System.currentTimeMillis();
        Material plastic = new Material(Material.Type.DIFFUSE);
        Material rubber = new Material(Material.Type.DIFFUSE);
        rubber.setShiny(0.0, 0);
        Material metal = new Material(Material.Type.REFLECTIVE);
        metal.setShiny(0.33, 30);
        metal.setReflection(0.6);

        Vector<Entity> vs = new Vector<Entity>();
        vs.add(new Sphere(1.5, new Point(6, 1.5, 2), new Point(100, 100, 100), metal));
        vs.add(new Sphere(1.5, new Point(3, 1.5, 2), new Point(100, 100, 100), metal));
        vs.add(new Sphere(500, new Point(0, -500, 0), new Point(164, 145, 255), rubber));
        vs.add(new Sphere(1, new Point(-3, 1, 4), new Point(231, 111, 81), plastic));
        vs.add(new Sphere(1, new Point(-2.5, 1, 2), new Point(244, 162, 97), plastic));
        vs.add(new Sphere(1, new Point(-2, 1, 0), new Point(233, 196, 106), plastic));
        vs.add(new Sphere(1, new Point(-1.5, 1, -2), new Point(42, 157, 143), plastic));
        vs.add(new Sphere(1, new Point(-1, 1, -4), new Point(38, 70, 83), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(80, 80, 80), 0.9, Light.Type.DISTANT));
        vl.add(new Light(new Point(-6, 16, -6), 400, Light.Type.SPHERICAL));

        Camera myCamera = new Camera(new Point(4, 5, 14), new Point(1, 1, 0), 60, 1920, 1080);

        Scene myScene = new Scene(vs, vl, myCamera);

        /* === Rendering === */
        long t2 = System.currentTimeMillis();
        myScene.render();

        /* === Creating image === */
        long t3 = System.currentTimeMillis();
        myScene.out("Benchmark");

        /* === Printing results === */
        long t4 = System.currentTimeMillis();
        System.out.println("Scene time:\t" + (t2 - t1) + "\tms");
        System.out.println("Rendering time:\t" + (t3 - t2) + "\tms");
        System.out.println("Output time:\t" + (t4 - t3) + "\tms");
        System.out.println("Total time:\t" + (t4 - t1) + "\tms");
    }
}
