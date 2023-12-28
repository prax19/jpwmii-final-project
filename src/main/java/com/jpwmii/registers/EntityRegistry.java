package com.jpwmii.registers;

import com.jpwmii.Game;
import com.jpwmii.entities.Bullet;
import com.jpwmii.entities.Entity;
import com.jpwmii.entities.Ship;

import java.util.Objects;

public class EntityRegistry extends Registry<Entity>{

    public EntityRegistry(Game context) {
        super(context);
    }

    @Override
    public void update() {
        for(int i = 0; i < this.size(); i++) {
            Entity entity = this.get(i);
            detectCollisions(entity);

            entity.update(1/60.0);
            entity.render(getContext().getGraphicsContext());
            if(entity.isRemoved()) {
                this.unregister(entity);
            }
        }
    }

    public void detectCollisions(Entity entity) {
        for(Entity entity2: this)
            if(     !Objects.equals(entity, entity2) &&
                    entity.hasCollisionWith(entity2))  {
                if(!(entity instanceof Bullet)) {
                    if(entity2 instanceof Bullet) {
                        if (((Bullet) entity2).getSource() != entity && !entity.isDestroyed()) {
                            entity.collide(entity2);
                            entity2.collide(entity);
                            if(entity.isDestroyed())
                                ((Ship)((Bullet) entity2).getSource()).score(10);
                        }
                    } else {
                        entity.collide(entity2);
                        entity2.collide(entity);
                    }
                }
            }
    }
}
