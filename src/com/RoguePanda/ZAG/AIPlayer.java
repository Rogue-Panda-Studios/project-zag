/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * The AI for the Player. Not used.
 *
 * @author Oa10712
 */
class AIPlayer extends AI {

    Player player;

    /**
     *
     * @param aThis The Player that owns this AI
     */
    public AIPlayer(Player aThis) {
        super(aThis);
        player = aThis;
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        if (player.dead) {
            ret = false;
            player.level.game.cgui.paused = true;
        } else {
            double regen = ((player.hunger - 25) * .001) + ((player.thirst - 25) * .001) / 10;
            player.setRegenRate(regen);
            player.hunger -= (.001 * Math.abs(player.velocity.getX()) + .01);
            player.thirst -= .0001;
            player.heal(player.getRegenRate());
            /*for (Entity e : player.level.game.currentLevel.entities) {
             if (player.getBounds().intersects(e.getBounds())) {
             System.out.println("Collision with Player and " + e.name);
             }
             }*/
        }
        return ret;
    }
}
