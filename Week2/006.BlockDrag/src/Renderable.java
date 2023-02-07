import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Renderable {
    private Point2D coords;
    private FXGraphics2D graphics;
    private double size;
    private Color color;

    public Renderable(ResizableCanvas canvas, FXGraphics2D graphics, double size) {
        double maxHeight = canvas.getHeight();
        double maxWidth = canvas.getWidth();

        this.color = Color.getHSBColor((float) Math.random(), 1, 1);
        this.size = size;
        this.graphics = graphics;
        this.coords = new Point2D.Double(Math.random() * maxWidth, Math.random() * maxHeight);
    }

    public boolean contains(double x, double y) {
        return this.createShape().contains(x, y);
    }

    public Point2D getCoords() {
        return this.coords;
    }

    public void setCoords(Point2D point) {
        this.coords = point;
    }

    public void draw() {
        Shape shape = this.createShape();
        this.graphics.setColor(this.color);
        this.graphics.fill(shape);
        this.graphics.setColor(Color.BLACK);
        this.graphics.draw(shape);
    }

    private Shape createShape() {
        return new Rectangle2D.Double(this.coords.getX() - (this.size / 2), this.coords.getY() - (this.size / 2), this.size, this.size);
    }




}
