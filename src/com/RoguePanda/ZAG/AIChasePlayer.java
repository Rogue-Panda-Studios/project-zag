/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the chase methods for entities following the Player
 *
 * @author Oa10712
 */
public class AIChasePlayer extends AI {

    int counter = 0;
    int cycleNumber = 0;
    int currentSprite = 0;
    /**
     * The speed at which the entity moves, in pixels
     */
    double walkSpeed;
    /**
     * The range that the Entity will stop chasing the Player
     */
    int distance;
    /**
     * The range that the Entity can see the Player from
     */
    int range;

    /**
     *
     * @param e The Entity that owns this AI
     * @param speed How fast the Entity can move, in pixels
     * @param dist The range that the Entity will stop chasing the Player
     * @param ran The range that the Entity can see the Player from
     */
    AIChasePlayer(Entity e, double speed, int dist, int ran) {
        super(e);
        walkSpeed = speed;
        distance = dist;
        range = ran;
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        if (!entity.dead && !entity.falling && entity.inside == entity.level.game.player.inside && entity.location.distance(entity.level.game.player.location) < range && !entity.level.game.player.dead) {
            if (entity.inside != null) {
                for (BuildingObject bo : entity.inside.getObjects()) {
                    if (bo.boundingBox.intersects(entity.boundingBox)) {
                        entity.velocity.setLocation(entity.velocity.getX(), - 1);
                    }
                }
            }
            int wid = (entity.level.game.player.sprite.getWidth() / 2 + entity.sprite.getWidth() / 2) + distance;
            if (entity.location.getX() < (entity.level.game.player.location.getX() - wid)) {
                entity.direction = 1;
                entity.velocity.setLocation(entity.direction * walkSpeed, entity.velocity.getY());
                chaseCycle();
            } else if (entity.location.getX() > (entity.level.game.player.location.getX() + wid)) {
                entity.direction = -1;
                entity.velocity.setLocation(entity.direction * walkSpeed, entity.velocity.getY());
                chaseCycle();
            } else {
                cycleNumber = 0;
                entity.spritenumber = 7;
                entity.velocity.setLocation(0, entity.velocity.getY());
                ret = false;
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
    private void chaseCycle() {
        if (counter < 3) {
            counter++;
        } else {
            if (currentSprite >= entity.chaseSprites.length - 1) {
                currentSprite = 0;
            } else {
                currentSprite++;
                entity.spritenumber = entity.chaseSprites[currentSprite];
            }
            counter = 0;
        }
    }
}
