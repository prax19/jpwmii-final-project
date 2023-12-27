package com.jpwmii.registers;

import com.jpwmii.Game;
import javafx.scene.input.KeyCode;

public class KeyClickedRegistry extends Registry<KeyCode>{

    public KeyClickedRegistry(Game context) {
        super(context);
    }

    public boolean isKeyClicked(KeyCode key) {
        return isRegistered(key);
    }

    @Override
    public void update() {
        if(isKeyClicked(KeyCode.P)) {
            getContext().createNewEnemy();
        }

        if(isKeyClicked(KeyCode.O)) {
            getContext().createNewPlayer();
        }

        if(isKeyClicked(KeyCode.F3)) {
            getContext()
                    .setDeveloperModeEnabled(
                            !getContext().isDeveloperModeEnabled()
                    );
        }

        if(isKeyClicked(KeyCode.F11)) {
            getContext()
                    .getStage().setFullScreen(
                            !getContext().getStage().isFullScreen()
                    );
        }

        this.clear();
    }

}
