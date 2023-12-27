package com.jpwmii.registers;

import com.jpwmii.Game;
import javafx.scene.input.KeyCode;

public class KeyPressedRegistry extends Registry<KeyCode>{

    public KeyPressedRegistry(Game context) {
        super(context);
    }

    public boolean isKeyPressed(KeyCode key) {
        return isRegistered(key);
    }

    @Override
    public void update() {

        if(isKeyPressed(KeyCode.LEFT)) {
            getContext().getPlayersShip().rotateLeft(true);
        } else {
            getContext().getPlayersShip().rotateLeft(false);
        }

        if(isKeyPressed(KeyCode.RIGHT)) {
            getContext().getPlayersShip().rotateRight(true);
        } else {
            getContext().getPlayersShip().rotateRight(false);
        }

        if(isKeyPressed(KeyCode.UP)) {
            getContext().getPlayersShip().moveForward(true);
        } else {
            getContext().getPlayersShip().moveForward(false);
        }

        if(isKeyPressed(KeyCode.SPACE)) {
            getContext().getPlayersShip().fireBullet();
        }

    }

}
