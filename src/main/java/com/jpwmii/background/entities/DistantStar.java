package com.jpwmii.background.entities;

import com.jpwmii.Game;
import com.jpwmii.utils.Sprite;

import java.util.Random;

public class DistantStar extends BackgroundEntity {

    int frameChangeTime;
    boolean animationForward;

    public DistantStar(Game context) {
        super(
                context,
                "DistantStar",
                1,
                new Sprite(
                        context,
                        "background/entities/distant_star/star1_1.png",
                        "background/entities/distant_star/star1_2.png",
                        "background/entities/distant_star/star1_3.png",
                        "background/entities/distant_star/star1_4.png"
                ));
        frameChangeTime = new Random().nextInt(60 + 1);
        animationForward = new Random().nextBoolean();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        Sprite sprite = getSprite();
        if(frameChangeTime == getContext().getAnimationTime()) {
            determineAnimationDirection();
            if(animationForward) {
                sprite.nextImageIndex();
                determineAnimationDirection();
            } else {
                sprite.previousImageIndex();
                determineAnimationDirection();
            }
        }
    }

    public void determineAnimationDirection() {
        Sprite sprite = getSprite();
        if(!sprite.hasNextImageIndex())
            animationForward = false;
        if(!sprite.hasPreviousImageIndex())
            animationForward = true;
    }

}
