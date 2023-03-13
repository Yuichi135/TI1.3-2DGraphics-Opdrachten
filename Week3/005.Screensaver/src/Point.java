import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Rectangle2D;
import java.util.Vector;

public class Point {
    private Vector2D position;
    private Vector2D direction;
    private double speed;

    public Point(Vector2D position) {
        this.position = position;
        this.direction = new Vector2D(Math.random() - 0.5, Math.random() - 0.5);
        this.direction.normalize();
        this.speed = Math.random() * 5 + 5;
    }

    public Point(double x, double y) {
        this(new Vector2D(x, y));
    }

    public Vector2D getPosition() {
        return position;
    }

    public void update(double maxWidth, double maxHeight) {
        double theta = direction.getDirection();

        double newX = position.getX() + speed * Math.cos(theta);
        double newY = position.getY() + speed * Math.sin(theta);

        if (newX > maxWidth || newX < 0) {
            direction.setX(-direction.getX());

            if (newX > maxWidth) {
                newX = maxWidth;
            } else {
                newX = 0;
            }
        }

        if (newY > maxHeight || newY < 0) {
            direction.setY(-direction.getY());

            if (newY > maxHeight) {
                newY = maxHeight;
            } else {
                newY = 0;
            }
        }

        position.setLocation(newX, newY);
    }

    public void draw(FXGraphics2D g) {
        g.fill(new Rectangle2D.Double(position.getX() - 5, position.getY() -5, 10, 10));
    }
}
