/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the special closeAttack methods
 *
 * @author Oa10712
 */
public class Damaging {

    /**
     * Attacks the nearest Entity
     *
     * @param en The Entity making the attack
     * @param range The range to check for Entities
     * @param type The type of damage
     */
    public static void closeAttack(Entity en, int range, int type) {
        Entity close = null;
        double dist = range;
        if (en.isPlayer) {
            for (Entity e : en.level.entities) {
                if (!e.isPlayer && !e.dead) {
                    try {
                        DroppedItem it = (DroppedItem) e;
                    } catch (Exception ex) {
                        try {
                            Projectile pr = (Projectile) e;
                        } catch (Exception ex1) {
                            if (e.location.distance(en.level.game.player.location) < dist) {
                                close = e;
                                dist = e.location.distance(en.level.game.player.location);
                            }
                        }
                    }
                }
            }
        } else {
            for (Entity e : en.level.entities) {
                if (!e.isPlayer && !e.dead) {
                    try {
                        DroppedItem it = (DroppedItem) e;
                    } catch (Exception ex) {
                        try {
                            Projectile pr = (Projectile) e;
                        } catch (Exception ex1) {
                            if (e.location.distance(en.level.game.player.location) < dist) {
                                close = e;
                                dist = e.location.distance(en.level.game.player.location);
                            }
                        }
                    }
                }
            }
        }
        if (close != null) {
            close.injure(en.level.game.player.items.get(0).item.getDamage(), type);
        }
    }

    /**
     * Attacks all entities within a specified range, other than itself
     *
     * @param range The range for the closeAttack
     * @param type The type of damage
     * @param en The Entity making the closeAttack
     */
    public static void splashAttack(int range, int type, Entity en) {
        for (Entity e : en.level.entities) {
            if (e.location.distance(en.level.game.player.location) < range && e != en) {
                e.injure(en.level.game.player.items.get(0).item.getDamage(), type);
            }
        }
    }
}
