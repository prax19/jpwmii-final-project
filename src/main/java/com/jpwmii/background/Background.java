package com.jpwmii.background;

import com.jpwmii.Game;
import com.jpwmii.background.entities.DistantStar;
import com.jpwmii.registers.BackgroundEntityRegistry;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Background {

    private final Game context;
    private final BackgroundEntityRegistry bgEntityReigstry;

    public Background(Game context) {
        this.context = context;
        bgEntityReigstry = new BackgroundEntityRegistry(context);
        compose();
    }

    public void createNewDistantStar(double x, double y) {
        DistantStar star = new DistantStar(context);
        star.getSprite().setRandomCurrentImageIndex(0, star.getSprite().getMaxImageIndex());
        star.setPosition(x, y);
        bgEntityReigstry.register(star);
    }

    public void compose() {
        int starCount = (int) (context.getScreenWidth() * context.getScreenWidth() / 20000);
        bgEntityReigstry.clear();
        for(int i = 0; i < starCount; i++)
            createNewDistantStar(
                    new Random().nextInt((int) context.getScreenWidth()),
                    new Random().nextInt((int) context.getScreenHeight()));
    }

    public void update() {
        this.render(context.getGraphicsContext());
        bgEntityReigstry.update();
    }

    public void render(GraphicsContext gContext) {
        gContext.setFill(Color.rgb(1, 11, 15, 1));
        gContext.fillRect(0, 0, context.getScreenWidth(), context.getScreenHeight());
    }

}
