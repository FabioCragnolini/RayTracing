package test;

import static org.junit.Assert.*;

import org.junit.Test;

import lib.Point;
import lib.Ray;

public class RayTest {
    // Using threshold to compare double values.
    final double THRESHOLD = 0.0001;

    @Test
    public void testAt() {
        Ray r = new Ray(new Point(0, 0, 0), new Point(3, 4, 0));
        Point actual = r.at(2);
        Point expected = new Point(6, 8, 0);
        assertTrue(actual.x - expected.x < THRESHOLD);
        assertTrue(actual.y - expected.y < THRESHOLD);
        assertTrue(actual.z - expected.z < THRESHOLD);
    }

    @Test
    public void testReflection() {
        Ray r = new Ray(new Point(0, 0, 0), new Point(0, 1, 1));
        Ray actual = r.reflection(new Ray(new Point(0, 0, 0), new Point(0, -1, 0)));
        Ray expected = new Ray(new Point(0, 0, 0), new Point(0, -1, 1));
        assertTrue(actual.getDir().x - expected.getDir().x < THRESHOLD);
        assertTrue(actual.getDir().y - expected.getDir().y < THRESHOLD);
        assertTrue(actual.getDir().z - expected.getDir().z < THRESHOLD);
    }
}
