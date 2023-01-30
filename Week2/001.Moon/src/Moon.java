import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.setTransform(affineTransform);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Ellipse2D moon = new Ellipse2D.Double(-50, -50, 100.0, 100.0);
        Ellipse2D moonGap = new Ellipse2D.Double(-80, -60, 100, 100.0);
        Area area = new Area(moon);
        area.subtract(new Area(moonGap));

        graphics.fill(area);
    }


    public static void main(String[] args)
    {
        launch(Moon.class);
    }

}
