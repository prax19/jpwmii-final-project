package com.jpwmii.registers;

import com.jpwmii.Game;
import com.jpwmii.entities.Entity;
import com.jpwmii.utils.AudioEffect;
import javafx.scene.input.KeyCode;

import javax.sound.sampled.Clip;

public class KeyClickedRegistry extends Registry<KeyCode>{
    public KeyClickedRegistry(Game context) {
        super(context);
    }

    public boolean isKeyClicked(KeyCode key) {
        return isRegistered(key);
    }

    @Override
    public void update() {
        if(isKeyClicked(KeyCode.M)) {
            AudioEffect music = getContext().getBackground().getMusic();
            if(music.getCurrentClip().isRunning())
                music.getCurrentClip().stop();
            else
                music.getCurrentClip().loop(Clip.LOOP_CONTINUOUSLY);
        }

        if(isKeyClicked(KeyCode.F)) {
            getContext().getPlayersShip().setPosition(0, 0);
            for(Entity entity: getContext().getEntityRegistry())
                entity.remove();
            getContext().createNewPlayer();
            getContext().createNewEnemy();
            getContext().setGameStarted(true);

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
