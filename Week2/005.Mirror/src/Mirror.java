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
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(150, 150);

        // Reference X line
        graphics.setColor(Color.RED);
        graphics.draw(new Line2D.Double(-1000, 0, 1000, 0));

        // Originele rechthoek
        graphics.draw(this.getShape());

        double k = 1;
        double shear = (2 * k) / (1 + (k * k));
        double xScale = (2 / (1 + (k * k))) - 1;
        double yScale = ((2 * (k * k)) / (1 + (k * k))) - 1;

//        System.out.println("Shear: " + shear);
//        System.out.println("xScale: " + xScale);
//        System.out.println("yScale: " + yScale);

        // y = k*x lijn
        graphics.draw(new Line2D.Double(0, 0, 1000, (1000 * k)));

        AffineTransform transform = new AffineTransform(xScale, shear, shear, yScale, 0, 0);
        graphics.draw(transform.createTransformedShape(this.getShape()));
    }

    public Shape getShape() {
//        return new Rectangle2D.Double(0, 150, 100, 100);
        return YingYang.getShape();
    }

    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
