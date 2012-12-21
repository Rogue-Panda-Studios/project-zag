/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;

/**
 * This handles the attack and despawn for a specified Projectile
 *
 * @author Oa10712
 */
public class AIProjectile extends AI {

    boolean temp = true;

    /**
     *
     * @param e The projectile that owns this AI
     */
    public AIProjectile(Projectile e) {
        super(e);
    }

    @Override
    public boolean execute() {
        Projectile p = (Projectile) entity;
        if (p.distance >= p.range) {
            Cleanup.remove(p.level, p);
        } else {
            p.distance += p.velocity.distance(new Point(0, 0));
            for (Entity e : p.level.entities) {
                if (!e.dead) {
                    if (e.isPlayer || e.monster) {
                        try {
                            if (entity.boundingBox.intersects(e.boundingBox)) {
                                e.injure(p.damage, 0);
                                Cleanup.remove(p.level, p);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            if (!p.level.game.player.dead) {
                int wid = (p.level.game.player.sprite.getWidth() / 2 + p.sprite.getWidth() / 2);
                if (p.location.getX() < (p.level.game.player.location.getX() - wid)) {
                } else if (p.location.getX() > (p.level.game.player.location.getX() + wid)) {
                } else {
                    p.level.game.player.injure(p.damage, 0);
                    Cleanup.remove(p.level, p);
                }
            }
        }
        return true;
    }
}
