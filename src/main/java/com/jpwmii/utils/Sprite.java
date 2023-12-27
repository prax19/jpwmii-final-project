package com.jpwmii.utils;

import com.jpwmii.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sprite {

    public final Game context;
    public Vector position;
    public Vector velocity;
    public double rotation;
    private final Rectangle boundary;

    public List<Image> images;
    private int currentImage;

    private boolean visible;

    public Sprite(Game context) {
        this.position = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Rectangle();
        this.images = new ArrayList<>();
        this.currentImage = 0;
        this.visible = true;
        this.context = context;
    }

    public Sprite(Game context, String... sprites) {
        this(context);
        for(String sprite: sprites)
            addImage(sprite);
    }

    public void addImage(String path) {
        try {
            images.add(new Image(getClass().getClassLoader().getResource(path).toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rotate(double degrees) {
        double newRotation = rotation + degrees;
        if( newRotation > 180 || newRotation < -180 ) {
            newRotation = -newRotation + newRotation%180;
        }
        rotation = newRotation;
    }

    public void setCurrentImageIndex(int currentImage) {
        this.currentImage = currentImage;
    }

    public void setRandomCurrentImageIndex(int min, int max) {
        if(min > max || min < 0 || max > images.size() + 1)
            throw new IndexOutOfBoundsException();
        int index = min + new Random().nextInt(max + 1 - min);
        setCurrentImageIndex(index);
    }

    public void nextImageIndex() {
        if(hasNextImageIndex())
            currentImage++;
    }

    public void previousImageIndex() {
        if(hasPreviousImageIndex())
            currentImage--;
    }

    public boolean hasNextImageIndex() {
        return currentImage < images.size() - 1;
    }

    public boolean hasPreviousImageIndex() {
        return currentImage > 0;
    }

    public int getCurrentImageIndex() {
        return this.currentImage;
    }

    public int getMinImageIndex() {
        return 0;
    }

    public int getMaxImageIndex() {
        return images.size() - 1;
    }

    public Image getCurrentImage() {
        return this.images.get(this.currentImage);
    }

    public Rectangle getHitbox() {
        this.boundary.setPosition(this.position.x, this.position.y);
        return this.boundary;
    }

    public void setHitboxSize(double w, double h) {
        this.boundary.setSize(w, h);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void update(double deltaTime) {
        this.position.add(
                this.velocity.x * deltaTime,
                this.velocity.y * deltaTime);
    }

    public void render(GraphicsContext gContext) {
        if(visible) {

            gContext.save();

            gContext.translate(
                    this.position.x,
                    this.position.y
            );
            gContext.rotate(this.rotation);
            gContext.translate(
                    -this.images.get(currentImage).getWidth() / 2,
                    -this.images.get(currentImage).getHeight() / 2
            );

            gContext.drawImage(this.images.get(currentImage), 0, 0);

            if(this.context.isDeveloperModeEnabled()) {
                gContext.setStroke(Color.RED);
                gContext.strokeRect(
                        this.images.get(currentImage).getWidth() / 2 - getHitbox().width / 2,
                        this.images.get(currentImage).getHeight() / 2 - getHitbox().height / 2,
                        getHitbox().width,
                        getHitbox().height);
            }

            gContext.restore();

        }
    }

}
