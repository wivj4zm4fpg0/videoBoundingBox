import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String MEDIA_URL =
            "file:///C:/Users/智/Documents/shoplifting/HsaTjpESyDU.mp4";

    static Media media = new Media(MEDIA_URL);
    static Rectangle rectangle = new Rectangle(0, 0);

    @Override
    public void start(Stage primaryStage) {

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

        MediaControl mediaControl = new MediaControl(mediaPlayer);
        scene.setRoot(mediaControl);
        mediaControl.getChildren().add(rectangle);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
