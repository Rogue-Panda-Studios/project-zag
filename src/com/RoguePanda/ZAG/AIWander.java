/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the code to make an Entity wander
 *
 * @author Oa10712
 */
public class AIWander extends AI {

    int counter = 0;
    int cycleNumber = 0;
    int waiter = 0;
    int currentSprite = 0;
    /**
     * The speed for the Entity to move at
     */
    double walkSpeed;

    /**
     *
     * @param e The Entity that owns this AI
     * @param speed The speed for this Entity to move
     */
    AIWander(Entity e, double speed) {
        super(e);
        walkSpeed = speed;
        System.out.println(speed);
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        if (!entity.dead && !entity.falling) {
            if (entity.inside != null && entity.direction != 0) {
                if (entity.boundingBox.getMinX() <= entity.inside.location.x) {
                    if (entity.velocity.getX() < 0) {
                        waiter = 151;
                    }
                }
                if (entity.inside.location.x + entity.inside.insideSprite.getWidth() <= entity.boundingBox.getMaxX()) {
                    if (entity.velocity.getX() > 0) {
                        waiter = 151;
                    }
                }
                for (BuildingObject bo : entity.inside.getObjects()) {
                    if (bo.boundingBox.intersects(entity.boundingBox)) {
                        entity.velocity.setLocation(entity.velocity.getX(), - 1);
                    }
                }
            }
            if (waiter > 150) {
                waiter = 0;
                entity.direction = (int) (Math.random() * 3) - 1;
            } else {
                entity.velocity.setLocation(entity.direction * walkSpeed, entity.velocity.getY());
                if (entity.velocity.getX() != 0) {
                    walkCycle();
                } else {
                    cycleNumber = 0;
                    entity.spritenumber = 2;
                }
                waiter++;
            }
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * This is called every frame when the BasicZombie is walking; It updates
     * the sprite
     */
    private void walkCycle() {
        if (counter < 5) {
            counter++;
        } else {
            if (currentSprite >= entity.walkSprites.length - 1) {
                currentSprite = 0;
            } else {
                currentSprite++;
                entity.spritenumber = entity.walkSprites[currentSprite];
            }
            counter = 0;
        }
    }
}
