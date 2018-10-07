import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

class MediaControl extends BorderPane {

    private MediaPlayer mp;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private double mediaWidth;
    private double mediaHeight;

    MediaControl(final MediaPlayer mp, Media media, Rectangle rectangle, Stage stage) {
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        MediaView mediaView = new MediaView(mp);
        mediaView.setPreserveRatio(true);
        Pane mvPane = new Pane();
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        HBox mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

        final Button playButton = new Button(">");

        playButton.setOnAction(e -> {
            Status status = mp.getStatus();

            if (status == Status.UNKNOWN || status == Status.HALTED) {
                // don't do anything in these states
                return;
            }

            if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                // rewind the movie if we're sitting at the end
                if (atEndOfMedia) {
                    mp.seek(mp.getStartTime());
                    atEndOfMedia = false;
                }
                mp.play();
            } else {
                mp.pause();
            }
        });
        mp.currentTimeProperty().addListener(ov -> {
            mediaView.setFitWidth(mvPane.getWidth());
            mediaView.setFitHeight(mvPane.getHeight());
            if (mvPane.getHeight() / mvPane.getWidth() < mediaHeight / mediaWidth) {
                mediaView.setX((mvPane.getWidth() - mvPane.getHeight() * mediaWidth / mediaHeight) / 2);
            } else {
                mediaView.setX(0);
            }
            if (mvPane.getWidth() / mvPane.getHeight() < mediaWidth / mediaHeight) {
                mediaView.setY((mvPane.getHeight() - mvPane.getWidth() * mediaHeight / mediaWidth) / 2);
            } else {
                mediaView.setY(0);
            }
            updateValues();
        });

        mp.setOnPlaying(() -> {
            if (stopRequested) {
                mp.pause();
                stopRequested = false;
            } else {
                playButton.setText("||");
            }
        });

        mp.setOnPaused(() -> playButton.setText(">"));

        mp.setOnReady(() -> {
            duration = mp.getMedia().getDuration();
            mediaWidth = media.getWidth();
            mediaHeight = media.getHeight();
            stage.setWidth(mediaWidth + 14);
            stage.setHeight(mediaHeight + 72);
            updateValues();
            System.out.println("\nWidth = " + mediaWidth + ", Height = " + mediaHeight +
                    ", Time = " + media.getDuration().toSeconds() + ", Path = " + media.getSource());
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(() -> {
            if (!repeat) {
                playButton.setText(">");
                stopRequested = true;
                atEndOfMedia = true;
            }
        });

        mediaBar.getChildren().add(playButton);
        // Add spacer
        Label spacer = new Label(" ");
        mediaBar.getChildren().add(spacer);

        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(ov -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
            }
        });
        mediaBar.getChildren().add(timeSlider);

        // Add Play label
        playTime = new Label();
        playTime.setMinWidth(65);
        mediaBar.getChildren().add(playTime);

        setBottom(mediaBar);

        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        mediaView.setOnMousePressed(event -> {
            x.set(event.getX());
            y.set(event.getY());
            rectangle.setWidth(0);
            rectangle.setHeight(0);
        });
        mediaView.setOnMouseReleased(event -> System.out.println("x = " + (int) rectangle.getX() +
                ", y = " + (int) rectangle.getY() + ", width = " + (int) rectangle.getWidth() +
                ", height = " + (int) rectangle.getHeight()));
        mediaView.setOnMouseDragged(event -> {
            if (event.getX() > x.get()) {
                rectangle.setX(x.get());
                rectangle.setWidth(event.getX() - rectangle.getX());
            } else {
                rectangle.setX(event.getX());
                rectangle.setWidth(x.get() - event.getX());
            }
            if (event.getY() > y.get()) {
                rectangle.setY(y.get());
                rectangle.setHeight(event.getY() - rectangle.getY());
            } else {
                rectangle.setY(event.getY());
                rectangle.setHeight(y.get() - event.getY());
            }

            if (rectangle.getY() + rectangle.getHeight() > mvPane.getHeight()) {
                rectangle.setHeight(mvPane.getHeight() - rectangle.getY());
            }
            if (rectangle.getY() < 0) {
                rectangle.setY(0);
                rectangle.setHeight(y.get());
            }
            if (rectangle.getX() + rectangle.getWidth() > mvPane.getWidth()) {
                rectangle.setWidth(mvPane.getWidth() - rectangle.getX());
            }
            if (rectangle.getX() < 0) {
                rectangle.setX(0);
                rectangle.setWidth(x.get());
            }
        });
    }

    private void updateValues() {
        if (playTime != null && timeSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mp.getCurrentTime();
                playTime.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration.toSeconds()).toMillis() / 10);
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }
}