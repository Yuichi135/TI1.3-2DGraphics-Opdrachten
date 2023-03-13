import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class MovingCharacter extends Application {
    public static final byte IDLE = 0;
    public static final byte WALK = 1;
    public static final byte RUN = 2;
    public static final byte FALL = 3;
    public static final byte JUMP = 4;
    public static final byte HOVER = 5;
    public static final byte LAND = 6;
    private ResizableCanvas canvas;
    private BufferedImage image;
    private BufferedImage[] idle;
    private BufferedImage[] run;
    private BufferedImage[] walk;
    private BufferedImage[] jump;
    private BufferedImage[] hover;
    private BufferedImage[] land;
    private BufferedImage[] fall;
    private BufferedImage[][] animations;
    private int activeAnimation = IDLE;
    private int activeImage = 0;
    private boolean finishedAnimation;
    private double frame;
    private double fps = 12;
    private boolean facingRight = true;
    private Vector2D position = new Vector2D(0, 100);


    @Override
    public void start(Stage stage) throws Exception {

        try {
            image = ImageIO.read(new File("Week3/002.MovingCharacter/resources/images/sprite.png"));
            idle = new BufferedImage[4];
            walk = new BufferedImage[8];
            run = new BufferedImage[8];
            fall = new BufferedImage[8];
            jump = new BufferedImage[6];
            hover = new BufferedImage[8];
            land = new BufferedImage[4];

            // idle
            for (int i = 0; i < idle.length; i++) {
                idle[i] = image.getSubimage(64 * (i % 8), 64 * (i / 8), 64, 64);
            }

            // walk
            for (int i = 0; i < walk.length; i++) {
                int j = i + 32;
                walk[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            // run
            for (int i = 0; i < run.length; i++) {
                int j = i + 4;
                run[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            // fall
            for (int i = 0; i < fall.length; i++) {
                int j = i + 16;
                fall[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            // jump
            for (int i = 0; i < jump.length; i++) {
                int j = i + 43;
                jump[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            // hover
            for (int i = 0; i < hover.length; i++) {
                int j = i + 48;
                hover[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            // land
            for (int i = 0; i < land.length; i++) {
                int j = i + 13;
                land[i] = image.getSubimage(64 * (j % 8), 64 * (j / 8), 64, 64);
            }

            animations = new BufferedImage[7][];

            animations[0] = idle;
            animations[1] = walk;
            animations[2] = run;
            animations[3] = fall;
            animations[4] = jump;
            animations[5] = hover;
            animations[6] = land;

        } catch (IOException e) {
            e.printStackTrace();
        }

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);

        canvas.requestFocus();
        canvas.setOnKeyPressed(e -> {
            int newAnimation = -1;
            switch (e.getCode()) {
                case LEFT:
                    newAnimation = WALK;
                    facingRight = false;
                    break;
                case RIGHT:
                    facingRight = true;
                    newAnimation = WALK;
                    break;
                case UP:
                    newAnimation = JUMP;
                    break;
                case DOWN:
                    newAnimation = FALL;
                    break;
                case SPACE:
                    newAnimation = IDLE;
                    break;
                case SHIFT:
                    newAnimation = RUN;
                    break;
                case R:
                    finishedAnimation = false;
                    newAnimation = IDLE;
            }

            if (newAnimation != -1 && newAnimation != activeAnimation && !finishedAnimation) {
                activeAnimation = newAnimation;
                activeImage = 0;
                updateActiveImage();
            }
        });
    }


    public void draw(FXGraphics2D graphics) {
        AffineTransform transform = new AffineTransform();
        graphics.setTransform(transform);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        transform.translate(position.getX(), position.getY());
        if (!facingRight) {
            transform.scale(-1, 1);
            transform.translate(-64, 0);
        }

        graphics.drawImage(animations[activeAnimation][activeImage], transform, null);
    }


    public void update(double deltaTime) {
        frame += deltaTime;
        if (frame > 1 / fps) {
            frame = frame % 1 / fps;
            updateActiveImage();
            updatePosition();
        }
    }

    private void updateActiveImage() {
        if (finishedAnimation)
            return;

        int i = (activeImage + 1) % animations[activeAnimation].length;

        if (i == 0) {
            if (activeAnimation == JUMP)
                activeAnimation = LAND;
            else if (activeAnimation == LAND)
                activeAnimation = IDLE;
            else if (activeAnimation == FALL) {
                finishedAnimation = true;
                return;
            }
        }
        activeImage = i;
    }

    private void updatePosition() {
        int x = 0;
        if (activeAnimation == WALK)
            x = 3;
        if (activeAnimation == RUN)
            x = 6;

        if (position.getX() + 64 > canvas.getWidth() || position.getX() < 0)
            facingRight = !facingRight;

        if (!facingRight)
            x = -x;

        position.addX(x);
    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

}
