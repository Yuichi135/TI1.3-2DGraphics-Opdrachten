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

public class YingYang extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Ying Yang");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        AffineTransform affineTransform = new AffineTransform();
//        affineTransform.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.setTransform(affineTransform);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area yingYang = new Area();

        Ellipse2D circle = new Ellipse2D.Double(0, 0, 500, 500);
        Ellipse2D topCircle = new Ellipse2D.Double(125, 0, 250, 250);
        Ellipse2D bottomCircle = new Ellipse2D.Double(125, 250, 250, 250);
        Rectangle rightHalf = new Rectangle(250, 0, 250, 500);

        Ellipse2D topSmallCircle = new Ellipse2D.Double(225, 100, 50, 50);
        Ellipse2D bottomSmallCircle = new Ellipse2D.Double(225, 350, 50, 50);

        yingYang.add(new Area(circle));
        yingYang.subtract(new Area(topCircle));
        yingYang.subtract(new Area(rightHalf));
        yingYang.add(new Area(bottomCircle));
        yingYang.add(new Area(topSmallCircle));
        yingYang.subtract(new Area(bottomSmallCircle));

        graphics.fill(yingYang);
        graphics.draw(circle);

//        graphics.setColor(Color.getHSBColor((float) Math.random(), 1, 1)); // epilepsy warning
    }


    public static void main(String[] args)
    {
        launch(YingYang.class);
    }

}
