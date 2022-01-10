package lib;

import java.util.Vector;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Scene {
    private Vector<Sphere> subject;
    private Vector<Light> lights;
    private Camera camera;
    private Vector<Vector<Point>> matrix;
    private String format;
    // scene parameters
    private final double BIAS = 0.00001;
    private final double AMBIENT = 0.2;

    public Scene(Vector<Sphere> subject, Vector<Light> lights, Camera camera) {
        this.subject = subject;
        this.lights = lights;
        this.camera = camera;
        format = "png";

        matrix = new Vector<Vector<Point>>(); // initialize black matrix
        for (int i = 0; i < camera.getyDim(); i++) {
            Vector<Point> line = new Vector<Point>();
            for (int j = 0; j < camera.getxDim(); j++) {
                line.add(j, new Point(0, 0, 0));
            }
            matrix.add(i, line);
        }
    }

    public void changeFormat(String format) {
        this.format = format;
    }

    public void render() {
        System.out.print("Rendering... ");
        for (int i = 0; i < camera.getyDim(); i++) {
            Vector<Point> line = new Vector<Point>();
            for (int j = 0; j < camera.getxDim(); j++) {
                Ray primaryRay = camera.getRay(j, i);
                line.add(j, cast(primaryRay, 1));
            }
            matrix.add(i, line);

            if ((i + 1) % (camera.getyDim() / 20) == 0) // progress bar
            {
                // System.out.println((i + 1) / (camera.getyDim() / 20) * 5 + "%");
                progressBar((i + 1) / (camera.getyDim() / 20));
            }
        }
    }

    public void out() {
        int i = 0, j = 0;
        try {
            BufferedImage image = new BufferedImage(camera.getxDim(), camera.getyDim(), BufferedImage.TYPE_INT_RGB);

            for (i = 0; i < camera.getyDim(); i++) {
                for (j = 0; j < camera.getxDim(); j++) {
                    Color pixelColor = new Color((int) matrix.get(i).get(j).getX(), (int) matrix.get(i).get(j).getY(),
                            (int) matrix.get(i).get(j).getZ());
                    image.setRGB(j, camera.getyDim() - 1 - i, pixelColor.getRGB());
                }
            }
            File output = new File("Render." + format);
            ImageIO.write(image, format, output);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("(" + i + ", " + j + ") Color: " + matrix.get(i).get(j).toString());
            ;
        }
    }

    private Point cast(Ray r, int depth) {
        double intersection = -1.0;
        Sphere currentSphere = new Sphere(0, new Point(), new Point(), new Material(Material.Type.DIFFUSE));
        for (int i = 0; i < subject.size(); i++) // find closest intersection
        {
            double currentIntersection = r.intersect(subject.elementAt(i));
            if ((intersection == -1.0 && currentIntersection > 0)
                    || (currentIntersection > 0 && currentIntersection < intersection)) {
                intersection = currentIntersection;
                currentSphere = subject.elementAt(i);
            }
        }

        if (intersection > 0.0) {

            switch (currentSphere.material.getType()) {
                case DIFFUSE:
                    Point shadowOrig = r.at(intersection);
                    Point shadowDir = Point.diff(lights.get(0).origin, shadowOrig).normalize();
                    Point normalDir = Point.diff(shadowOrig, currentSphere.center).normalize();
                    shadowOrig = Point.sum(shadowOrig, Point.scalar(BIAS, normalDir)); // removing shadow acne
                    Ray normal = new Ray(shadowOrig, normalDir);
                    Ray shadowRay = new Ray(shadowOrig, shadowDir);

                    for (int i = 0; i < subject.size(); i++) {
                        if (shadowRay.intersect(subject.elementAt(i)) > 0) // no light passes
                        {
                            return Point.scalar(AMBIENT, currentSphere.color); // ambient correction
                        }
                    }

                    Point diffuse = Point.scalar(
                            AMBIENT + ((1.0 - AMBIENT) * currentSphere.material.getkDiff() * Math.max(0.0, Point.dot(normal.getDir(), shadowRay.getDir()))),
                            currentSphere.color);
                    Point shiny = Point.scalar(
                            currentSphere.material.getkShiny() * Math.max(0.0,
                                    Math.pow(Point.dot(r.getDir().normalize(), shadowRay.reflection(normal).getDir()),
                                            currentSphere.material.getExpShiny())),
                            new Point(255, 255, 255));
                    return Point.sum(diffuse, shiny).clamp(0, 255);

                case REFLECTIVE:
                    return null;
                case TRANPARENT:
                    // TODO
                    return null;
                default:
                    return null;
            }

        } else { // background
            int shade = (int) ((r.getDir().normalize().getY() + 1) / 2.0 * 255);
            return new Point(255 - shade, 255 - shade, 255);
        }
    }

    private static void progressBar(int n) {
        for (int i = 0; i < n - 1; i++) {
            if (i == 0)
                System.out.print("\b");
            System.out.print("\b");
            if (i == n - 2)
                System.out.print("\b");
        }
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print("=");
        }
        System.out.print("]");
    }
}
