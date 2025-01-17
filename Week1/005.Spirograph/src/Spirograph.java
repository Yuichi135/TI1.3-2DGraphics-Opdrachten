import java.awt.*;
import java.awt.geom.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private final double PHI = (1 + Math.sqrt(5)) / 2;

    private Canvas canvas;
    private TextField radiusBig;
    private TextField rotateSpeedBig;
    private TextField radiusSmall;
    private TextField rotateSpeedSmall;
    private double a;
    private double b;
    private double c;
    private double d;
    private double theta;
    private final double stepSize = 0.001;
    private final double scale = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Poep code want ik heb dit voor de lessen gemaakt en ik weet niet wat ik doe
        this.canvas = new Canvas(900, 400);
        primaryStage.setScene(new Scene(this.createTopbar()));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }

    private VBox createTopbar() {
        FXGraphics2D fxGraphics2D = new FXGraphics2D(canvas.getGraphicsContext2D());
        AffineTransform originalTransform = fxGraphics2D.getTransform();
        fxGraphics2D.setBackground(Color.WHITE);
        fxGraphics2D.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        fxGraphics2D.scale(.2, .2);
        AffineTransform newTransform = fxGraphics2D.getTransform();

        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));

        Button generateButton = new Button("Generate");
        Button randomButton = new Button("Random");
        Button clearButton = new Button("Clear");

        topBar.getChildren().add(radiusBig = new TextField("200"));
        topBar.getChildren().add(rotateSpeedBig = new TextField("1"));
        topBar.getChildren().add(radiusSmall = new TextField("50"));
        topBar.getChildren().add(rotateSpeedSmall = new TextField("10"));
        topBar.getChildren().add(randomButton);
        topBar.getChildren().add(generateButton);
        topBar.getChildren().add(clearButton);

        generateButton.setOnAction(event -> {
            setVariables(false);
            Timeline continuousUpdate = new Timeline(new KeyFrame(Duration.millis(1), (ActionEvent e) -> this.drawPart(fxGraphics2D)));
            continuousUpdate.setCycleCount(50000);
            continuousUpdate.play();

        });
        randomButton.setOnAction(event -> {
            setVariables(true);
            Timeline continuousUpdate = new Timeline(new KeyFrame(Duration.millis(1), (ActionEvent e) -> this.drawPart(fxGraphics2D)));
            continuousUpdate.setCycleCount(50000);
            continuousUpdate.play();
        });
        clearButton.setOnAction(event -> {
//            new FXGraphics2D(canvas.getGraphicsContext2D()).clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
            fxGraphics2D.setTransform(originalTransform);
            fxGraphics2D.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
            fxGraphics2D.setTransform(newTransform);
        });


        return mainBox;
    }

    public void draw(FXGraphics2D graphics, boolean isRandom) {
        this.setVariables(isRandom);

        graphics.setColor(Color.getHSBColor((float) Math.random() * 1, 1, 1));
        double old_x = a + c;
        double old_y = 0;
        double x;
        double y;

        for (double theta = 0; theta < (2 * Math.PI) * (2 * Math.PI); theta += stepSize) {
            x = formulaX(a, b, c, d, theta);
            y = formulaY(a, b, c, d, theta);

            graphics.draw(new Line2D.Double(old_x * scale, old_y * scale, x * scale, y * scale));
            old_x = x;
            old_y = y;
        }
    }

    private double formulaX(double a, double b, double c, double d, double theta) {
        return a * Math.cos(b * theta) + c * Math.cos(d * theta);
    }

    private double formulaY(double a, double b, double c, double d, double theta) {
        return a * Math.sin(b * theta) + c * Math.sin(d * theta);
    }

    public void drawHypotrochoid(FXGraphics2D graphics, boolean random) {
        // gegeven formule niet gebruikt (wtf is a, b, c, d en φ)
//        x = a × cos(b × φ) + c × cos(d × φ);
//        y = a × sin(b × φ) + c × sin(d × φ);
        // Andere formule gebruikt van https://mathworld.wolfram.com/Hypotrochoid.html en https://en.wikipedia.org/wiki/Hypotrochoid

        double a;
        double b;
        double p;
        double steps;
        double h;

        if (random) {
            a = (Math.random() * 500);
            b = (Math.random() * 500);
            p = Math.random() * 500;
            // Aantal stappen om een complete cirkel te maken
            steps = 2 * Math.PI * (kgv((int) (a * 100), (int) (b * 100)) / (a * 10000));
            System.out.println(steps);
            h = p;
        } else {
            a = Double.parseDouble(this.radiusBig.getText());
            b = Double.parseDouble(this.rotateSpeedBig.getText());
            p = Double.parseDouble(this.radiusSmall.getText());
            steps = Double.parseDouble(this.rotateSpeedSmall.getText());
            h = p;
        }

        graphics.setColor(Color.getHSBColor((float) Math.random() * 1, 1, 1));

        double resolution = 0.1;
        double scale = 8;

        double old_x;
        double old_y;
        double x = (a - b) * Math.cos(0) + h * Math.cos((a - b) / b * 0);
        double y = (a - b) * Math.sin(0) - h * Math.sin((a - b) / b * 0);

        for (double t = 0; t < steps; t += resolution) {
            old_x = x;
            old_y = y;

            x = (a - b) * Math.cos(t) + h * Math.cos((a - b) / b * t);
            y = (a - b) * Math.sin(t) - h * Math.sin((a - b) / b * t);

            graphics.draw(new Line2D.Double(old_x * scale, old_y * scale, x * scale, y * scale));
        }
    }


    public static void main(String[] args) {
        launch(Spirograph.class);
    }

    // KGV van internet
    public static int kgv(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    private void setVariables(boolean isRandom) {
        if (isRandom) {
            this.a = (Math.random() * 500);
            this.b = (Math.random() * 49) + 1;
            this.c = (Math.random() * 500);
            this.d = (Math.random() * 49) + 1;
            this.radiusBig.setText(Double.toString(a));
            this.rotateSpeedBig.setText(Double.toString(b));
            this.radiusSmall.setText(Double.toString(c));
            this.rotateSpeedSmall.setText(Double.toString(d));
        } else {
            this.a = Double.parseDouble(this.radiusBig.getText());
            this.b = Double.parseDouble(this.rotateSpeedBig.getText());
            this.c = Double.parseDouble(this.radiusSmall.getText());
            this.d = Double.parseDouble(this.rotateSpeedSmall.getText());
        }
        this.theta = 0;
    }
    public void drawPart(FXGraphics2D graphics) {
        theta += stepSize;

        graphics.setColor(Color.getHSBColor((float) theta/10, 1, 1));

        double old_x = formulaX(a, b, c, d, theta - stepSize);
        double old_y = formulaY(a, b, c, d, theta - stepSize);
        double x = formulaX(a, b, c, d, theta);
        double y = formulaY(a, b, c, d, theta);

        graphics.draw(new Line2D.Double(old_x * scale, old_y * scale, x * scale, y * scale));
    }
}
