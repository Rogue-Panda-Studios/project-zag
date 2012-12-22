/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Handles the code to generate a projectile and set it to shoot at the player
 *
 * @author Oa10712
 */
public class AIShootProjectileAtPlayer extends AI {

    int attackSpeed;
    int counter = 0;
    int distance;
    int damage;
    int projectileType;

    /**
     *
     * @param e The Entity that owns this AI
     * @param speed The rate of attack, higher numbers mean slower speeds
     * @param dist The distance that the Entity will attempt to fire at
     * @param dam The amount of damage for the Projectile to deal
     * @param type The type of Projectile to fire
     */
    public AIShootProjectileAtPlayer(Entity e, int speed, int dist, int dam, int type) {
        super(e);
        projectileType = type;
        attackSpeed = speed;
        distance = dist;
        damage = dam;
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        int wid = (entity.level.game.player.sprite.getWidth() / 2 + entity.sprite.getWidth() / 2);
        if (!entity.dead && !entity.falling && entity.inside == entity.level.game.player.inside && entity.location.distance(entity.level.game.player.location) < distance && !entity.level.game.player.dead) {
            if (entity.location.getX() < entity.level.game.player.location.getX()) {
                entity.direction = 1;
            } else if (entity.location.getX() > entity.level.game.player.location.getX()) {
                entity.direction = -1;
            }
            if (entity.location.getX() < (entity.level.game.player.location.getX() - (wid + distance))) {
                entity.direction = 1;
            } else if (entity.location.getX() > (entity.level.game.player.location.getX() + (wid + distance))) {
                entity.direction = -1;
            } else {
                if (counter > attackSpeed) {
                    entity.level.entities.add(new Projectile(
                            "Fireball",
                            projectileType,
                            5,
                            new Point2D.Double(entity.location.getX() - 3 + ((entity.sprite.getWidth() / 2 + 5) * entity.direction),
                            entity.location.getY() - 5),
                            entity.level,
                            damage,
                            distance + 20,
                            new Point(2 * entity.direction, 0)));
                    counter = 0;
                } else {
                    counter++;
                }
            }
        } else {
            ret = false;
        }
        return ret;
    }
}
