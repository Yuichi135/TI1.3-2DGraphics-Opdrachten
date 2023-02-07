import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g, canvas.getWidth()/2, canvas.getHeight()/2), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();

        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        canvas.setOnMouseDragged(event -> draw(graphics, event.getX(), event.getY()));
    }


    public void draw(FXGraphics2D graphics, double x, double y) {
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        float radius = 250;
        float[] fractions = {0f, 0.5f, 1f};
        Color[] colors = {Color.ORANGE, Color.WHITE, Color.BLUE};

        Paint paint = new RadialGradientPaint((float) x,
                (float) y,
                radius,
                fractions,
                colors, MultipleGradientPaint.CycleMethod.REFLECT);


//        // Linear
//        paint = new LinearGradientPaint(0,
//                        0,
//                        (float) this.canvas.getWidth(),
//                        (float) this.canvas.getHeight(),
//                        fractions,
//                        colors);

        graphics.setPaint(paint);
        graphics.draw(new Rectangle2D.Double(0,
                0,
                this.canvas.getWidth(),
                this.canvas.getHeight()));
    }


    public static void main(String[] args) {
        launch(GradientPaintExercise.class);
    }

}
