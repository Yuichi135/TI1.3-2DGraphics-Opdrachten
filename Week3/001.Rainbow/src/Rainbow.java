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

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);

        graphics.setFont(new Font("Arial", Font.PLAIN, 64));

        char[] chars = "Regenboog".toCharArray();
        graphics.rotate(Math.PI * .5 - Math.PI * (1.0/(chars.length)));
        graphics.translate(0, 100);

        for (int i = 0; i < chars.length; i++) {
            graphics.setColor(Color.getHSBColor(i/(chars.length - 1f), 1, 1));
            graphics.translate(0, -100);
            graphics.rotate(Math.PI * (1.0/(chars.length)));
            graphics.translate(0, 100);
            graphics.rotate(Math.PI);
            graphics.drawChars(chars, i, 1, 0, 0);
            graphics.rotate(Math.PI);
        }
    }


    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
