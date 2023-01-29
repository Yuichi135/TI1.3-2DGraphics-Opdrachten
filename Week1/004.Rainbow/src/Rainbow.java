import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(640, 400);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.scale(1, -1);

        double resolution = 0.1;
        int radiusBinnen = 2;
        int radiusBuiten = 3;
        int scale = 50;

        for (double i = 0; i < 100; i += resolution) {
            double hoek = Math.PI * (i / 100.0);

            graphics.setColor(Color.getHSBColor((float) i/100, 1, 1));
            double x1 = radiusBinnen * Math.cos(hoek) * scale;
            double y1 = radiusBinnen * Math.sin(hoek) * scale;
            double x2 = radiusBuiten * Math.cos(hoek) * scale;
            double y2 = radiusBuiten * Math.sin(hoek) * scale;

            graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }


    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
