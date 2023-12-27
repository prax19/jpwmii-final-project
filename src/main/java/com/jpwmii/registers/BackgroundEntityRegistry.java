package com.jpwmii.registers;

import com.jpwmii.Game;
import com.jpwmii.entities.Entity;

public class BackgroundEntityRegistry extends EntityRegistry{
    public BackgroundEntityRegistry(Game context) {
        super(context);
    }

    @Override
    public void detectCollisions(Entity entity) {  }


}
