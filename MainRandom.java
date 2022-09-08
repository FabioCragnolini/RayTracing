

import java.util.*;

import lib.*;

public class MainRandom {
    public static void main(String[] args) {
        Material plastic = new Material(Material.Type.DIFFUSE);
        Material metal = new Material(Material.Type.REFLECTIVE);
        metal.setShiny(0.33, 50);
        metal.setReflection(0.4);

        Vector<Entity> vs = new Vector<Entity>();
        vs.add(new Sphere(2, new Point(2, 2, -1), new Point(234, 45, 46), plastic));
        vs.add(new Sphere(2, new Point(-2, 2, 1.5), new Point(0, 116, 189), plastic));
        vs.add(new Sphere(1.5, new Point(5, 1.5, 3), new Point(100, 100, 100), metal));

        final int N_METAL = 3;
        final int N_PLASTIC = 100 - N_METAL - 4;
        Random rand = new Random();
        for (int i = 0; i < N_METAL; i++) {
            Sphere s;
            do {
                s = new Sphere(3,
                        new Point(rand.nextDouble(32.0) - 16.0 + 1.0, 0.7, rand.nextDouble(35.0) - 20.0 + 4.0),
                        new Point(100, 100, 100), metal);
            } while (s.intersect(vs) == 1);

            vs.add(new Sphere(0.7, s.center, s.color, metal));
            // System.out.println(i);
        }
        for (int i = 0; i < N_PLASTIC; i++) {
            Sphere s;
            do {
                s = new Sphere(1.4,
                        new Point(rand.nextDouble(32.0) - 16.0 + 1.0, 0.7, rand.nextDouble(35.0) - 20.0 + 4.0),
                        new Point(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)), plastic);
            } while (s.intersect(vs) == 1);

            vs.add(new Sphere(0.7, s.center, s.color, plastic));
            // System.out.println(i);
        }
        vs.add(new Sphere(1000, new Point(0, -1000, 0), new Point(128, 128, 180), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-20, 30, 30), 0.9, Light.Type.DISTANT));
        // vl.add(new Light(new Point(10, 40, 10), 0.1, Light.Type.DISTANT));

        Camera myCamera = new Camera(new Point(1, 10, 18), new Point(1, 1, 0), 45, 1920, 1080);

        Scene myScene = new Scene(vs, vl, myCamera);
        // myScene.changeFormat("jpg");
        myScene.render();
        myScene.out("Render");

    }
}
