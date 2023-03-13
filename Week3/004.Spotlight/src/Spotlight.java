import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private ArrayList<Line2D> lines;
    private ArrayList<Color> colors;
    private Point2D clipPosition = new Point2D.Double(200, 200);

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();


        int amount = 1000;
        lines = new ArrayList<>(amount);
        colors = new ArrayList<>(amount);

        for (int i = 0; i < amount; i++) {
            double x = Math.random() * canvas.getWidth();
            double y = Math.random() * canvas.getHeight();
            double xe = Math.random() * canvas.getWidth();
            double ye = Math.random() * canvas.getHeight();

            lines.add(new Line2D.Double(x, y, xe, ye));
            colors.add(Color.getHSBColor((float) Math.random(), 1, 1));
        }

        draw(g2d);

        canvas.setOnMouseDragged(event -> {
            clipPosition.setLocation(event.getX(), event.getY());
            draw(g2d);
        });
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        if (lines == null)
            return;

        Shape shape = new Ellipse2D.Double(clipPosition.getX()-100, clipPosition.getY()-100, 200, 200);
        graphics.clip(shape);


        for (int i = 0; i < lines.size(); i++) {
            graphics.setColor(colors.get(i));
            graphics.draw(lines.get(i));
        }
        graphics.setClip(null);
    }

    public void update(double deltaTime)
    {

    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
