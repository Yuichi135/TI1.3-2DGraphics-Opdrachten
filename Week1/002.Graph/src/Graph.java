import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Graph extends Application {
    private Canvas canvas;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.canvas = new Canvas(640, 400);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
//        graphics.scale( 1, -1);

        graphics.setColor(Color.RED);
        graphics.draw(new Line2D.Double(-1000, 0, 1000, 0));
        graphics.setColor(Color.GREEN);
        graphics.draw(new Line2D.Double(0, -1000, 0, 1000));
        graphics.setColor(Color.BLACK);

        double resolution = 0.0001;
        double scale = 50.0;
        double lastY = -10 * -10 * -10;

        Long start = System.nanoTime();

        for(double x = -10; x < 10; x += resolution)
        {
            // Pow is traag (200001), time in nanos
            double y = x * x * x; // 38137000, 34192500, 35649600, 36339700, 35290900
//            double y = Math.pow(x, 3); // 43370400, 41599100, 43204400, 43786000, 42949400
            graphics.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
            lastY = y;
        }

        Long end = System.nanoTime();
        System.out.println("Total time: " + (end - start));
    }
    
    
    
    public static void main(String[] args) {
        launch(Graph.class);
    }

}
