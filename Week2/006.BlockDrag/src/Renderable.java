import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Renderable {
    private Shape shape;

    public Renderable(Canvas canvas, double size) {
        double maxHeight = canvas.getHeight() - size;
        double maxWidth = canvas.getWidth() - size;

        this.shape = new Rectangle2D.Double(Math.random() * maxWidth, Math.random() * maxHeight, size, size);
    }

    public double getX() {
        return this.shape.getBounds2D().getX();
    }

    public double getY() {
        return this.shape.getBounds2D().getY();
    }
}
