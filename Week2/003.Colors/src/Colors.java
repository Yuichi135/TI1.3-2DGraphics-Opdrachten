import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        int x = 0;
        graphics.setPaint(Color.BLACK);
        Rectangle2D black = new Rectangle(x, 0, 50, 50);
        graphics.fill(black);

        x += 50;
        graphics.setPaint(Color.BLUE);
        Rectangle2D blue = new Rectangle(x, 0, 50, 50);
        graphics.fill(blue);

        x += 50;
        graphics.setPaint(Color.CYAN);
        Rectangle2D cyan = new Rectangle(x, 0, 50, 50);
        graphics.fill(cyan);

        x += 50;
        graphics.setPaint(Color.DARK_GRAY);
        Rectangle2D darkGray = new Rectangle(x, 0, 50, 50);
        graphics.fill(darkGray);

        x += 50;
        graphics.setPaint(Color.GRAY);
        Rectangle2D gray = new Rectangle(x, 0, 50, 50);
        graphics.fill(gray);

        x += 50;
        graphics.setPaint(Color.GREEN);
        Rectangle2D green = new Rectangle(x, 0, 50, 50);
        graphics.fill(green);

        x += 50;
        graphics.setPaint(Color.LIGHT_GRAY);
        Rectangle2D lightGray = new Rectangle(x, 0, 50, 50);
        graphics.fill(lightGray);

        x += 50;
        graphics.setPaint(Color.MAGENTA);
        Rectangle2D magenta = new Rectangle(x, 0, 50, 50);
        graphics.fill(magenta);

        x += 50;
        graphics.setPaint(Color.ORANGE);
        Rectangle2D orange = new Rectangle(x, 0, 50, 50);
        graphics.fill(orange);

        x += 50;
        graphics.setPaint(Color.PINK);
        Rectangle2D pink = new Rectangle(x, 0, 50, 50);
        graphics.fill(pink);

        x += 50;
        graphics.setPaint(Color.RED);
        Rectangle2D red = new Rectangle(x, 0, 50, 50);
        graphics.fill(red);

        x += 50;
        graphics.setPaint(Color.WHITE);
        Rectangle2D white = new Rectangle(x, 0, 50, 50);
        graphics.fill(white);

        x += 50;
        graphics.setPaint(Color.YELLOW);
        Rectangle2D yellow = new Rectangle(x, 0, 50, 50);
        graphics.fill(yellow);
    }


    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
