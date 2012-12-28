/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

public class AIFlyingWander extends AI {

    int currentSprite = 0;

    public AIFlyingWander(Entity e) {
        super(e);
    }

    @Override
    public boolean execute() {
        boolean pass = true;
        if (!entity.dead) {
            if (waiter > 100) {
                entity.direction = (int) (Math.random() * 3) - 1;
                entity.velocity.setLocation(entity.direction, -((int) (Math.random() * 7) - 3));
            } else {
                waiter++;
            }
            walkCycle();
        } else {
            pass = false;
        }
        return pass;
    }

    private void walkCycle() {
        if (counter < 5) {
            counter++;
        } else {
            if (currentSprite >= entity.walkSprites.length - 1) {
                currentSprite = 0;
            } else {
                currentSprite++;
                entity.spritenumber = entity.walkSprites[currentSprite - 1];
            }
            counter = 0;
        }
    }
}
