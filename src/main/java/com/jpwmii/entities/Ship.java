package com.jpwmii.entities;

import com.jpwmii.Game;
import com.jpwmii.utils.Sprite;
import com.jpwmii.utils.enums.Direction;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Ship extends Entity {

    private int fireDelay = 0;
    private double rotationSpeed = 0;

    public Ship(Game context) {
        super(
                context,
                "Ship",
                150,
                new Sprite(
                        context,
                        "entities/ship/ship.png",
                        "entities/ship/ship_fw.png",
                        "entities/ship/ship_fw2.png",
                        "entities/ship/ship_fw3.png",
                        "entities/ship/ship_w.png",
                        "entities/ship/ship_w2.png",
                        "entities/ship/ship_w3.png"
                )
        );
        setHitboxSize(10, 10);
    }

    public Ship(
            Game context,
            double x,
            double y
    ) {
        this(context);
        setPosition(x, y);
    }

    public Ship(
            Game context,
            double x,
            double y,
            String name
    ) {
        this(context, x, y);
        this.setName(name);
    }

    @Override
    public void moveForward(boolean hold) {
        double velocity = getVelocity().getLength();
        if(!isDestroyed())
            if(hold) {
                if(velocity < 200)
                    velocity = velocity + 1;
                getVelocity().setLength(velocity);
                getVelocity().setAngle(this.getSprite().rotation);
                if(velocity <= 100)
                    this.getSprite().setCurrentImageIndex(1);
                if(velocity > 100)
                    this.getSprite().setCurrentImageIndex(2);
                if(velocity > 150)
                    this.getSprite().setCurrentImageIndex(3);

            } else {
                if(velocity > 20)
                    velocity = velocity - 0.5;
                getVelocity().setLength(velocity);
                this.getSprite().setCurrentImageIndex(0);
            }
    }

    @Override
    public void rotateLeft(boolean hold) {
        if(hold && !isDestroyed()) {
            if( rotationSpeed > -2)
                rotationSpeed -= 0.1;
        } else {
            if(rotationSpeed < 0 && !isDestroyed())
                rotationSpeed += 0.2;
        }
        getSprite().rotate(rotationSpeed);
    }

    @Override
    public void rotateRight(boolean hold) {
        if(hold && !isDestroyed()) {
            if( rotationSpeed < 2)
                rotationSpeed += 0.1;
        } else {
            if (rotationSpeed > 0 && !isDestroyed())
                rotationSpeed -= 0.2;
        }
        getSprite().rotate(rotationSpeed);
    }

    @Override
    public void destroy() {
        if(!isDestroyed()) {
            super.destroy();
            this.getSprite().setRandomCurrentImageIndex(4, 6);
        }
    }

    public void fireBullet() {
        if(fireDelay == 0 && !isDestroyed()) {
            Bullet bullet = new Bullet(
                    this.getContext(),
                    this,
                    this.getPosition().x,
                    this.getPosition().y);
            bullet.setRotation(this.getRotation());
            getContext().getEntityRegistry().register(bullet);
            fireDelay = 4;
            bullet.fire();
        } else {
            fireDelay--;
        }

    }

    @Override
    public void render(GraphicsContext gContext) {
        super.render(gContext);
        if(getContext().isDeveloperModeEnabled()) {
            gContext.save();

            String stateInfo;
            if(!isDestroyed())
                stateInfo = String.format("%dhp", getHealth());
            else
                stateInfo = "destroyed";
            if(isRemoved())
                stateInfo = "removed";

            String shipDebugInfo = String.format(
                    getName() +"\n" +
                    "%s", stateInfo);

            gContext.setFill(Color.RED);
            gContext.fillText(shipDebugInfo,
                    getSprite().position.x + getSprite().getCurrentImage().getWidth() / 2,
                    getSprite().position.y
            );

            gContext.restore();
        }

    }

    @Override
    public void onScreenLeaving(Direction direction) {
        if(isDestroyed())
            remove();
        super.onScreenLeaving(direction);
    }

}
