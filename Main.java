import java.util.Vector;

public class Main {
    public static void main(String[] args) {

        Vector<Sphere> vs = new Vector<Sphere>();
        vs.add(new Sphere(1.5, new Point(0,0,0), new Point(255,0,0)));
        //vs.add(new Sphere(1, new Point(-3,0,0), new Point(0,0,255)));
        vs.add(new Sphere(100, new Point(0,-100,-20), new Point(0,128,0)));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-2,5,2)));
        //vl.add(new Light(new Point(0,5,0)));

        Camera myCamera = new Camera(new Point(0,0,9), new Point(0,0,0), 60, 1920, 1080);
        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.render();
        myScene.out();
    }
}