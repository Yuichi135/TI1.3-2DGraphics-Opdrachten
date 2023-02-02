import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
//        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {

        AffineTransform transform = new AffineTransform();
        transform.translate(150, 150);
        transform.scale(0.5, 0.5);
        graphics.setTransform(transform);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());


        graphics.setColor(Color.RED);
        graphics.draw(new Line2D.Double(-1000, 0, 1000, 0));
//        graphics.setColor(Color.GREEN);
//        graphics.draw(new Line2D.Double(0, -1000, 0, 1000));
        graphics.setColor(Color.BLACK);
        drawSquare(graphics);
        drawGraphLine(graphics);


        transform.concatenate(new AffineTransform(1, 0, 0, 1, 0, 0));

        graphics.setTransform(transform);
        drawSquare(graphics);
    }

    private void drawGraphLine(FXGraphics2D graphics) {
        double lastY = 0;
        for (int x = 0; x < 1000; x++) {
            double y = 2.5 * x;
            graphics.draw(new Line2D.Double(x, y, (x - 1), lastY));
            lastY = y;
        }
    }



    private void drawSquare(FXGraphics2D graphics) {
        graphics.draw(new Rectangle2D.Double(0, 150, 100, 100));
    }


    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
