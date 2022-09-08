package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lib.Point;

public class PointTest {
    final double THRESHOLD = 0.0001;

    @Test
    public void testAbs() {
        Point actual = new Point(12.5, -3, -1.5).abs();
        Point expected = new Point(12.5, 3, 1.5);
        assertTrue(actual.x - expected.x < THRESHOLD);
        assertTrue(actual.y - expected.y < THRESHOLD);
        assertTrue(actual.z - expected.z < THRESHOLD);
    }

    @Test
    public void testCross() {
        Point expected = Point.cross(new Point(3, 4, -1), new Point(1, -2, 0));
        Point actual = new Point(-2, -1, -10);
        assertTrue(actual.x - expected.x < THRESHOLD);
        assertTrue(actual.y - expected.y < THRESHOLD);
        assertTrue(actual.z - expected.z < THRESHOLD);
    }

    @Test
    public void testDot() {
        double expected = Point.dot(new Point(3, 4, -1), new Point(1, -2, 0));
        double actual = -5.0;
        assertTrue(actual - expected < THRESHOLD);
    }
}
