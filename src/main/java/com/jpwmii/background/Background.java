package com.jpwmii.background;

import com.jpwmii.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Background {

    private Game context;

    public Background(Game context) {
        this.context = context;
    }

    public void update() {
        this.render(context.getGraphicsContext());
    }

    public void render(GraphicsContext gContext) {
        gContext.setFill(Color.rgb(40, 35, 35, 1));
        gContext.fillRect(0, 0, context.getScreenWidth(), context.getScreenHeight());
    }

}
