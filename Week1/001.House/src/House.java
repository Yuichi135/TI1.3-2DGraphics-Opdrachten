import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(640, 400);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        // House
        graphics.drawLine(100, 100, 100, 200); // wall left
        graphics.drawLine(200, 100, 200, 200); // wall right
        graphics.drawLine(100, 200, 200, 200); // floor
        graphics.drawLine(100, 100, 150, 50); // roof left
        graphics.drawLine(200, 100, 150, 50); // roof right

        // Door
        graphics.drawLine(112, 200, 112, 150); // left part door
        graphics.drawLine(137, 200, 137, 150); // right part door
        graphics.drawLine(112, 150, 137, 150); // top part door

        // Window
        graphics.drawLine(150, 187, 150, 137); // left part window
        graphics.drawLine(187, 187, 187, 137); // right part window
        graphics.drawLine(150, 187, 187, 187); // bottom part window
        graphics.drawLine(150, 137, 187, 137); // bottom part window


    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
