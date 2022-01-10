import java.util.*;
import lib.*;

public class MainRandom {
    public static void main(String[] args) {
        Material plastic = new Material(Material.Type.DIFFUSE);

        Vector<Sphere> vs = new Vector<Sphere>();
        vs.add(new Sphere(2, new Point(2, 2, -1), new Point(234, 45, 46), plastic));
        vs.add(new Sphere(2, new Point(-2, 2, 1.5), new Point(0, 116, 189), plastic));

        final int N = 107;
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            Sphere s;
            do {
                s = new Sphere(1.2,
                        new Point(rand.nextDouble(32.0) - 16.0, 0.7, rand.nextDouble(35.0) - 20.0),
                        new Point(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)), plastic);
            } while (s.intersect(vs) == 1);

            vs.add(new Sphere(0.7, s.center, s.color, plastic));
            // System.out.println(i);
        }
        vs.add(new Sphere(1000, new Point(0, -1000, 0), new Point(128, 128, 180), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-20, 30, 30)));

        Camera myCamera = new Camera(new Point(0, 10, 18), new Point(0, 1, 0), 45, 1920, 1080);

        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.changeFormat("jpg");
        myScene.render();
        myScene.out();
    }
}
