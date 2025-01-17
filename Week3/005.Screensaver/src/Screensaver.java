import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Loop loop;

    @Override
    public void start(Stage stage) throws Exception {
        loop = new Loop(new Point[] {new Point(100, 100), new Point(300, 100), new Point( 100, 200), new Point(300, 200)});

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.setColor(Color.MAGENTA.darker());
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        loop.draw(graphics);
    }

    public void init() {

    }

    public void update(double deltaTime) {
        loop.update(canvas.getWidth(), canvas.getHeight());
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }

}
