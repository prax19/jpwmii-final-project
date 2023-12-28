package com.jpwmii;

import com.jpwmii.ai.AI;
import com.jpwmii.background.Background;
import com.jpwmii.entities.Ship;
import com.jpwmii.registers.AIRegistry;
import com.jpwmii.registers.EntityRegistry;
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
import java.util.Random;

public class Game extends Application {

    private final String NAME;
    private final String VERSION;
    private final BorderPane ROOT;
    private final Canvas CANVAS;
    final private HashMap<String, String> PROPERTIES;

    private double screenWidth;
    private double screenHeight;
    private boolean developerMode;

    public InputManager inputManager;

    private EntityRegistry entityRegistry;
    private AIRegistry aiRegistry;
    private Text info;

    //FPS
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false;
    private int animationTime;
    private Stage STAGE;
    private Scene SCENE;
    private final GraphicsContext gContext;
    private Ship ship;

    private Background background;

    public Game() {
        PROPERTIES = loadProperties("config.properties");
        NAME = PROPERTIES.get("game.name");
        VERSION = PROPERTIES.get("game.version");
        screenWidth = Double.parseDouble(PROPERTIES.get("game.defaults.screen.width"));
        screenHeight = Double.parseDouble(PROPERTIES.get("game.defaults.screen.height"));

        this.ROOT = new BorderPane();
        this.SCENE = new Scene(ROOT);
        this.CANVAS = new Canvas(screenWidth, screenHeight);
        gContext = CANVAS.getGraphicsContext2D();
        ROOT.setCenter(CANVAS);

        animationTime = 0;
    }

    @Override
    public void start(Stage stage) {
        this.STAGE = stage;
        STAGE.setScene(SCENE);
        STAGE.setTitle(NAME + " " + VERSION);

        setDeveloperModeEnabled(Boolean.parseBoolean(PROPERTIES.get("game.defaults.devmode")));

        info = new Text(5, 15, "Loading...");
        info.setFill(Color.WHITE);
        ROOT.getChildren().add(info);

        background = new Background(this);

        ROOT.widthProperty().addListener((obs, oldValue, newValue) -> {
            CANVAS.setWidth(newValue.doubleValue());
            setScreenWidth(newValue.doubleValue());
            background.compose();
        });

        ROOT.heightProperty().addListener((obs, oldValue, newValue) -> {
            CANVAS.setHeight(newValue.doubleValue());
            setScreenHeight(newValue.doubleValue());
            background.compose();
        });

        inputManager = new InputManager(this);

        entityRegistry = new EntityRegistry(this);
        aiRegistry = new AIRegistry(this);
//        Sprite background = new Sprite(this, "background/background.png");
//        background.position.set(400, 300);

        createNewPlayer();

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
                            ship.getScore(), ship.getHealth(), ship.getHealthMax(), frameRate));
                }

                // Random enemy generation
                // Max: 3
                // 20% for spawn new enemy every second
                if(animationTime == 30) {
                    int randomNumber = new Random().nextInt(100);
                    if (randomNumber >= 0 && randomNumber <= 20 && aiRegistry.size() < 3)
                        createNewEnemy();
                }

                gContext.save();

                background.update();
                inputManager.update();
                entityRegistry.update();
                aiRegistry.update();

                animationTime++;
                if(animationTime == 60)
                    animationTime = 0;
                gContext.restore();

            }
        };

        gameLoop.start();
        stage.show();

    }

    public void createNewEnemy() {
        Ship ship = new Ship(this, 0, 0, "Ship_enemy");
        if(new Random().nextBoolean()) {
            ship.getPosition().y = new Random().nextInt((int)this.getScreenHeight());
            if(new Random().nextBoolean()) {
                ship.getPosition().x = 0;
            } else {
                ship.getPosition().x = this.getScreenWidth();
                ship.setRotation(180);
            }
        } else {
            ship.getPosition().x = new Random().nextInt((int)this.getScreenWidth());
            if(new Random().nextBoolean()) {
                ship.getPosition().y = 0;
                ship.setRotation(90);
            } else {
                ship.getPosition().y = this.getScreenHeight();
                ship.setRotation(270);
            }
        }
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
            STAGE.setTitle(NAME + " " + VERSION + " | developer mode");
        else
            STAGE.setTitle(NAME + " " + VERSION);
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
        return this.STAGE;
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

    public int getAnimationTime() {
        return animationTime;
    }
}
