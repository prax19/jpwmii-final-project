package com.jpwmii;

import com.jpwmii.registers.KeyClickedRegistry;
import com.jpwmii.registers.KeyPressedRegistry;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class InputManager {

    private Game context;
    private KeyPressedRegistry keyPressedRegister;
    private KeyClickedRegistry keyClickedRegister;

    public InputManager(Game context) {

        setContext(context);
        keyPressedRegister = new KeyPressedRegistry(context);
        keyClickedRegister = new KeyClickedRegistry(context);

        context.getStage().getScene().setOnKeyPressed(
                (KeyEvent event) -> {
                    if(!keyPressedRegister.isRegistered(event.getCode())) {
                        keyPressedRegister.register(event.getCode());
                        keyClickedRegister.register(event.getCode());
                    }

                }
        );

        context.getStage().getScene().setOnKeyReleased(
                (KeyEvent event) -> {
                    if(keyPressedRegister.isRegistered(event.getCode()))
                        keyPressedRegister.unregister(event.getCode());

                }
        );

    }

    public boolean isKeyPressed(KeyCode key) {
        return keyPressedRegister.isKeyPressed(key);
    }

    public boolean isKeyClicked(KeyCode key) {
        return keyClickedRegister.isKeyClicked(key);
    }

    public void update() {

        keyPressedRegister.update();
        keyClickedRegister.update();

    }

    public Game getContext() {
        return context;
    }

    public void setContext(Game context) {
        this.context = context;
    }
}
