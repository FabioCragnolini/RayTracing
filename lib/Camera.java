package lib;

public class Camera {
    private Point lookFrom;
    private Point lookAt;
    private Point upDir;
    private double verticalFOV; // degrees
    private int xDim;
    private int yDim;
    private double ratio;

    public Camera(Point lookFrom, Point lookAt, double verticalFOV, int xDim, int yDim) {
        this.lookFrom = lookFrom;
        this.lookAt = lookAt;
        this.upDir = new Point(0, 1, 0);
        this.verticalFOV = verticalFOV;
        this.xDim = xDim;
        this.yDim = yDim;
        this.ratio = this.xDim / (double) this.yDim;
    }

    public int getxDim() {
        return xDim;
    }

    public int getyDim() {
        return yDim;
    }

    public void move(Point lookFrom) {
        this.lookFrom = lookFrom;
    }

    public void focus(Point lookAt) {
        this.lookAt = lookAt;
    }

    public Ray getRay(int i, int j) {
        // create camera coordinate system (u,v,w)
        Point w = Point.diff(lookFrom, lookAt).normalize();
        Point u = Point.cross(upDir, w).normalize();
        Point v = Point.cross(w, u);

        double fov = 1.0 / Math.tan(Math.toRadians(verticalFOV / 2.0));
        Point horizontal = Point.scalar(2.0, u);
        Point vertical = Point.scalar(2.0 / ratio, v);
        Point offset = Point.diff(
                Point.diff(Point.diff(lookFrom, Point.scalar(0.5, horizontal)), Point.scalar(0.5, vertical)),
                Point.scalar(fov, w));
        Ray r = new Ray(lookFrom, Point.diff(Point.sum(Point.sum(offset, Point.scalar(i / (double) xDim, horizontal)),
                Point.scalar(j / (double) yDim, vertical)), lookFrom));
        return r;
    }
}
