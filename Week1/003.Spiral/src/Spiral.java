import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(640, 400);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);

        double scale = 5;
        double resolution = 0.0001;
        double n = 2; // dichtheid

        double old_x;
        double old_y;
        double x = 0;
        double y = 0;

        for (double r = 0; r < 3*Math.PI; r += resolution) {
            old_x = x;
            old_y = y;

            double o = n * r;

            x = n * o * Math.cos(o);
            y = n * o * Math.sin(o);

            graphics.draw(new Line2D.Double(old_x * scale, old_y * scale, x * scale, y * scale));
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
