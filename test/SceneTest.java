package test;

import java.util.Vector;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import org.junit.Test;

import lib.*;

public class SceneTest {
    @Test
    public void testOut() {
        Material plastic = new Material(Material.Type.DIFFUSE);
        Material metal = new Material(Material.Type.REFLECTIVE);
        plastic.setShiny(0.33, 30);
        metal.setReflection(0.4);

        Vector<Entity> vs = new Vector<Entity>();
        vs.add(new Sphere(1.5, new Point(0, 0, 0), new Point(100, 100, 100), metal));
        // vs.add(new Box(new Point(-1.5, -2.5, -1.5), new Point(1.5, -1.5, 1.5), new
        // Point(255, 0, 0), plastic));

        Vector<Light> vl = new Vector<Light>();
        vl.add(new Light(new Point(-2, 5, 3), 1, Light.Type.DISTANT));

        Camera myCamera = new Camera(new Point(-5, 1.4, 5), new Point(0, -0.8, 0), 60, 400, 400);

        Scene myScene = new Scene(vs, vl, myCamera);
        myScene.render();
        myScene.out("test");

        try {
            BufferedImage actual = ImageIO.read(new File("test.png"));
            BufferedImage expected = ImageIO.read(new File("src/test/SceneTest.png"));
            assertEquals(actual, expected);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
