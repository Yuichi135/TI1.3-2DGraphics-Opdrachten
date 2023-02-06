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

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    private Rectangle2D.Double[] shapes;
    private Color[] colors;
    private int activeShape = -1;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e, new FXGraphics2D(canvas.getGraphicsContext2D())));

        createShapes();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        if (this.shapes == null || this.colors == null)
            return;

        for (int i = 0; i < 10; i++) {
            graphics.setColor(colors[i]);
            graphics.fill(this.shapes[i]);
        }
    }


    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (int i = 0; i < this.shapes.length; i++) {
            if ((e.getX() >= this.shapes[i].getBounds2D().getX() && e.getX() <= this.shapes[i].getBounds2D().getX() + this.shapes[i].getBounds2D().getWidth())
                    && (e.getY() >= this.shapes[i].getBounds2D().getY() && e.getY() <= this.shapes[i].getBounds2D().getY() + this.shapes[i].getBounds2D().getHeight())) {
                this.activeShape = i;
            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        this.activeShape = -1;
    }

    private void mouseDragged(MouseEvent e, FXGraphics2D graphics) {
        if (this.activeShape == -1)
            return;

        Rectangle2D.Double activeElement = this.shapes[this.activeShape];

        double xOffset = e.getX() - activeElement.getX();
        double yOffset = e.getY() - activeElement.getY();

//        System.out.println();
//        System.out.println("MouseX:\t" + e.getX());
//        System.out.println("SquareX:\t" + activeElement.getX());
//        System.out.println("Offset:\t" + xOffset);
//        System.out.println("temp\t:" + (activeElement.getX() - xOffset + 50));

        activeElement.setRect(e.getX(),  e.getY(), activeElement.getWidth(), activeElement.getHeight());

        draw(graphics);
    }

    private void createShapes() {
        this.shapes = new Rectangle2D.Double[10];
        this.colors = new Color[10];
        double squareSize = 50;
        double maxHeight = this.canvas.getHeight() - squareSize;
        double maxWidth = this.canvas.getWidth() - squareSize;


        for (int i = 0; i < 10; i++) {
            this.shapes[i] = new Rectangle2D.Double(Math.random() * maxWidth, Math.random() * maxHeight, squareSize, squareSize);
            this.colors[i] = Color.getHSBColor((float) Math.random(), 1, 1);
        }
    }
}
