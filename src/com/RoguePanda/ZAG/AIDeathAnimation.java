/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the death animation for a specified Entity
 *
 * @author Oa10712
 */
public class AIDeathAnimation extends AI {

    int currentSprite = 0;
    int counter = 0;
    int waiter = 0;

    /**
     * This handles the death animation for the Entity
     *
     * @param e The Entity that owns this AI
     */
    AIDeathAnimation(Entity e) {
        super(e);
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        if (entity.dead) {
            if (counter < entity.deathSprites.length * 10) {
                if (waiter < 10) {
                    waiter++;
                } else {
                    waiter = 0;
                    currentSprite++;
                }
                entity.spritenumber = entity.deathSprites[currentSprite];
                counter++;
            }
        } else {
            ret = false;
        }
        return ret;
    }
}
