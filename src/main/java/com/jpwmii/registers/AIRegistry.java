package com.jpwmii.registers;

import com.jpwmii.Game;
import com.jpwmii.ai.AI;

public class AIRegistry extends Registry<AI>{

    public AIRegistry(Game context) {
        super(context);
    }

    @Override
    public void update() {
        for(int i = 0; i < this.size(); i++){
            AI ai = this.get(i);
            ai.update(getContext().getPlayersShip().getPosition().x, getContext().getPlayersShip().getPosition().y);
            if(ai.getEntity().isDestroyed())
                this.unregister(ai);
        }
    }
}
