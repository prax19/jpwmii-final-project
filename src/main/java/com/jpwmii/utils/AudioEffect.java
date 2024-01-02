package com.jpwmii.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AudioEffect {

    private final List<Clip> clips;
    private int currentSound;

    private AudioEffect() {
        this.clips = new ArrayList<>();
        this.currentSound = 0;
    }

    public AudioEffect(String... audioClips) {
        this();
        try {
            for(String clip: audioClips)
                this.addSound(clip);
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(int soundIndex) {
        getClip(soundIndex).start();
    }

    public void play() {
        getCurrentClip().start();
    }

    public void loop() {
        getCurrentClip().loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(float volume) {
        Clip clip = getCurrentClip();
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(20f * (float) Math.log10(volume));
    }

    public Clip getCurrentClip() {
        return clips.get(currentSound);
    }

    public Clip getClip(int index) {
        if(currentSound < clips.size() && currentSound > 0)
            return clips.get(index);
        else
            throw new IndexOutOfBoundsException();
    }

    public void setCurrentSoundIndex(int currentSound) {
        if(currentSound < clips.size() && currentSound > 0)
            this.currentSound = currentSound;
        else
            throw new IndexOutOfBoundsException();
    }

    public int getCurrentSoundIndex() {
        return currentSound;
    }

    public void addSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clips.add(clip);
    }
}
