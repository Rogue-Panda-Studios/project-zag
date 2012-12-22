/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.SoundBox;
import java.io.File;

/**
 * This handles the close range attack functions for attacks against the player
 *
 * @author Oa10712
 */
public class AIAttackPlayerMelee extends AI {

    int counter = 0;
    /**
     * The rate of attack, with higher numbers being slower
     */
    int attackSpeed;
    /**
     * The range that the Entity can attack from
     */
    int distance;
    Player player;

    /**
     *
     * @param e The entity that owns this AI
     * @param speed The rate of attack, with higher numbers being slower
     * @param dist The range that the Entity can attack from
     */
    public AIAttackPlayerMelee(Entity e, int speed, int dist) {
        super(e);
        attackSpeed = speed;
        distance = dist;
        player = entity.level.game.player;
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        int wid = (player.sprite.getWidth() / 2 + entity.sprite.getWidth() / 2) + distance;
        if (!entity.dead && !entity.falling && entity.inside == player.inside&& entity.location.distance(player.location) < distance && !player.dead) {
            if (entity.location.getX() < (player.location.getX() - wid)) {
                entity.direction = 1;
            } else if (entity.location.getX() > (player.location.getX() + wid)) {
                entity.direction = -1;
            } else {
                if (counter > 15) {
                    player.injure(1, 0);
                    counter = 0;
                    SoundBox.playClip(new File(getClass().getResource("/com/RoguePanda/ZAG/Sounds/robit.wav.wav").getFile()));
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
