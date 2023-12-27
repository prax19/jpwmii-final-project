package com.jpwmii;

import com.jpwmii.ai.AI;
import com.jpwmii.entities.Ship;
import com.jpwmii.registers.AIRegistry;
import com.jpwmii.registers.EntityRegistry;
import com.jpwmii.utils.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Game extends Application {

    final private HashMap<String, String> properties = loadProperties("config.properties");
    private double screenWidth = 800;
    private double screenHeight = 600;
    private final String NAME = "JPWMII_9";
    private boolean developerMode;

    public InputManager inputManager;

    private EntityRegistry entityRegistry;
    private AIRegistry aiRegistry;
    private int points;
    private Text info;
    //FPS
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false;
    private Stage stage;
    private GraphicsContext gContext;
    private Ship ship;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setScene(scene);
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        gContext = canvas.getGraphicsContext2D();
        root.setCenter(canvas);
        stage.setTitle(NAME + " " + properties.get("game.version"));

        setDeveloperModeEnabled(false);

        info = new Text(5, 15, "Loading...");
        info.setFill(Color.WHITE);
        root.getChildren().add(info);

        root.widthProperty().addListener((obs, oldValue, newValue) -> {
            canvas.setWidth(newValue.doubleValue());
            setScreenWidth(newValue.doubleValue());
        });

        root.heightProperty().addListener((obs, oldValue, newValue) -> {
            canvas.setHeight(newValue.doubleValue());
            setScreenHeight(newValue.doubleValue());
        });

        inputManager = new InputManager(this);

        points = 0;

        entityRegistry = new EntityRegistry(this);
        aiRegistry = new AIRegistry(this);

        Sprite background = new Sprite(this, "background/background.png");
        background.position.set(400, 300);

        createNewPlayer();
        createNewEnemy();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {

                long oldFrameTime = frameTimes[frameTimeIndex] ;
                frameTimes[frameTimeIndex] = l;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
                if (frameTimeIndex == 0) {
                    arrayFilled = true ;
                }
                if (arrayFilled) {
                    long elapsedNanos = l - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    info.setText(String.format(
                            """
                                    %d points
                                    %d / %d HP
                                    %.3f FPS
                            """,
                            points, ship.getHealth(), ship.getHealthMax(), frameRate));
                }

                gContext.save();
                gContext.setFill(Color.rgb(40, 35, 35, 1));
                gContext.fillRect(0, 0, getScreenWidth(), getScreenHeight());
                gContext.restore();

                inputManager.update();
                entityRegistry.update();
                aiRegistry.update();

            }
        };

        gameLoop.start();
        stage.show();

    }

    public void createNewEnemy() {
        Ship ship = new Ship(this, 200, 200, "Ship_enemy");
        AI ai = new AI(ship);
        aiRegistry.register(ai);
        entityRegistry.register(ship);
    }

    public void createNewPlayer() {
        if(Objects.nonNull(ship))
            ship.remove();
        ship = new Ship(this, 100, 100, "Ship_player");
        entityRegistry.register(ship);
    }

    public void setDeveloperModeEnabled(boolean devModeEnabled) {
        this.developerMode = devModeEnabled;
        if(developerMode)
            stage.setTitle(NAME + " " + properties.get("game.version") + " | developer mode");
        else
            stage.setTitle(NAME + " " + properties.get("game.version"));
    }

    public boolean isDeveloperModeEnabled() {
        return developerMode;
    }

    private HashMap<String, String> loadProperties(String fileName) {
        HashMap<String, String> outputProperties = new HashMap<>();
        try {
            final Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
            for(Object key: properties.keySet())
                outputProperties.put(key.toString(), properties.getProperty(key.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputProperties;
    }

    public static void main(String[] args) {
        launch();
    }

    public EntityRegistry getEntityRegistry() {
        return entityRegistry;
    }

    public Stage getStage() {
        return this.stage;
    }

    public double getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(double screenWidth) {
        this.screenWidth = screenWidth;
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(double screenHeight) {
        this.screenHeight = screenHeight;
    }

    public GraphicsContext getGraphicsContext() {
        return gContext;
    }

    public Ship getPlayersShip() {
        return ship;
    }
}
