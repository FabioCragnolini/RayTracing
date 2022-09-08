package lib;

import java.util.Vector;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Scene {
    private Vector<Entity> subject;
    private Vector<Light> lights;
    private Camera camera;
    private Vector<Vector<Point>> matrix;
    private String format;
    // scene parameters
    private final double BIAS = 0.00001;
    private final double AMBIENT = 0.2;
    private final int MAX_DEPTH = 5;

    public Scene(Vector<Entity> subject, Vector<Light> lights, Camera camera) {
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
        System.out.println("");
    }

    public void out(String fileName) {
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
            File output = new File(fileName + "." + format);
            ImageIO.write(image, format, output);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("(" + i + ", " + j + ") Color: " + matrix.get(i).get(j).toString());
            ;
        }
    }

    private Point cast(Ray r, int depth) {
        if (depth > MAX_DEPTH)
            return new Point();
        double intersection = -1.0;
        Entity currentEntity = null;
        for (int i = 0; i < subject.size(); i++) // find closest intersection
        {
            double currentIntersection = subject.elementAt(i).intersect(r);
            if ((intersection == -1.0 && currentIntersection > 0)
                    || (currentIntersection > 0 && currentIntersection < intersection)) {
                intersection = currentIntersection;
                currentEntity = subject.elementAt(i);
            }
        }

        if (intersection > 0.0) {
            Ray normal = currentEntity.normal(r.at(intersection));

            Point currentColor = new Point();
            for (int iLight = 0; iLight < lights.size(); iLight++) // cycle all the lights
            {
                currentColor = Point.sum(currentColor, Point.scalar(lights.elementAt(iLight).getIntensity(normal.getOrigin()),
                        shading(currentEntity, r, normal, lights.elementAt(iLight), depth)));
            }
            return currentColor.clamp(0, 255);

        } else { // background
            int shade = (int) ((r.getDir().normalize().getY() + 1) / 2.0 * 255);
            return new Point(255 - shade, 255 - shade / 1.5, 255);
        }
    }

    private Point shading(Entity e, Ray eye, Ray normal, Light l, int depth) {
        Point shadowOrig = Point.sum(normal.getOrigin(), Point.scalar(BIAS, normal.getDir())); // removing shadow acne
        Point shadowDir = Point.diff(l.origin, normal.getOrigin()).normalize();
        Ray shadowRay = new Ray(shadowOrig, shadowDir);

        if (e.material.getType() == Material.Type.DIFFUSE) {
            for (int i = 0; i < subject.size(); i++) {
                if (subject.elementAt(i).intersect(shadowRay) > 0) // no light passes
                {
                    return Point.scalar(AMBIENT, e.color); // ambient correction
                }
            }
        }

        Point diffuse = Point.scalar(
                AMBIENT + ((1.0 - AMBIENT) * e.material.getkDiff()
                        * Math.max(0.0, Point.dot(normal.getDir(), shadowRay.getDir()))),
                e.color);
        Point shiny;
        if (Point.dot(normal.getDir(), shadowRay.getDir()) > 0.0) { // correct negative shiny reflection
            shiny = Point.scalar(
                    e.material.getkShiny() * Math.max(0.0,
                            Math.pow(Point.dot(eye.getDir().normalize(), shadowRay.reflection(normal).getDir()),
                                    e.material.getExpShiny())),
                    new Point(255, 255, 255));
        } else
            shiny = new Point();

        switch (e.material.getType()) {
            case DIFFUSE:
                return Point.sum(diffuse, shiny).clamp(0, 255);
            case REFLECTIVE:
                Ray reflectRay = eye.reflection(normal);
                Point reflect = Point.scalar(e.material.getkReflect(), cast(reflectRay, depth + 1));
                return Point.sum(diffuse, shiny, reflect).clamp(0, 255);
            default:
                return new Point();
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
