import org.jfree.fx.FXGraphics2D;

import java.awt.geom.GeneralPath;
import java.util.LinkedList;

public class Loop {
    private Point[] points;
    private LinkedList<GeneralPath> paths;

    public Loop(Point[] points) {
        this.points = points;
        this.paths = new LinkedList<>();

        for (int i = 0; i < 50; i++) {
            paths.add(getPath());
        }
    }

    public void update(double maxWidth, double maxHeight) {
        for (Point point : points) {
            point.update(maxWidth, maxHeight);
        }

        paths.add(getPath());
        paths.removeFirst();
    }

    public void draw(FXGraphics2D g) {
        for (GeneralPath path : paths) {
            g.draw(path);
        }
    }

    private GeneralPath getPath() {
        GeneralPath path = new GeneralPath();
        path.moveTo(points[0].getPosition().getX(), points[0].getPosition().getY());

        for (Point point : points) {
            Vector2D position = point.getPosition();

            path.lineTo(position.getX(), position.getY());
        }

        path.closePath();

        return path;
    }
}
