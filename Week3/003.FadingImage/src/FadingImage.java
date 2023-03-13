import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.sun.imageio.plugins.jpeg.JPEGImageReaderSpi;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import sun.security.util.ArrayUtil;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage image[];
    private int currentIndex = 0;
    private double opacity = 0;
    private boolean x = false;
    private double time = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
//        URL url = new URL("https://api.api-ninjas.com/v1/randomimage");
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestProperty("Content-Type", "image/jpg");
//        con.setRequestProperty("Accept", "image/jpg");
//        // SSSHHHH dit zie je niet
//        con.setRequestProperty("X-Api-Key", "aaaaaaaaaaaaaaaaaaaaaaaa");
//        con.setRequestMethod("GET");
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
////        BufferedImage i = new BufferedImage(new ImageReader());
//        ArrayList<Byte> list = new ArrayList<>(10000);
//
//        while (reader.ready())
//            list.add((byte) reader.read());
//
//        byte[] imageInBytes = new byte[list.size()];
//        for(int i = 0; i < list.size(); i++) {
//            imageInBytes[i] = list.get(i).byteValue();
//        }
//        reader.close();
//        con.disconnect();
//
//        InputStream is = new ByteArrayInputStream(imageInBytes);
//        image = ImageIO.read(is);

        image = new BufferedImage[3];

        try {
            image[0] = ImageIO.read(new File("Week3/003.FadingImage/resources/images/image1.png"));
            image[1] = ImageIO.read(new File("Week3/003.FadingImage/resources/images/image2.jpg"));
            image[2] = ImageIO.read(new File("Week3/003.FadingImage/resources/images/image3.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();
        
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        graphics.drawImage(image[currentIndex], new AffineTransform(), null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) opacity));
        graphics.drawImage(image[(currentIndex + 1) % image.length], new AffineTransform(), null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    

    public void update(double deltaTime) {
        time += deltaTime;

        opacity += deltaTime * 0.1;
        if (opacity > 1) {
            opacity = 0;

            currentIndex = (currentIndex + 1) % image.length;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
