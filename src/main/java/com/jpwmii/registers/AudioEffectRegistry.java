package com.jpwmii.registers;

import com.jpwmii.Game;
import com.jpwmii.utils.AudioEffect;

public class AudioEffectRegistry extends Registry<AudioEffect>{

    public AudioEffectRegistry(Game context) {
        super(context);
        register(
            new AudioEffect("entities/bullet/fire.wav")
        );
    }

    @Override
    public void update() {

    }
}
