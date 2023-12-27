package com.jpwmii.background.entities;

import com.jpwmii.Game;
import com.jpwmii.entities.Entity;
import com.jpwmii.utils.Sprite;

public abstract class BackgroundEntity extends Entity {

    public BackgroundEntity(Game context, String name, int healthMax, Sprite sprite) {
        super(context, name, healthMax, sprite);
    }

    @Override
    public void update(double deltaTime) {
        getSprite().update(deltaTime);
    }

}
