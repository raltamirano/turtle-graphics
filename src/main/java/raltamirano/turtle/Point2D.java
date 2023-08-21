package raltamirano.turtle;
import java.awt.*;

public class Point2D {
    public static final Point2D HOME = new Point2D(0.0, 0.0);

    private final double x;
    private final double y;

    private Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Point awtPoint(final Dimension dimension) {
        return new Point((int) (x + (dimension.getWidth() / 2)), (int) (-y + (dimension.getHeight() / 2)));
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
