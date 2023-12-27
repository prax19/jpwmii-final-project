package com.jpwmii.ai;

import com.jpwmii.entities.Entity;
import com.jpwmii.entities.Ship;
import com.jpwmii.utils.Vector;

public class AI {

    private Ship entity;

    private double targetX;
    private  double targetY;

    public AI(Ship entity) {
        this.entity = entity;
    }

    private void controlRotation() {

        double target = Math.toDegrees(Math.atan2(targetY - entity.getPosition().y, targetX - entity.getPosition().x));
        double aim = entity.getSprite().rotation;

        target = (target + 360) % 360;
        aim = (aim + 360) % 360;

        double diff = target - aim;

        if(diff < -180) {
            diff += 360;
        } else if (diff >= 180) {
            diff -=360;
        }

        if(diff < 5 && diff > -5) {
            entity.rotateLeft(true);
            entity.rotateRight(false);
            entity.fireBullet();
        } else if (diff > 5) {
            entity.rotateLeft(false);
            entity.rotateRight(true);
        } else {
            //entity.rotateRight(false);
            //entity.rotateLeft(false);
        }

    }

    private void controlAcceleration() {

        Vector vector = new Vector(
                entity.getPosition().x - targetX,
                entity.getPosition().y - targetY);

        double distance = vector.getLength();

        if(distance > 300)
            if(entity.getVelocity().getLength() < 100)
                entity.moveForward(true);
            else
                entity.moveForward(false);
        else if(distance <= 300 && distance > 100)
            entity.moveForward(true);
        else if(distance <= 100 && distance > 25) {
            if(entity.getVelocity().getLength() < 100)
                entity.moveForward(true);
            else
                entity.moveForward(false);
        } else {
            entity.moveForward(false);
        }

    }

    public void update(double x, double y) {
        targetX = x;
        targetY = y;
        if(!entity.isDestroyed()) {
            controlRotation();
            controlAcceleration();
        }
    }

    public Entity getEntity() {
        return entity;
    }

}
