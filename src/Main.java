import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static String MEDIA_URL;

    @Override
    public void start(Stage primaryStage) {

        Media media = new Media(MEDIA_URL);
        Rectangle rectangle = new Rectangle(0, 0);

        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(1);

        primaryStage.setTitle("Video Player");
        Group root = new Group();
        Scene scene = new Scene(root, 640, 480 + 35);

        // create media player
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(primaryStage::sizeToScene);
        mediaPlayer.setAutoPlay(true);

        MediaControl mediaControl = new MediaControl(mediaPlayer, media, rectangle);
        scene.setRoot(mediaControl);
        mediaControl.getChildren().add(rectangle);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        MEDIA_URL = args[0];
        launch(args);
    }
}
