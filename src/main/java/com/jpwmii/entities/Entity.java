package com.jpwmii.entities;

import com.jpwmii.Game;
import com.jpwmii.utils.AudioEffect;
import com.jpwmii.utils.Rectangle;
import com.jpwmii.utils.Sprite;
import com.jpwmii.utils.Vector;
import com.jpwmii.utils.enums.Direction;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

public abstract class Entity {

    private final Game context;
    private String name;
    private int health;
    private int healthMax;
    private Sprite sprite;
    protected AudioEffect audioEffect;
    private boolean destroyed;
    private boolean removed;

    public Entity(
            Game context,
            String name,
            int healthMax,
            Sprite sprite
    ) {
        setName(name);
        setHealth(healthMax);
        setHealthMax(health);
        setSprite(sprite);
        this.audioEffect = null;
        this.destroyed = false;
        this.removed = false;
        this.context = context;

    }

    public Entity(
            Game context,
            String name,
            int healthMax,
            Sprite sprite,
            AudioEffect audioEffect
    ) {
        setName(name);
        setHealth(healthMax);
        setHealthMax(health);
        setSprite(sprite);
        setAudioEffect(audioEffect);
        this.destroyed = false;
        this.removed = false;
        this.context = context;

    }

    public void moveForward(boolean hold) {}

    public void moveBack(boolean hold) {}

    public void moveLeft(boolean hold) {}

    public void moveRight(boolean hold) {}

    public void rotateLeft(boolean hold) {}

    public void rotateRight(boolean hold) {}

    public void collide(Entity entity) {
        if(entity instanceof Bullet) {
            damage(10);
        } else if (entity instanceof Ship) {
            damage(50);
        }
    }

    public void damage(int hp) {
        int newHealth =  this.health - hp;
        if(newHealth <= 0) {
            health = 0;
            destroy();
        } else {
            health = newHealth;
        }
    }

    public void destroy() {
        moveForward(false);
        rotateRight(false);
        rotateLeft(false);
        this.destroyed = true;
        setHealth(0);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void remove() {
        this.removed = true;
        if(!isDestroyed())
            this.destroy();
    }

    public boolean isRemoved() {
        return this.removed;
    }

    public void onScreenLeaving(Direction direction) {
        if(direction.equals(Direction.LEFT))
            this.getPosition().x = context.getScreenWidth();
        if(direction.equals(Direction.RIGHT))
            this.getPosition().x = -this.getWidth();
        if(direction.equals(Direction.TOP))
            this.getPosition().y = context.getScreenHeight();
        if(direction.equals(Direction.BOTTOM))
            this.getPosition().y = -this.getHeight();
    }

    private void detectScreenLeaving() {
        if(this.getPosition().x + this.getWidth() < 0)
            onScreenLeaving(Direction.LEFT);
        if(this.getPosition().x > context.getScreenWidth())
            onScreenLeaving(Direction.RIGHT);
        if(this.getPosition().y + this.getHeight() < 0)
            onScreenLeaving(Direction.TOP);
        if(this.getPosition().y > context.getScreenHeight())
            onScreenLeaving(Direction.BOTTOM);
    }

    public void setPosition(
            double x,
            double y
    ) {
        sprite.position.set(x, y);
    }

    public boolean hasCollisionWith(Entity entity) {
        return this.getSprite().getHitbox().overlaps(entity.getSprite().getHitbox());
    }

    public Vector getPosition() {
        return sprite.position;
    }

    public void setVelocity(
            double x,
            double y
    ) {
        sprite.velocity.set(x, y);
    }

    public Vector getVelocity() {
        return sprite.velocity;
    }

    public double getWidth() {
        return sprite.getCurrentImage().getWidth();
    }

    public double getHeight() {
        return sprite.getCurrentImage().getHeight();
    }

    public double getRotation() {
        return getSprite().rotation;
    }

    public void setRotation(double degrees) {
        getSprite().rotate(degrees);
    }

    public void update(double deltaTime) {
        sprite.update(deltaTime);
        this.detectScreenLeaving();
    }

    public void render(GraphicsContext gContext) {
        sprite.render(gContext);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void heal(int hp) {
        if(hp < 0)
            throw new IllegalArgumentException("Wrong hp value");
        if(health + hp < healthMax)
            health = health + hp;
        else
            health = healthMax;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public void setHealthMax(int healthMax) {
        this.healthMax = healthMax;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public AudioEffect getAudioEffect() throws NoSuchFieldException {
        if(Objects.nonNull(audioEffect))
            return audioEffect;
        else
            throw new NoSuchFieldException("AudioEffect not specified for this entity.");
    }

    public void setAudioEffect(AudioEffect audioEffect) {
        this.audioEffect = audioEffect;
    }

    public Rectangle getHitbox() {
        return sprite.getHitbox();
    }

    public void setHitboxSize(double w, double h) {
        sprite.setHitboxSize(w, h);
    }

    public void setHitboxOffset(double x, double y) {
        sprite.getHitbox().setPosition(x, y);
    }

    public Game getContext() {
        return context;
    }


}
