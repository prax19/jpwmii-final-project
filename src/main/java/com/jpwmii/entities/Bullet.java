package com.jpwmii.entities;

import com.jpwmii.Game;
import com.jpwmii.utils.AudioEffect;
import com.jpwmii.utils.Sprite;
import com.jpwmii.utils.enums.Direction;

public class Bullet extends Entity {

    private boolean fired;
    private final Entity source;
    public Bullet(Game context, Entity source) {
        super(
                context,
                "Bullet",
                100,
                new Sprite(
                        context,
                        "entities/bullet/bullet.png"),
                new AudioEffect(
                        "entities/bullet/fire.wav"
                )
        );
        setHitboxSize(3, 3);
        this.source = source;
        this.fired = false;
    }

    public Bullet(
            Game context,
            Entity source,
            double x,
            double y
    ) {
        this(context, source);
        setPosition(x, y);
    }

    public void fire() {
        this.fired = true;
        this.audioEffect.play();
        moveForward(true);
    }

    @Override
    public void destroy() {
        super.destroy();
        remove();
    }

    @Override
    public void moveForward(boolean hold) {
        if(hold) {
            getVelocity().setLength(500);
            getVelocity().setAngle(getSprite().rotation);
        }
        else
            getVelocity().setLength(0);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if(source.isDestroyed())
            destroy();
        if(isFired()) {
            setHealth(getHealth() - 1);
            if(getHealth() == 0)
                destroy();
        }
    }

    @Override
    public void onScreenLeaving(Direction direction) { }

    public boolean isFired() {
        return fired;
    }

    public Entity getSource() {
        return source;
    }

}
