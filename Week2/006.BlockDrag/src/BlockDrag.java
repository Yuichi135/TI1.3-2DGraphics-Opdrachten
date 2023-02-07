import java.awt.*;
import java.awt.geom.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    private Renderable[] renderables;
    private int activeShape = -1;
    private final int amountOfShapes = 10;
    private FXGraphics2D graphics;
    private double xOffset;
    private double yOffset;

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
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        createShapes();
        draw(graphics);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        if (this.renderables == null)
            return;

        update();
    }


    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (int i = 0; i < this.renderables.length; i++) {
            if (this.renderables[i].contains(e.getX(), e.getY()))
                this.activeShape = i;
        }

        if (this.activeShape == -1)
            return;

        Point2D point = this.renderables[this.activeShape].getCoords();

        this.xOffset = point.getX() - e.getX();
        this.yOffset = point.getY() - e.getY();
    }

    private void mouseReleased(MouseEvent e) {
        this.activeShape = -1;
    }

    private void mouseDragged(MouseEvent e) {
        if (this.activeShape < 0 || this.activeShape >=   this.amountOfShapes)
            return;

        this.renderables[this.activeShape].setCoords(new Point2D.Double(e.getX() + xOffset, e.getY() + yOffset));

        draw(graphics);
    }

    private void update() {
        for (Renderable renderable : renderables) {
            renderable.draw();
        }
    }

    private void createShapes() {
        this.renderables = new Renderable[this.amountOfShapes];

        for (int i = 0; i < amountOfShapes; i++) {
            this.renderables[i] = new Renderable(canvas, graphics, 50);
        }
    }
}
